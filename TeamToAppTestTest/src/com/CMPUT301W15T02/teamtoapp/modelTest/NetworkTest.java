package com.CMPUT301W15T02.teamtoapp.modelTest;



import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

import android.test.AndroidTestCase;


public class NetworkTest extends AndroidTestCase{
	
	
	public void testNetworkAvailable() {
		MainManager.initializeContext(mContext);
	}
	
	public void testAddAndRemoveClaimInNetwork() {
		Claim claim = new Claim();
		MainManager.initializeContext(mContext);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainManager.addClaim(claim);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainManager.removeClaim(claim);
	}
	
	public void testGetSubmittedClaimsFromNetwork() {
		Claim claim = new Claim();
		MainManager.initializeContext(mContext);
		claim.setStatus(Status.SUBMITTED);

		int size = MainManager.getSubmittedClaims().size(); // save initial size
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainManager.addClaim(claim);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainManager.removeClaim(claim);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// assert size stays the same (adding/deletinfg the same claim)
		assertEquals(size, MainManager.getSubmittedClaims().size());
	}
	
	public void testGetClaimFromNetwork() {
		Claim claim = new Claim();
		MainManager.initializeContext(mContext);
		String claimID = claim.getClaimId();
		
		MainManager.addClaim(claim);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(claim.getClaimId(), ElasticSearchManager.getClaim(claimID).getClaimId());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainManager.removeClaim(claim);
	}

}
