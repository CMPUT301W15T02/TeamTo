package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import java.util.ArrayList;

import junit.framework.TestCase;
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

	// UC 3.0
	public void testAddTags() {
		User user = User.getInstance();
		Tag testTag = new Tag("test");
		user.addTag(testTag);
		assertTrue("Contains tag", user.getTags().contains(testTag));
		User.tearDownForTesting();
	}
	
	
	// UC 3.0
	public void testEditTags() {
		User user = User.getInstance();
		Tag tag = new Tag("Tag name");
		user.addTag(tag);
		user.renameTag(tag, "New name");
		
		assertEquals("Rename tags working?", "New name", user.getTags().get(4).getTagName());
		User.tearDownForTesting();
	}
	
	// UC 3.3
	//As a claimant, I want to filter the list of expense claims by tags, 
	//to show only those claims that have at least one tag matching any of a given set of one or more filter tags.
	
	public void testAddTagToClaim() {
		// User will add a new tag, then claim will be assigned the new tag 
		Claim claim = new Claim();
		Tag tag = new Tag("Tag name");
		User.getInstance().addTag(tag);
		claim.addTag(tag.getTagID());
		assertTrue("Tag added to claims", claim.getTags().contains(tag));
		User.tearDownForTesting();
	}
	
	public void testRemoveTagFromClaim() {
		Claim claim = new Claim();
		Tag tag = new Tag("Some tag");
		claim.addTag(tag.getTagID());
		claim.removeTag(tag.getTagID());
		assertEquals("Tag actually removed?", 0, claim.getTags().size());
		assertFalse("Tag not there", claim.getTags().contains(tag));
	}
	
	public void testFilterClaimsByTags() {
		MainManager.initializeContext(mContext);
		ClaimListController claimListController = new ClaimListController();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		claimListController.addClaim(claim1);
		claimListController.addClaim(claim2);
		ArrayList<Tag> userTags = User.getInstance().getTags();
		Tag tag = userTags.get(0);
		ArrayList<String> filterTags = new ArrayList<String>();
		filterTags.add(tag.getTagName());
		claim1.addTag(tag.getTagID());
		
		assertEquals(1, claimListController.getFilteredClaims(filterTags).size());
	}
	

}
