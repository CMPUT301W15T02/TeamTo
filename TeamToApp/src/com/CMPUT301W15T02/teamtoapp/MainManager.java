package com.CMPUT301W15T02.teamtoapp;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;

import android.content.Context;
import android.net.ConnectivityManager;

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
		// TODO
		if (isNetworkAvailable(applicationContext)) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					ElasticSearchManager.addClaim(claim);
					
				}
			}).start();
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
		}
		LocalDataManager.saveClaims();
	}
	
	public static void loadClaims() {
		LocalDataManager.loadClaims();
	}
	
	
	public static void loadUser() {
		LocalDataManager.loadUser();
	}
	
	
	public static void saveUser() {
		// TODO
	}
}
