package com.CMPUT301W15T02.teamtoapp.test;

import java.util.Calendar;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.ClaimManager;

// Source: https://www.youtube.com/watch?v=k9ZNbsc0Qgo 2015-02-08

public class ClaimTest extends TestCase {

	public void testClaimManager () {
		/*US01.01.01
		  As a claimant, I want make an expense claim that records my name, a starting date of travel, and an ending date of travel.
		*/
		ClaimManager manager = ClaimManager.getInstance();
		
		// Add new claim to manager - works
		Claim claim = new Claim();
		manager.addClaim(claim);
		assertNotNull("manager has no claim!", manager);
		
		// Save new information for claim, check if saved in manager - works
		String name = "new claim";
		Calendar start_date = Calendar.getInstance();
		Calendar end_date = Calendar.getInstance();
		claim.addClaimName(name);
		claim.setStartDate(start_date);
		claim.setEndDate(end_date);
		assertTrue("Name is not equal", manager.getClaim(claim).getClaimName() == name);
		assertTrue("Start date is not equal", manager.getClaim(claim).getStartDate() == start_date);
		assertTrue("End date is not equal", manager.getClaim(claim).getEndDate() == end_date);
		
		/*
		 * TODO:
		 * US01.02.01
		   As a claimant, I want an expense claim to record one or more destinations of 
		   travel and an associated reason for travel to each destination.
		*/
		
		/*US01.05.01
		  As a claimant, I want to delete an expense claim while changes are allowed. (Haven't checked statuses here)
		*/
		// Remove claim from manager - works
		manager.removeClaim(claim);
		assertNull("manager still this claim!", manager.getClaim(claim));

	}
	
	
}
