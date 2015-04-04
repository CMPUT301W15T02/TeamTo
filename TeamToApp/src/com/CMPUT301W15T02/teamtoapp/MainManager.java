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

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.widget.Toast;

import com.CMPUT301W15T02.teamtoapp.Model.Cache;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.User;

/** 
 * Function: Save any changes offline/online.
 * MainManager will send requests to LocalDataManager and/or ElasticSearchDataManager
 * depending on whether offline/online state.
 * 
 *  @authors Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
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
	
	public static boolean isConnectedToServer() {
		int timeout = 500;
	    try{
	        URL myUrl = new URL("http://cmput301.softwareprocess.es:8080/cmput301w15t02");
	        URLConnection connection = myUrl.openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.connect();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	
	public static void addClaim(final Claim claim) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (isNetworkAvailable(applicationContext) && isConnectedToServer()) {
					ElasticSearchManager.addClaim(claim);
				} else {
					Cache.getInstance().addUpdate(claim);
				}
				LocalDataManager.saveClaims();
			}
		}).start();
	}
	
	public static void removeClaim(final Claim claim) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (isNetworkAvailable(applicationContext) && isConnectedToServer()) {
					ElasticSearchManager.deleteClaim(claim.getClaimId());
				} else {
					Cache.getInstance().addRemoval(claim);
					LocalDataManager.saveClaims();
				}
			}
		}).start();
		
	}
	
	public static void updateClaim(final Claim claim) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (isNetworkAvailable(applicationContext) && isConnectedToServer()) {
					ElasticSearchManager.updateClaim(claim);
				} else {
					Cache.getInstance().addUpdate(claim);
				}
				LocalDataManager.saveClaims();
			}
		}).start();
	}
	
	public static void getSubmittedClaims(final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (isNetworkAvailable(applicationContext) && isConnectedToServer()) {
					ElasticSearchManager.getSubmittedClaims();
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
		
	}
	
	
	
	public static void loadUserAndClaims(final String name, final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (isNetworkAvailable(applicationContext) && isConnectedToServer()) {
					ElasticSearchManager.loadUser(name);
					ElasticSearchManager.loadClaims(name);
				} else {
					LocalDataManager.loadUser();
					LocalDataManager.loadClaims();
				}
				handler.sendEmptyMessage(0);
			}
		}).start();
	}
	
	
	public static void saveUser() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (isNetworkAvailable(applicationContext) && isConnectedToServer()) {
					ElasticSearchManager.saveUser();
				}
				LocalDataManager.saveUser(User.getInstance());
			}
		}).start();
	}
	
	
	public static void ApproveClaim(final Claim claim) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (isNetworkAvailable(applicationContext) && isConnectedToServer()) {
					ElasticSearchManager.updateClaim(claim);
				} else {
					Toast.makeText(applicationContext, "Can't connect", Toast.LENGTH_SHORT).show();
				}
			}
		}).start();
	}
	
	
}
