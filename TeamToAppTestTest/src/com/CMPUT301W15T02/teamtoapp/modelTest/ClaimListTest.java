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

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;

import junit.framework.TestCase;

/**
 * Tests the functionality of ClaimList.java
 */

public class ClaimListTest extends TestCase {

	public void testAddClaim() {
		ClaimList claims = new ClaimList();

		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		String claimName1 = "firstClaim";
		String claimName2 = "secondClaim";
		claim1.setClaimName(claimName1);
		claim2.setClaimName(claimName2);
		claims.addClaim(claim1);
		assertTrue("Claim not added properly", claims.getClaims().size() == 1);
		
		claims.addClaim(claim2);
		assertTrue("Claim not added properly", claims.getClaims().size() == 2);
	}
	
	public void testGetClaim() {
		ClaimList claims = new ClaimList();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		String claimName1 = "firstClaim";
		String claimName2 = "secondClaim";
		claim1.setClaimName(claimName1);
		claim2.setClaimName(claimName2);
		claims.addClaim(claim1);
		assertTrue("Didn't get right claim", claims.getClaim(claim1).equals(claim1));
		assertNull("Didn't get right claim", claims.getClaim(claim2));
		
		claims.addClaim(claim2);
		assertTrue("Didn't get right claim", claims.getClaim(claim2).equals(claim2));
	}
	
	public void testRemoveClaim() {
		ClaimList claims = new ClaimList();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		String claimName1 = "firstClaim";
		String claimName2 = "secondClaim";
		claim1.setClaimName(claimName1);
		claim2.setClaimName(claimName2);
		claims.addClaim(claim1);
		claims.addClaim(claim2);
		
		claims.removeClaim(claim2);
		assertNull("Did not remove claim2", claims.getClaim(claim2));
		claims.removeClaim(claim1);
		assertNull("Did not remove claim1", claims.getClaim(claim1));
		assertTrue("Claims not empty", claims.getClaims().size() == 0);
	}
	
	public void testFindClaimByID() {
		ClaimList claims = new ClaimList();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		String claimName1 = "firstClaim";
		String claimName2 = "secondClaim";
		claim1.setClaimName(claimName1);
		claim2.setClaimName(claimName2);
		claims.addClaim(claim1);
		claims.addClaim(claim2);
		
		String claimID = "1234";
		assertTrue("New claim not created", claims.findClaimByID(claimID).getExpenses().size() == 0);
		//
	}
	
	// RV might change
	public void testFindExpenseByID() {
		
	}
	
}










