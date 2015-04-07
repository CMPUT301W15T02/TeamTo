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

import android.test.AndroidTestCase;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;

/**
 * Tests use cases 7.X
 */

public class UseCase7Test extends AndroidTestCase {
	

	// UC 7.0
	public void testClaimStatuses() {
		MainManager.initializeContext(mContext);
		Claim claim = new Claim();
		ClaimList claims = ClaimList.getInstance();
		claims.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
		
		assertEquals("Claims match up?", claim, claims.findClaimByID(claim.getClaimId()));
		// UC 7.0 submit claim
		claimController.submitClaim();
		assertEquals("Claim status submitted?", Status.SUBMITTED, claimController.getCurrentClaim().getStatus());
		ClaimList.tearDownForTesting();
	}
}
