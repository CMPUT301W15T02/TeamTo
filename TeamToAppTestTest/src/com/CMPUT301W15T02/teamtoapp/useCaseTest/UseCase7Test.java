package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

import junit.framework.TestCase;

public class UseCase7Test extends TestCase {
	
	// UC 7.0
	public void testClaimStatuses() {
		Claim claim = new Claim();
		ClaimList claims = new ClaimList();
		claims.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
		
		assertEquals("Claims match up?", claim, claims.findClaimByID(claim.getClaimId()));
		// UC 7.0 submit claim
		claimController.submitClaim();
		assertEquals("Claim status submitted?", Status.SUBMITTED, claimController.getCurrentClaim().getStatus());
	}

}
