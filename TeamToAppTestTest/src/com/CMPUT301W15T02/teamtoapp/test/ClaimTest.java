package com.CMPUT301W15T02.teamtoapp.test;

import java.util.Calendar;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.ClaimManager;

// Source: https://www.youtube.com/watch?v=k9ZNbsc0Qgo 2015-02-08

public class ClaimTest extends TestCase {

	public void testClaim () {
		ClaimManager manager = ClaimManager.getInstance();
		
		Claim claim = new Claim();
		
		manager.addClaim(claim);
		
		assertNotNull("manager has a claim!", manager);
		
		//String name = "new claim";
		//claim.addClaimName(name);

	}
	
	
}
