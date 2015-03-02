package com.CMPUT301W15T02.teamtoapp.test;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.ClaimSingleton;
import com.CMPUT301W15T02.teamtoapp.SessionController;

import junit.framework.TestCase;

public class ClaimModelsTesting extends TestCase {
	
	
	public void testAddClaim() {
		SessionController sessionController = new SessionController();
		Claim claim = new Claim();
		sessionController.addClaim(claim);
		assertEquals("Added to aggregated claims", 1, sessionController.getClaims().size());
		assertNotNull(ClaimSingleton.getInstance().getClaim());
		assertEquals("Added to CurrentClaim singleton", claim, ClaimSingleton.getInstance().getClaim());
		sessionController.removeClaim(claim);
		assertEquals("Check if no claims saved", 0, sessionController.getClaims().size());
	}
	
	public void testDeleteClaim() {
		SessionController sessionController = new SessionController();
		Claim claim = new Claim();
		assertEquals("Check if no claims saved", 0, sessionController.getClaims().size());
	}
	
	public void testSortClaimsByDate() {
		//SessionController sessionController = new SessionController();
	}
}
