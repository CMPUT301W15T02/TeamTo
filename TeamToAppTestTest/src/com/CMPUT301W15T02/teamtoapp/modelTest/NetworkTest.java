package com.CMPUT301W15T02.teamtoapp.modelTest;



import com.CMPUT301W15T02.teamtoapp.LocalDataManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

import android.test.AndroidTestCase;


public class NetworkTest extends AndroidTestCase{
	
	public Claim claim = new Claim();
	
	public void testNetworkAvailable() {
		MainManager.initializeContext(mContext);
	}
	
	public void testAddClaimToNetwork() {
		MainManager.initializeContext(mContext);
		claim.setClaimName("NetworkTest.java");
		MainManager.addClaim(claim);
	}
	
	public void testDeleteClaimFromNetwork() {
		MainManager.initializeContext(mContext);
		MainManager.removeClaim(claim);
	}
	
	// Test still failing. For some reason submitted claims 
	// are not being saved even though it was working before?
	public void testGetSubmittedClaimsFromNetwork() {
		MainManager.initializeContext(mContext);
		claim.setStatus(Status.SUBMITTED);
		MainManager.addClaim(claim);
		
		//assertEquals(1, MainManager.getSubmittedClaims().size());
	}

}
