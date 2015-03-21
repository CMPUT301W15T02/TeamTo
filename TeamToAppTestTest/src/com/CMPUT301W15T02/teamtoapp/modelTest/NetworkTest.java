package com.CMPUT301W15T02.teamtoapp.modelTest;



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
		MainManager.addClaim(claim);
	}
	
	public void testDeleteClaimFromNetwork() {
		MainManager.initializeContext(mContext);
		MainManager.removeClaim(claim);
	}
	
	public void testGetSubmittedClaimsFromNetwork() {
		MainManager.initializeContext(mContext);
		claim.setStatus(Status.SUBMITTED);
		MainManager.addClaim(claim);
		MainManager.removeClaim(claim);
		
		// This si going to change every time you run the test.
		assertEquals(1, MainManager.getSubmittedClaims().size());
	}

}
