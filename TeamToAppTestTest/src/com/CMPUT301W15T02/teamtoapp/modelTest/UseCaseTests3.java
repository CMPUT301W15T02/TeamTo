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

package com.CMPUT301W15T02.teamtoapp.modelTest;

import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Session;

import junit.framework.TestCase;

public class UseCaseTests3 extends TestCase {

	public void testAddClaim() {
		ClaimListController claimListController = new ClaimListController();
		Claim claim = new Claim();
		claimListController.addClaim(claim);
		assertEquals("Added to claimList", 1, claimListController.getClaims().size());
		assertTrue("claim should exist", claimListController.getClaims().contains(claim));
		Session.tearDownForTesting();
	}
	
	public void testDeleteClaim() {
		ClaimListController claimListController = new ClaimListController();
		Claim claim = new Claim();
		claimListController.addClaim(claim);
		claimListController.removeClaim(claim);
		assertEquals("No claims in claimlist", 0, claimListController.getClaims().size());
		assertFalse("Contains claim?", claimListController.getClaims().contains(claim));
	}
	
}
