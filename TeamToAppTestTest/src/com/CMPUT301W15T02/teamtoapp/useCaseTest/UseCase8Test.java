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

import junit.framework.TestCase;
import android.test.AndroidTestCase;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Controllers.ApproverController;
import com.CMPUT301W15T02.teamtoapp.Model.ApproverClaims;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

/**
 * Tests use cases 8.X
 */

public class UseCase8Test extends AndroidTestCase {
	
	// UC 8.0, UC 8.1 (UC 8.2 - UC 8.4 are simply viewing the screen)

	public void testApproveClaim() {
		MainManager.initializeContext(mContext);
		ApproverClaims approverClaims = ApproverClaims.getInstance();
		Claim claim = new Claim();
		approverClaims.getClaims().add(claim);
		
		ApproverController approverController = new ApproverController();
		approverController.approveClaim(claim, "comment");
		
		// Save current index of claim added
		int index = approverController.getClaims().indexOf(claim);
		assertEquals(Status.APPROVED, approverController.getClaim(index).getStatus());
		approverClaims.tearDownForTesting();
	}
	
	public void testReturnClaim() {
		MainManager.initializeContext(mContext);
		ApproverClaims approverClaims = ApproverClaims.getInstance();
		Claim claim = new Claim();
		approverClaims.getClaims().add(claim);
		ApproverController approverController = new ApproverController();
		approverController.returnClaim(claim, "comment");
		
		// Save current index of claim added
		int index = approverController.getClaims().indexOf(claim);
		assertEquals(Status.RETURNED, approverController.getClaim(index).getStatus());
		approverClaims.tearDownForTesting();
	}

}
