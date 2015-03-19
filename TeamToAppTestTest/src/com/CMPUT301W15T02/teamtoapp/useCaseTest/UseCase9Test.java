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

import com.CMPUT301W15T02.teamtoapp.LocalDataManager;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

import junit.framework.TestCase;

/**
 * Tests use cases 9.X
 */

public class UseCase9Test extends TestCase {
	
	// UC 9.0
	
	/*// Not implemented yet
	public void testLoadSaveUser() {
		User user = new User("testuser");
		DataManager.saveUser("user");
		
		assertEquals("Loading and saving user working?", user, DataManager.loadUser("testuser"));
	}
	
	// Not implemented yet
	public void testLoadSaveClaims() {
		Claim claim = new Claim();
		ClaimList claimList = new ClaimList();
		claimList.addClaim(claim);
		
		DataManager.saveClaims(claimList);
		
		assertTrue("Loading and saving claims working?", DataManager.loadClaims().getClaims().contains(claim));
	}
	*/
	
}
