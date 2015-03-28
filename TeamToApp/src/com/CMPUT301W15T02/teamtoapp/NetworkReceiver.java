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