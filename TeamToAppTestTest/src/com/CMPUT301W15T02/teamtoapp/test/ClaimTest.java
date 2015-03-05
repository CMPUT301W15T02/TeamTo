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

import com.CMPUT301W15T02.teamtoapp.ClaimsList;
import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Expense;
import com.CMPUT301W15T02.teamtoapp.User;
import com.CMPUT301W15T02.teamtoapp.StringTuple;

// Source: https://www.youtube.com/watch?v=k9ZNbsc0Qgo 2015-02-08

public class ClaimTest extends TestCase {

	// UC 1.0
	public void testAddClaim () {
		ClaimsList claims = new ClaimsList();
		// Add new claim to manager - works
		Claim claim = new Claim();
		claims.addClaim(claim);
		assertNotNull("manager has no claim!", claims);
		
		// Save new information for claim, check if saved in claims
		ClaimController controller = new ClaimController();
		String name = "new claim";
		Calendar start_date = Calendar.getInstance();
		Calendar end_date = Calendar.getInstance();
		controller.setStartDate(start_date);
		claim.setEndDate(end_date);
		assertTrue("Name is not equal", claims.getClaim(claim).getClaimName() == name);
		assertTrue("Start date is not equal", claims.getClaim(claim).getStartDate() == start_date);
		assertTrue("End date is not equal", claims.getClaim(claim).getEndDate() == end_date);
		
		
		String dest = "some destination";
		String reason = "some reason";
		StringTuple record = new StringTuple(dest, reason);
		claim.addDestination(record);
		
		assertTrue("No destination and reason were added.", claims.getClaim(claim).verifyDestination(record));
	}
	
	// UC 1.1
	public void testEditClaim() {
		ClaimsList claims = new ClaimsList();
		Claim claim = new Claim();
		
		claim.setStatus(Claim.Status.IN_PROGRESS);
		claims.addClaim(claim); // Has default values
		
		claim.setClaimName("in progress");
		assertEquals("Name changed when in progress", "in progress", claims.getClaim(claim).getClaimName());
		
		claim.setStatus(Claim.Status.SUBMITTED);
		claim.setClaimName("submitted name");
		assertTrue("Claim name has not changed.", claims.getClaim(claim).getClaimName().equals("submitted name"));
		
		claim.setStatus(Claim.Status.RETURNED);
		claim.setClaimName("returned name");
		assertEquals("Claim name changed when returned", "returned name", claims.getClaim(claim).getClaimName());
	}
		
	// UC 1.2
	public void testDeleteClaim() {
		ClaimsList claims = new ClaimsList();
		Claim claim = new Claim();
		claims.addClaim(claim);
		// Remove claim from manager
		claims.removeClaim(claim);
		assertNull("user still has this claim!", claims.getClaim(claim));
		
	}
	
	// UC 1.*
	public void testClaimsSorted() {
		User user = new User("Peter");
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		user.addClaim(claim1);
		user.addClaim(claim2);
		Calendar cal1 = new GregorianCalendar(2005, 07, 28);
		Calendar cal2 = new GregorianCalendar(1999, 07, 28);
		claim1.setStartDate(cal1); // should be first claim
		claim2.setStartDate(cal2); 
		// Not really sure how to sort the claims by most recent dates 
		assertEquals("Sorting by start date?", claim2, user.getClaimPos(0));
	}
	
	
	// UC 3.2, UC 3.3
	public void testAddTags() {
		User user = new User("Kent Brockman");
		user.addTag(null);
		assertTrue("Contains tag", user.getTags().contains("tag"));
	}
	
	// UC 3.1
	public void testRemoveTags() {
		User user = new User("Peter");
		user.addTag(null);
		user.removeTag(null);
		assertEquals("Removed tag?", true, user.getTags().size()==0);
	}
	
	// UC 3.0
	public void testEditTags() {
		User user = new User("Sarah");
		user.addTag(null);
		user.editTag(null, null);
		assertEquals("Edit tags?", true, user.getTags().contains("tage"));
	}
	
	// UC 3.3
	//As a claimant, I want to filter the list of expense claims by tags, 
	//to show only those claims that have at least one tag matching any of a given set of one or more filter tags.
	public void testAddTagToUser() {
		// User will add a new tag, then claim will be assigned the new tag 
		User user = new User("Sarah");
		Claim claim = new Claim();
		user.addTag(null);
		claim.addTag(user.getATag(null));
		user.addClaim(claim);
		assertTrue("Tag added to claims", user.getClaim(claim).getTags().contains("tag"));
	}
	
	// UC 3.4
	public void testFilterByTag() {
		User user = new User("default");
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		claim2.addTag(null);
		user.addClaim(claim1);
		user.addClaim(claim2);
		assertTrue("Filter working", user.getClaimsWithTags("tag").contains((CharSequence) claim2));
		assertFalse("Tag filter working?", user.getClaimsWithTags("tag").contains((CharSequence) claim1));
	}
	
	// UC 7.0, UC 8.0, UC 8.1 (UC 8.2 - UC 8.4 are simply viewing the screen)
	public void testClaimStatuses() {
		User user = new User("Peter");
		Claim claim = new Claim();
		user.addClaim(claim);
		// UC 7.0 submit claim
		user.submitClaim(claim);
		assertEquals("Claim status submitted?", Claim.Status.SUBMITTED, user.getClaim(claim).getStatus());
		
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
	
	public void testGetExpense() {
		Claim emptyClaim = new Claim();
		Expense expense = new Expense();
		assertTrue("Not returning null if expense not in claim", emptyClaim.getExpense(expense).equals(null));
		
		Claim nonEmptyClaim = new Claim();
		ArrayList<Expense> expenseList = new ArrayList<Expense>();
		expenseList.add(expense);
		nonEmptyClaim.addExpense(expense);
		assertTrue("Not returning correct expense", nonEmptyClaim.getExpense(expense).equals(expense));
	}
		
}
