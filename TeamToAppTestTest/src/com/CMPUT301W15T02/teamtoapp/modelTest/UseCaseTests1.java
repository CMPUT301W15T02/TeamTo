/** Tests for use cases for TeamTo App. 
 * 	Use cases mostly apply to adding/editing/deleting claims, adding/editing/deleting tags, and 
 *  claim statuses.
 */

/* 
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


package com.CMPUT301W15T02.teamtoapp.modelTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorNewestFirst;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorOldestFirst;

// Source: https://www.youtube.com/watch?v=k9ZNbsc0Qgo 2015-02-08

public class UseCaseTests1 extends TestCase {

	// UC 1.0
	/**
	 * Tests claim controller functionality / claim persistence
	 */
	public void testAddClaim () {
		ClaimList claims = new ClaimList();
		Claim claim = new Claim();
		claims.addClaim(claim);
		
		ClaimController controller = new ClaimController(claim.getClaimId());
		String claimName = "New Claim";
		GregorianCalendar start_date = new GregorianCalendar();
		GregorianCalendar end_date = new GregorianCalendar();
		controller.setClaimName(claimName);
		controller.setStartDate(start_date);
		controller.setEndDate(end_date);
		
		assertEquals("Name is not equal", controller.getClaimName(), claimName);
		assertEquals("Start date is not equal", controller.getStartDate(), start_date);
		assertEquals("End date is not equal", controller.getEndDate(), end_date);
		
		String dest = "some destination";
		String reason = "some reason";
		controller.addDestination(dest, reason);
		
		//assertTrue("No destination and reason were added.", controller.getDestinations().contains(dest));
	}
	
	
	// UC 1.1
	public void testEditClaim() {
		ClaimList claims = new ClaimList();
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
		ClaimList claims = new ClaimList();
		Claim claim = new Claim();
		claims.addClaim(claim);
		// Remove claim from manager
		claims.removeClaim(claim);
		assertNull("user still has this claim!", claims.getClaim(claim));
		
	}

	public void testClaimsSorted() {
		ArrayList<Claim> claims = new ArrayList<Claim>();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		claims.add(claim1);
		claims.add(claim2);
		GregorianCalendar cal1 = new GregorianCalendar(2005, 07, 28);
		GregorianCalendar cal2 = new GregorianCalendar(1999, 07, 28);
		claim1.setStartDate(cal1); // should be first claim
		claim2.setStartDate(cal2); 
		// Not really sure how to sort the claims by most recent dates 
		Collections.sort(claims, new ClaimComparatorNewestFirst());
		assertEquals("Sorting by newest first?", claim1, claims.get(0));
		
		Collections.sort(claims, new ClaimComparatorOldestFirst());
		assertEquals("Sorting by oldest first?", claim2, claims.get(0));
	}
	
	// UC 3.2, UC 3.3
	public void testAddTags() {
		User user = new User("Kent Brockman");
		Tag testTag = new Tag("test");
		user.addTag(testTag);
		assertTrue("Contains tag", user.getTags().contains(testTag));
	}
	
	// UC 3.1
	public void testRemoveTags() {
		User user = new User("NAME");
		Tag testTag = new Tag("test");
		user.addTag(testTag);
		int initialSize = user.getTags().size();
		user.removeTag(testTag);
		assertEquals("Removed tag?", initialSize-1, user.getTags().size());
	}
	
	
	// UC 3.0
	public void testEditTags() {
		User user = new User("NAME");
		Tag tag = new Tag("Tag name");
		user.addTag(tag);
		user.renameTag(tag, "New name");
		
		assertEquals("Rename tags working?", "New name", user.getTags().get(4).getTagName());
	}
	
	// UC 3.3
	//As a claimant, I want to filter the list of expense claims by tags, 
	//to show only those claims that have at least one tag matching any of a given set of one or more filter tags.
	public void testAddTagToClaim() {
		// User will add a new tag, then claim will be assigned the new tag 
		Claim claim = new Claim();
		Tag tag = new Tag("Tag name");
		claim.addTag(tag);
		assertTrue("Tag added to claims", claim.getTags().contains(tag));
	}
	
	public void testRemoveTagFromClaim() {
		Claim claim = new Claim();
		Tag tag = new Tag("Tag name");
		claim.addTag(tag);
		claim.removeTag(tag);
		assertEquals("Tag actually removed?", 0, claim.getTags().size());
		assertFalse("Tag not there", claim.getTags().contains(tag));
	}
	
	/*
	// UC 3.4
	public void testFilterByTag() {
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		ClaimList claims = new ClaimList();
		Tag tag = new Tag("Tag name");
		claim2.addTag(tag);
		claims.addClaim(claim1);
		claims.addClaim(claim2);
		ClaimListController claimListController = new ClaimListController();
		assertTrue("Filter working", claimListController.getClaimsWithTags("Tag name").contains(claim1));
		assertFalse("Tag filter working?", claimListController.getClaimsWithTags("Tag name").contains(claim2));
	}
	*/
	
	// UC 7.0, UC 8.0, UC 8.1 (UC 8.2 - UC 8.4 are simply viewing the screen)
	public void testClaimStatuses() {
		Claim claim = new Claim();
		ClaimList claims = new ClaimList();
		claims.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
		
		assertEquals("Claims match up?", claim, claims.findClaimByID(claim.getClaimId()));
		// UC 7.0 submit claim
		claimController.submitClaim();
		assertEquals("Claim status submitted?", Status.SUBMITTED, claimController.getCurrentClaim().getStatus());
		
		// UC 8.0 return claim
		claimController.returnClaim();
		assertEquals("Claim returned?", Status.RETURNED, claimController.getCurrentClaim().getStatus());
		
		// UC 8.1 approve claim
		claimController.submitClaim();
		claimController.approvedClaim();
		assertEquals("Claim approved?", Status.APPROVED, claimController.getCurrentClaim().getStatus());
	}
	
	/*
	// UC 9.0
	public void testCloudStatus() {
		User user = new User("Peter");
		Claim claim = new Claim();
		user.saveToCloud();
		assertEquals("Cloud saving working", user, user.loadFromCloud());
	}
*/
		
}
