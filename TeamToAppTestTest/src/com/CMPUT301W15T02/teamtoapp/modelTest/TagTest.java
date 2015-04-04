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

import com.CMPUT301W15T02.teamtoapp.Controllers.UserController;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;

import junit.framework.TestCase;

/**
 * Tests the functionality of Tag.java
 */

public class TagTest extends TestCase {

	public void testToString() {
		String tagName = "tag1";
		Tag tag = new Tag(tagName);
		assertTrue("", tag.toString().equals(tagName));
	}
	
	public void testAddTag() {
		UserController userController = new UserController();
		int previous = userController.getTags().size();
		Tag tag1 = new Tag("hello");
		Tag tag2 = new Tag("hey there");
		userController.addTag(tag1);
		userController.addTag(tag2);
		int after = userController.getTags().size();
		int difference = after - previous;
		assertEquals("Number of tags don't add up.", previous, after - difference);
	}
	
}
