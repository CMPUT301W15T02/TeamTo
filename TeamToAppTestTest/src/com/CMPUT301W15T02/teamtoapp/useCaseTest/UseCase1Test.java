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

package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import android.test.AndroidTestCase;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Destination;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorNewestFirst;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorOldestFirst;

/**
 * Tests use cases 1.X
 */

public class UseCase1Test extends AndroidTestCase {

	// UC 1.0
	// Tests adding a claim (i.e. through  claim list controller)
	public void testAddClaim () {
		MainManager.initializeContext(mContext);
		// Need to set username first before dealing with any claims.
		User.getInstance().setName("New name");
		
		ClaimListController claims = new ClaimListController();
		Claim claim = new Claim();

		assertEquals("New name", claims.getUserName());
		claims.addClaim(claim);
		
		ClaimController controller = new ClaimController(claim.getClaimId());
		
		// Preconditions: Claimant knows name, starting/ending date, destination, and reason.
		String claimName = "New Claim";
		GregorianCalendar start_date = new GregorianCalendar();
		GregorianCalendar end_date = new GregorianCalendar();
		controller.setClaimName(claimName);
		controller.setStartDate(start_date);
		controller.setEndDate(end_date);
		
		// Check if attributes are saved
		assertEquals("Name is not equal", controller.getClaimName(), claimName);
		assertEquals("Start date is not equal", controller.getStartDate(), start_date);
		assertEquals("End date is not equal", controller.getEndDate(), end_date);
		
		String dest = "some destination";
		String reason = "some reason";
		controller.addDestination(dest, reason, 53.0, -113.0);
		
		// Check if destination is saved
		assertTrue("Destination and reason were not added.", controller.getDestinations().size() == 1);
		ClaimList.tearDownForTesting();
	}
	// UC 1.0.2
	public void testAddTagToClaim() {
		// User will add a new tag, then claim will be assigned the new tag 
		Claim claim = new Claim();
		Tag tag = new Tag("Tag name");
		User.getInstance().addTag(tag);
		claim.addTag(tag.getTagID());
		assertTrue("Tag added to claims", claim.getTags().contains(tag));
		User.tearDownForTesting();
	}
	
	
	// uc 3.1
	// Test if user can remove an existing tag
	public void testRemoveTagFromClaim() {
		Claim claim = new Claim();
		Tag tag = new Tag("Some tag");
		claim.addTag(tag.getTagID());
		claim.removeTag(tag.getTagID());
		assertEquals("Tag actually removed?", 0, claim.getTags().size());
		assertFalse("Tag not there", claim.getTags().contains(tag));
	}
	
	// UC 1.1
	 public void testEditClaim() {
		// Check whether claims can be edited and saved depending on claim status
		Claim claim = new Claim();
		
		
		claim.setClaimName("in progress");
		assertEquals("Name changed when in progress", "in progress", claim.getClaimName());
		
		claim.setClaimName("submitted name");
		assertEquals("Claim name changed.", "submitted name", claim.getClaimName());
		
		claim.setClaimName("returned name");
		assertEquals("Claim name changed when returned", "returned name", claim.getClaimName());
		ClaimList.tearDownForTesting();
	}
	
	
	// UC 1.2
	public void testDeleteClaim() {
		// Test whether claim can be deleted
		ClaimList claims = ClaimList.getInstance();
		Claim claim = new Claim();
		claims.addClaim(claim);
		// Remove claim from manager
		claims.removeClaim(claim);
		assertFalse("user still has this claim!", claims.getClaims().contains(claim));
		ClaimList.tearDownForTesting();
		
	}
	
	// UC 2.0
	// Tests whether claims can be sorted by start date
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
		ClaimList.tearDownForTesting();
	}
	
	// UC 1.3
	// Test ability to add/remove destination 
	public void testDestinationLocation() {
		Destination destination = new Destination("Bahamas", "personal", 3.74, -12.94);
		Claim claim = new Claim();
		claim.addDestination(destination);
		assertEquals("Destination added", 1, claim.getDestinations().size());

		assertEquals("Destination added", destination, claim.getDestinations().get(0));

		claim.removeDestination(destination);
		assertEquals("Destination removed", 0, claim.getDestinations().size());
		assertFalse("Destination removed", claim.getDestinations().contains(destination));

	}
	
}
