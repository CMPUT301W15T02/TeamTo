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

import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

import junit.framework.TestCase;

/**
 * Tests use cases 8.X
 */

public class UseCase8Test extends TestCase {
	
	// UC 8.0, UC 8.1 (UC 8.2 - UC 8.4 are simply viewing the screen)
/*
	public void testClaimStatuses() {
		Claim claim = new Claim();
		ClaimList claims = new ClaimList();
		claims.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
			
		// UC 8.0 return claim
		claimController.submitClaim();
		claimController.returnClaim();
		assertEquals("Claim returned?", Status.RETURNED, claimController.getCurrentClaim().getStatus());
		
		// UC 8.1 approve claim
		claimController.submitClaim();
		claimController.approvedClaim();
		assertEquals("Claim approved?", Status.APPROVED, claimController.getCurrentClaim().getStatus());
	}
*/
}
