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

import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Controllers.SessionController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorNewestFirst;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorOldestFirst;

import junit.framework.TestCase;

/**
 * Tests use cases 1.X
 */

public class UseCase1Test extends TestCase {

	// UC 1.0
	/**
	 * Tests adding a claim (i.e. though session controller)
	 */
	public void testAddClaim () {
		SessionController sessionController = new SessionController();
		sessionController.setUser("New name");
		ClaimListController claims = new ClaimListController();
		Claim claim = new Claim();
		claims.getUserName();
		// added getUserName to ClaimListController just to see if username is the same - method only for testing.
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
		
		assertEquals("Name is not equal", controller.getClaimName(), claimName);
		assertEquals("Start date is not equal", controller.getStartDate(), start_date);
		assertEquals("End date is not equal", controller.getEndDate(), end_date);
		// TODO: Why does this not work? You updated the controller object but not the one you stored in the ClaimList
		// assertEquals("Claim name not changed", claimName, claims.getClaim(claim).getClaimName());
		
		String dest = "some destination";
		String reason = "some reason";
		controller.addDestination(dest, reason);
		// TODO: Why isn't this working?  You had it backwards
		assertTrue("No destination and reason were added.", controller.getDestinations().size() != 0);
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
	
}
