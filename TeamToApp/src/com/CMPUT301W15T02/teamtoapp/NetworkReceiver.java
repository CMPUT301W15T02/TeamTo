/* 
 * 
 * Copyright 2015 Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.CMPUT301W15T02.teamtoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.CMPUT301W15T02.teamtoapp.Model.Cache;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


    	boolean isConnected = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
        if (isConnected) {
        	MainManager.initializeContext(context.getApplicationContext());
        	new Thread(new Runnable() {
				
				@Override
				public void run() {
					Cache.getInstance().loadRemovals();
					Cache.getInstance().loadUpdates();
					for (Claim claim: Cache.getInstance().getUpdates()) {
						ElasticSearchManager.updateClaim(claim);
					}
					for (Claim claim: Cache.getInstance().getRemovals()) {
						ElasticSearchManager.deleteClaim(claim.getClaimId());
					}
					Cache.getInstance().clearCache();
					
				}
			}).start();
        }
    }
}