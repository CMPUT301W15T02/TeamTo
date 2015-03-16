package com.CMPUT301W15T02.teamtoapp.modelTest;

import com.CMPUT301W15T02.teamtoapp.Model.Tag;

import junit.framework.TestCase;

public class TagTest extends TestCase {

	public void testToString() {
		String tagName = "tag1";
		Tag tag = new Tag(tagName);
		assertTrue("", tag.toString().equals(tagName));
	}
	
}
