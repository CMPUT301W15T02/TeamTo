package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import java.util.ArrayList;

import android.R.integer;
import android.test.AndroidTestCase;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
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
import com.CMPUT301W15T02.teamtoapp.Model.User;

/**
 * Tests use cases 3.X
 */

public class UseCase3Test extends AndroidTestCase {

	// UC 3.2
	// Test adding tags in user object 
	public void testAddTags() {
		User user = User.getInstance();
		Tag testTag = new Tag("test");
		user.addTag(testTag);
		assertTrue("Contains tag", user.getTags().contains(testTag));
		User.tearDownForTesting();
	}
	
	
	// UC 3.0
	// Test editing tags in user object
	public void testEditTags() {
		User user = User.getInstance();
		Tag tag = new Tag("Tag name");
		user.addTag(tag);
		user.renameTag(tag, "New name");
		
		assertEquals("Rename tags working?", "New name", user.getTags().get(4).getTagName());
		User.tearDownForTesting();
	}
	
	// UC 3.1
	// Test deleting tags in user object
	public void testRemoveTags() {
		User user = User.getInstance();
		int count = user.getTags().size();
		Tag tag = new Tag("New tag");
		user.addTag(tag);
		user.removeTag(tag);
		assertEquals("Tag deleted", count, user.getTags().size());
	}
	
	
	// UC 3.4
	// Test ability to filter claims by tags made by user
	public void testFilterClaimsByTags() {
		MainManager.initializeContext(mContext);
		ClaimListController claimListController = new ClaimListController();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		claimListController.addClaim(claim1);
		claimListController.addClaim(claim2);
		
		// Grab tags from user
		ArrayList<Tag> userTags = User.getInstance().getTags();
		Tag tag = userTags.get(0);
		
		// Create list of filtered tags to be saved
		ArrayList<String> filterTags = new ArrayList<String>();
		filterTags.add(tag.getTagName());
		claim1.addTag(tag.getTagID());
		
		assertEquals(1, claimListController.getFilteredClaims(filterTags).size());
	}
	

}
