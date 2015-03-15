package com.CMPUT301W15T02.teamtoapp.test;

import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Session;

import junit.framework.TestCase;

public class UseCaseTests3 extends TestCase {

	public void testAddClaim() {
		ClaimListController claimListController = new ClaimListController();
		Claim claim = new Claim();
		claimListController.addClaim(claim);
		assertEquals("Added to claimList", 1, claimListController.getClaims().size());
		assertTrue("claim should exist", claimListController.getClaims().contains(claim));
		Session.tearDownForTesting();
	}
	
	public void testDeleteClaim() {
		ClaimListController claimListController = new ClaimListController();
		Claim claim = new Claim();
		claimListController.addClaim(claim);
		claimListController.removeClaim(claim);
		assertEquals("No claims in claimlist", 0, claimListController.getClaims().size());
		assertFalse("Contains claim?", claimListController.getClaims().contains(claim));
	}
	
}
