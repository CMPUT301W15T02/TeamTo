/* Tests for User.java */

package com.CMPUT301W15T02.teamtoapp.test;

import com.CMPUT301W15T02.teamtoapp.Tag;
import com.CMPUT301W15T02.teamtoapp.User;

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
		String tag2Name = "tag2";
		Tag tag2 = new Tag(tag2Name);
		user.addTag(tag2);
		
		user.removeTag(tag1);
	}
	
}
