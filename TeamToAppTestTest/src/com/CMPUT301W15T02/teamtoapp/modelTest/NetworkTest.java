package com.CMPUT301W15T02.teamtoapp.modelTest;



import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;

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
	
	
	public void testSaveUser() {
		User user = User.getInstance();
		user.setName("Bob");
		user.addTag(new Tag("TESTING"));
		ElasticSearchManager.saveUser();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User.getInstance().setName("Joe");
		ElasticSearchManager.loadUser("Bob");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Bob", User.getInstance().getName());
	}
	
	
	public void testGetUserClaims() {
		String user = "Iggy";
		Claim claim1 = new Claim();
		claim1.setUserName(user);
		Claim claim2 = new Claim();
		claim2.setUserName(user);
		Claim claim3 = new Claim();
		claim3.setUserName("Someguy");
		ElasticSearchManager.addClaim(claim1);
		ElasticSearchManager.addClaim(claim2);
		ElasticSearchManager.addClaim(claim3);
		ElasticSearchManager.loadClaims(user);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(2, ClaimList.getInstance().getClaims().size());
	}
	

}
