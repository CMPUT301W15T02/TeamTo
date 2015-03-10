package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

public class UserController {
	
	private User user;
	
	public UserController() {
		user = Session.getInstance().getCurrentUser();
	}
	
	public void addTag(Tag tag) {
		user.addTag(tag);
	}
	
	public void removeTag(Tag tag) {
		user.removeTag(tag);
	}
	
	public ArrayList<Tag> getTags() {
		return user.getTags();
	}
	
	public void renameTag(String tagID, String newText) {
		Tag tag = user.findTagsById(tagID);
		if (tag.equals(null)) {
			return;
		}
		user.renameTag(tag, newText);
	}
}
