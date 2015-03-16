/* Tests for User.java */

package com.CMPUT301W15T02.teamtoapp.modelTest;

import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	public void testAddTag() {
		String userName = "User1";
		User user = new User(userName);
		assertTrue("Tag list not empty", user.getTags().size() == 4);
		
		String tag1Name = "tag1";
		Tag tag1 = new Tag(tag1Name);
		user.addTag(tag1);
		assertTrue("Tag1 not added", user.getTags().size() == 5);
		
		String tag2Name = "tag2";
		Tag tag2 = new Tag(tag2Name);
		user.addTag(tag2);
		assertTrue("Tag2 not added", user.getTags().size() == 6);
		
		String tag3Name = "Shopping";
		Tag tag3 = new Tag(tag3Name);
		user.addTag(tag3);
		assertFalse("Tag3 was added and it was already in the list", user.getTags().size() == 7);
	}
	
	public void testRemoveTag() {
		String userName = "User1";
		User user = new User(userName);
		String tag1Name = "tag1";
		Tag tag1 = new Tag(tag1Name);
		user.addTag(tag1);
		assertTrue("Tag not added", user.getTags().size() == 5);
		
		// Test remove default tag (made in constructor)
		user.removeTag(tag1);
		assertTrue("tag1 wasn't removed", user.getTags().size() == 4);
		ArrayList<Tag> tags = user.getTags();
		user.removeTag(user.getTags().get(1));
		assertTrue("Default tag not removed", user.getTags().size() == 3);
	}
	
	public void testRenameTag() {
		String userName = "User1";
		User user = new User(userName);
		String tag1Name = "tag1";
		Tag tag1 = new Tag(tag1Name);
		user.addTag(tag1);
		String newTagName1 = "newTag";
		user.renameTag(tag1, newTagName1);
		assertTrue("New tag name not changed", user.getTags().get(4).getTagName().equals(newTagName1));
		
		// WHY DOES THIS WORK
		String newTagName3 = "Jello";
		String newTagName2 = "Hello";
		Tag newTag = new Tag(newTagName2);
		user.renameTag(newTag, newTagName3);
	}
	
}








