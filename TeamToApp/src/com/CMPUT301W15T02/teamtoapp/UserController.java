package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;
import java.util.Observer;

import android.R.integer;

public class UserController {
	
	private User user;
	
	public UserController() {
		user = Session.getInstance().getCurrentUser();
	}
	
	public void addObserverToUser(Observer observer) {
		user.addObserver(observer);
	}
	
	public void removeObserverFromUser(Observer observer) {
		user.deleteObserver(observer);
	}
	
	public void addTag(Tag tag) {
		user.addTag(tag);
	}
	
	public void removeTag(Tag tag) {
		user.removeTag(tag);
	}
	
	public void removeTag(int position) {
		user.removeTag(user.getTags().get(position));
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
	
	public Tag findTagById(String tagID) {
		for (Tag tag: user.getTags()) {
			if (tag.getTagId().equals(tagID)) {
				return tag;
			}
		}
		return null;
	}
	
}
