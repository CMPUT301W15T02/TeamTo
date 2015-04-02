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

import java.util.ArrayList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.CMPUT301W15T02.teamtoapp.Model.Cache;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.User;

/** 
 * Function: Save any changes offline/online.
 * MainManager will send requests to LocalDataManager and/or ElasticSearchDataManager
 * depending on whether offline/online state.
 *
 */

public class MainManager {

	private static Context applicationContext;
	
	
	
	public static void initializeContext(Context context) {
		applicationContext = context;
		LocalDataManager.initializeContext(context);
		ElasticSearchManager.initializeContext(context);
		Cache.initializeContext(context);
		
	}
	
	
	//Source: http://stackoverflow.com/a/9570292 2015-03-10
	/**
	 * Checks if there is network availability
	 * @param 	context	Current state of the application
	 * @return	boolean false if no connections, true otherwise
	 */
	public static boolean isNetworkAvailable(Context context) {
		return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}
	
	
	public static void addClaim(final Claim claim) {
		Log.i("CLAIMIDBAN", claim.getClaimId());
		if (isNetworkAvailable(applicationContext)) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					ElasticSearchManager.addClaim(claim);
				}
			}).start();
		} else {
			Cache.getInstance().addUpdate(claim);
		}
		LocalDataManager.saveClaims();
	}
	
	public static void removeClaim(final Claim claim) {
		if (isNetworkAvailable(applicationContext)) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					ElasticSearchManager.deleteClaim(claim.getClaimId());
				}
			}).start();
		} else {
			Cache.getInstance().addRemoval(claim);
		}
		LocalDataManager.saveClaims();
	}
	
	public static void updateClaim(final Claim claim) {
		if (isNetworkAvailable(applicationContext)) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					ElasticSearchManager.updateClaim(claim);
					
				}
			}).start();
		} else {
			Cache.getInstance().addUpdate(claim);
		}
		LocalDataManager.saveClaims();
	}
	
	public static ArrayList<Claim> getSubmittedClaims() {
		
		ArrayList<Claim> submittedClaims = new ArrayList<Claim>();
		if (isNetworkAvailable(applicationContext)) {
			submittedClaims = ElasticSearchManager.getSubmittedClaims();
		} 
		return submittedClaims;
	}
	
	public static void loadClaims(final String name) {
		if (isNetworkAvailable(applicationContext)) {
			ElasticSearchManager.loadClaims(name);
		} else {
			LocalDataManager.loadClaims();
		}
	}
	
	
	public static void loadUser(String name) {
		if (isNetworkAvailable(applicationContext)) {
			ElasticSearchManager.loadUser(name);
		} else {
			LocalDataManager.loadUser();
		}
	}
	
	
	public static void saveUser() {
		if (isNetworkAvailable(applicationContext)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					ElasticSearchManager.saveUser();
				}
			}).start();
		}
		LocalDataManager.saveUser(User.getInstance());
	}
	
	
	public static void ApproveClaim(final Claim claim) {
		if (isNetworkAvailable(applicationContext)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					ElasticSearchManager.updateClaim(claim);
				}
			}).start();
		}
	}
	
	
}
