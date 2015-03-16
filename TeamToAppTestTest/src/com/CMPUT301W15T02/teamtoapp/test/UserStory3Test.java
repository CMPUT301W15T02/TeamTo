package com.CMPUT301W15T02.teamtoapp.test;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;

import junit.framework.TestCase;

public class UserStory3Test extends TestCase {

	// UC 3.2, UC 3.3
	public void testAddTags() {
		User user = new User("Kent Brockman");
		Tag testTag = new Tag("test");
		user.addTag(testTag);
		assertTrue("Contains tag", user.getTags().contains(testTag));
	}
	
	// UC 3.1
	public void testRemoveTags() {
		User user = new User("NAME");
		Tag testTag = new Tag("test");
		user.addTag(testTag);
		int initialSize = user.getTags().size();
		user.removeTag(testTag);
		assertEquals("Removed tag?", initialSize-1, user.getTags().size());
	}
	
	
	// UC 3.0
	public void testEditTags() {
		User user = new User("NAME");
		Tag tag = new Tag("Tag name");
		user.addTag(tag);
		user.renameTag(tag, "New name");
		
		assertEquals("Rename tags working?", "New name", user.getTags().get(4).getTagName());
	}
	
	// UC 3.3
	//As a claimant, I want to filter the list of expense claims by tags, 
	//to show only those claims that have at least one tag matching any of a given set of one or more filter tags.
	public void testAddTagToClaim() {
		// User will add a new tag, then claim will be assigned the new tag 
		Claim claim = new Claim();
		Tag tag = new Tag("Tag name");
		claim.addTag(tag);
		assertTrue("Tag added to claims", claim.getTags().contains(tag));
	}
	
	public void testRemoveTagFromClaim() {
		Claim claim = new Claim();
		Tag tag = new Tag("Tag name");
		claim.addTag(tag);
		claim.removeTag(tag);
		assertEquals("Tag actually removed?", 0, claim.getTags().size());
		assertFalse("Tag not there", claim.getTags().contains(tag));
	}
	
	/*
	// UC 3.4
	public void testFilterByTag() {
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		ClaimList claims = new ClaimList();
		Tag tag = new Tag("Tag name");
		claim2.addTag(tag);
		claims.addClaim(claim1);
		claims.addClaim(claim2);
		ClaimListController claimListController = new ClaimListController();
		assertTrue("Filter working", claimListController.getClaimsWithTags("Tag name").contains(claim1));
		assertFalse("Tag filter working?", claimListController.getClaimsWithTags("Tag name").contains(claim2));
	}
	*/
}
