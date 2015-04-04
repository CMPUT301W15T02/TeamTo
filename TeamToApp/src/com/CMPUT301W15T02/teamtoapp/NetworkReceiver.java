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

import com.CMPUT301W15T02.teamtoapp.Model.Cache;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;

/**
 * 
 * Network Reciever extends a BroadcastReciever in order to recieve a broadcast.
 * Once recieved, the cache will complete requests of removing unwanted claims and 
 * loading claim updates from the user to the elastic search server.
 * 
 * @author Kyle Carlstrom
 *
 */
public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	final Context appContext = context.getApplicationContext();
    	
    	// Initialize context in MainManager
    	MainManager.initializeContext(appContext);
    	
    	// Run separate thread
    	new Thread(new Runnable() {
    		@Override
    		public void run() {
    			
    			// If network and ElasticSearch server is available, load removals and updates to cache
    			if (MainManager.isNetworkAvailable(appContext) && MainManager.isConnectedToServer()) {
    				Cache.getInstance().loadRemovals();
    				Cache.getInstance().loadUpdates();
    				
    				// Save updated claims to ElasticSearch
    				for (Claim claim: Cache.getInstance().getUpdates()) {
    					ElasticSearchManager.updateClaim(claim);
    				}
    				
    				// Delete unwanted claims from ElasticSearch
    				for (Claim claim: Cache.getInstance().getRemovals()) {
    					ElasticSearchManager.deleteClaim(claim.getClaimId());
    				}
    				
    				// Clear cache once done with updating and removing claims from cache
    				Cache.getInstance().clearCache();
    			}
    		}
    	}).start();
    }
    
}