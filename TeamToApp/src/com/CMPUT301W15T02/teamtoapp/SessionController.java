package com.CMPUT301W15T02.teamtoapp;

import android.content.Context;
import android.net.ConnectivityManager;


public class SessionController {
	
	private User user;
	private ClaimList claims;
	
	public SessionController() {
	}

	//Checks connection to Internet
	//Source: http://stackoverflow.com/a/9570292 2015-03-10
	public static boolean isNetworkAvailable(Context context) {
	    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}
}

	    
