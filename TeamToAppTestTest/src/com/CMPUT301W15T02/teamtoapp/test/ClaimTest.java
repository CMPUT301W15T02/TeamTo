/* Claim tests for TeamToApp
 * 
 * Copyright 2015 Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/


package com.CMPUT301W15T02.teamtoapp.test;

import java.util.Calendar;
import java.util.HashMap;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.ClaimManager;
import com.CMPUT301W15T02.teamtoapp.StringTuple;

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
		 * US01.02.01
		   As a claimant, I want an expense claim to record one or more destinations of 
		   travel and an associated reason for travel to each destination.
		*/
		String dest = "some destination";
		String reason = "some reason";
		StringTuple record = new StringTuple(dest, reason);
		claim.addDestination(record);
		
		// Fixed test case error due to changing destinations from HashMap to StringTuple object.
		assertTrue("No destination and reason were added.", manager.getClaim(claim).verifyDestination(record));
		
		
		/*
		 * US01.04.01
		 * As a claimant, I want to edit an expense claim while changes are allowed
		 * (Haven't checked status here)
		*/
		//manager.getClaim(claim).setClaimName("new claim name");
		claim.setClaimName("new claim name");
		assertTrue("Claim name has not changed.", manager.getClaim(claim).getClaimName() == "new claim name");
		
		
		/*US01.05.01
		  As a claimant, I want to delete an expense claim while changes are allowed. (Haven't checked statuses here)
		*/
		// Remove claim from manager - works
		manager.removeClaim(claim);
		assertNull("manager still this claim!", manager.getClaim(claim));

	}
	
	
}
