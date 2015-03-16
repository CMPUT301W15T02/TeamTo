package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

import junit.framework.TestCase;

public class UseCase9Test extends TestCase {

	
	// UC 9.0
	public void testCloudStatus() {
		Claim claim = new Claim();
		ClaimList claimList = new ClaimList();
		claimList.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
		claimController.submitClaim();
		//assertEquals("Cloud saving working", user, user.loadFromCloud());
	}
}
