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

package com.CMPUT301W15T02.teamtoapp.modelTest;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;

/**
 * Tests the functionality of User.java
 */

public class UserTest extends TestCase {

	public void testAddTag() {
		User user = User.getInstance();
		
		String tag1Name = "tag1";
		Tag tag1 = new Tag(tag1Name);
		user.addTag(tag1);
		assertTrue("Tag1 not added", user.getTags().contains(tag1));
		
		String tag2Name = "tag2";
		Tag tag2 = new Tag(tag2Name);
		user.addTag(tag2);
		assertTrue("Tag2 not added", user.getTags().contains(tag2));
		
		String tag3Name = "Shopping";
		Tag tag3 = new Tag(tag3Name);
		user.addTag(tag3);
		assertTrue("Tag3 not added", user.getTags().contains(tag3));
	}
	
	public void testRemoveTag() {
		User user = User.getInstance();
		String tag1Name = "tag1";
		Tag tag1 = new Tag(tag1Name);
		user.addTag(tag1);
		assertTrue("Tag not added", user.getTags().contains(tag1));
		
		// Test remove default tag (made in constructor)
		user.removeTag(tag1);
		assertFalse("tag1 wasn't removed", user.getTags().contains(tag1));
		
		int sizeBefore = user.getTags().size();
		user.removeTag(user.getTags().get(1));
		assertTrue("Default tag not removed", user.getTags().size() == sizeBefore-1);
	}
	
	public void testRenameTag() {
		User user = User.getInstance();
		String tag1Name = "tag1";
		Tag tag1 = new Tag(tag1Name);
		user.addTag(tag1);
		assertTrue("Tag exists", user.getTags().contains(tag1));
		
		// Rename tag
		String newTagName1 = "newTag";
		user.renameTag(tag1, newTagName1);
		int index = user.getTags().indexOf(tag1);
		assertEquals("Tag name didn't change", newTagName1, user.getTags().get(index).getTagName());
		
		
	}
	
}








