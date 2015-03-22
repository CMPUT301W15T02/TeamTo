package com.CMPUT301W15T02.teamtoapp.modelTest;



import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
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

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int size = MainManager.getSubmittedClaims().size(); // save initial size
		MainManager.addClaim(claim);
		MainManager.removeClaim(claim);
		
		// assert size stays the same (adding/deletinfg the same claim)
		assertEquals(size, MainManager.getSubmittedClaims().size());
	}
	
	public void testGetClaimFromNetwork() {
		MainManager.initializeContext(mContext);
		String claimID = claim.getClaimId();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		MainManager.addClaim(claim);
		assertEquals(claim, ElasticSearchManager.getClaim(claimID));
		MainManager.removeClaim(claim);
	}

}
