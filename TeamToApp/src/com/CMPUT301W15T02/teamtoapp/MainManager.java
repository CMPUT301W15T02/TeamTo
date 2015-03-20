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
	
	
	public static void addClaim(Claim claim) {
		if (isNetworkAvailable(applicationContext)) {
			
		}
	}
	
	public static void loadClaims() {
		LocalDataManager.loadClaims();
	}
	
	public static void loadUser() {
		LocalDataManager.loadUser();
	}
}
