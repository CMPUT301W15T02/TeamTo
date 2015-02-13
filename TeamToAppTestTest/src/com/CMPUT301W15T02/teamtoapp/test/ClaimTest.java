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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.User;
import com.CMPUT301W15T02.teamtoapp.UserController;
import com.CMPUT301W15T02.teamtoapp.StringTuple;

// Source: https://www.youtube.com/watch?v=k9ZNbsc0Qgo 2015-02-08

public class ClaimTest extends TestCase {

	// UC 1.0
	public void testAddClaim () {
		User user = new User("John");
		
		// Add new claim to manager - works
		Claim claim = new Claim();
		user.addClaim(claim);
		assertNotNull("manager has no claim!", user);
		
		// Save new information for claim, check if saved in user
		String name = "new claim";
		Calendar start_date = Calendar.getInstance();
		Calendar end_date = Calendar.getInstance();
		claim.addClaimName(name);
		claim.setStartDate(start_date);
		claim.setEndDate(end_date);
		assertTrue("Name is not equal", user.getClaim(claim).getClaimName() == name);
		assertTrue("Start date is not equal", user.getClaim(claim).getStartDate() == start_date);
		assertTrue("End date is not equal", user.getClaim(claim).getEndDate() == end_date);
		
		
		String dest = "some destination";
		String reason = "some reason";
		StringTuple record = new StringTuple(dest, reason);
		claim.addDestination(record);
		
		assertTrue("No destination and reason were added.", user.getClaim(claim).verifyDestination(record));
	}
	
	// UC 1.1
	public void testEditClaim() {
		User user = new User("Sarah");
		Claim claim = new Claim();
		
		claim.setStatus(Claim.Status.IN_PROGRESS);
		user.addClaim(claim); // Has default values
		
		claim.setClaimName("in progress");
		assertEquals("Name changed when in progress", "in progress", user.getClaim(claim).getClaimName());
		
		claim.setStatus(Claim.Status.SUBMITTED);
		claim.setClaimName("sumbitted name");
		assertTrue("Claim name has not changed.", user.getClaim(claim).getClaimName().equals("in progress"));
		
		claim.setStatus(Claim.Status.RETURNED);
		claim.setClaimName("returned name");
		assertEquals("Claim name changed when returned", "returned name", user.getClaim(claim).getClaimName());
	}
		
	// UC 1.2
	public void testDeleteClaim() {
		User user = new User("Peter");
		Claim claim = new Claim();
		user.addClaim(claim);
		// Remove claim from manager
		user.removeClaim(claim);
		assertNull("user still has this claim!", user.getClaim(claim));
		
	}
	
	// UC 1.*
	public void testClaimsSorted() {
		User user = new User("Peter");
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		user.addClaim(claim1);
		user.addClaim(claim2);
		Calendar cal = new GregorianCalendar(1999, 07, 28);
		claim2.setStartDate(cal); // should now be first claim
		assertEquals("Sorting by start date?", claim2, user.getClaims.get(0));
	}
	
	
	// UC 3.2, UC 3.3
	public void testAddTags() {
		User user = new User("Kent Brockman");
		user.addTag("tag");
		assertTrue("Contains tag", user.getTags().contains("tag"));
	}
	
	// UC 3.1
	public void testRemoveTags() {
		User user = new User("Peter");
		user.addTag("tag");
		user.removeTag("tag");
		assertEquals("Removed tag?", 0, user.getTags().size()==0);
	}
	
	// UC 3.0
	public void testEditTags() {
		User user = new User("Sarah");
		Claim claim = new Claim();
		user.addTag("tag");
		user.editTag("tag", "tage");
		assertEquals("Edit tags?", "tage", user.getTags.contains("tage"));
	}
	
	// UC 3.3
	public void testAddTagToUser() {
		User user = new User("Sarah");
		Claim claim = new Claim();
		user.addTag("tag");
		claim.addTag(user.getTag("tag"));
		assertEquals("Tag added to claims", claim.getTags().contains("tag"));
	}
	
	// UC 3.4
	public void testFilterByTag() {
		User user = new User("default");
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		claim2.addTag("tag");
		assertTrue("Filter working", user.getClaimsWithTags("tag").contains(claim2));
		assertFalse("Tag filter working?", user.getClaimsWithTags("tag").contains(claim1));
	}
	
	// UC 7.0, UC 8.0, UC 8.1 (UC 8.2 - UC 8.4 are simply viewing the screen)
	public void testClaimStatuses() {
		User user = new User("Peter");
		Claim claim = new Claim();
		// UC 7.0 submit claim
		user.submitClaim(claim);
		assertEquals("Claim status submitted?", Claim.Status.SUBMITTED, claim.getStatus());
		
		// UC 8.0 return claim
		user.returnClaim(claim);
		assertEquals("Claim returned?", Claim.Status.RETURNED, claim.getStatus());
		
		// UC 8.1 approve claim
		user.submitClaim(claim);
		user.approveClaim(claim);
		assertEquals("Claim approved?", Claim.Status.APPROVED, claim.getStatus());
	}
	
	// UC 9.0
	public void testCloudStatus() {
		User user = new User("Peter");
		Claim claim = new Claim();
		user.saveToCloud();
		assertEquals("Cloud saving working", user, user.loadFromCloud());
	}
		
}
