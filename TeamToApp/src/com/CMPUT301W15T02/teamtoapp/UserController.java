package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

import android.R.integer;

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
	
	public void removeTag(int position) {
		user.getTags().remove(position);
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
	
	public Tag getTag(int position) {
		return user.getTags().get(position);
	}
	
	public String getTagId(int position) {
		return getTag(position).getTagId();
	}
	
	public String getTagText(int position) {
		return getTag(position).getTagText();
	}
}
