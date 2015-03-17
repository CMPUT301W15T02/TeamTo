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

package com.CMPUT301W15T02.teamtoapp.Model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * 
 * User class consists of the name of the user, the type of user (claimant or approver), and a personal list of tags.
 *
 */

public class User extends Observable {
	
	private String name;
	private boolean type; // true for claimant, false for approver
	private ArrayList<Tag> tags;
	
	private static User instance = null;
	
	
	
	
	/**
	 * User constructor
	 * @param string	the name of the user
	 */
	private User() {
		this.name = "";
		tags = new ArrayList<Tag>();
		type = true;
		// Default tags for now
		tags.add(new Tag("Shopping"));
		tags.add(new Tag("Business"));
		tags.add(new Tag("Personal"));
		tags.add(new Tag("Recreation"));
	}
	
	
	public static User getInstance() {
		if (instance == null) {
			instance = new User();
		}
		return instance;
	}
	
	
	public static void setUser(User user) {
		instance = user;
	}
	
	

	public String getName() {
		return name;
	}
	
	
	
	public void setName(String name) {
		this.name = name;
		setChanged();
		notifyObservers();
	}
	
	

	public boolean getType() {
		return type;
	}
	
	
	
	/**
	 * Sets the type of user, false is approver mode, true for claimant
	 * @param type 
	 */
	public void setType(boolean type) {
		this.type = type;
	}
	
	

	public ArrayList<Tag> getTags() {
		return tags;
	}
	
	

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Adds a tag to the users personal list of tags only if it is unique to avoid duplicates
	 * @param tag
	 */
	public void addTag(Tag tag) {
		if (!tags.contains(tag)) {
			tags.add(tag);
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Removes a tag from the users personal list of tags only if it exists
	 * @param tag
	 */
	public void removeTag(Tag tag) {
		if (tags.contains(tag)) {
			tags.remove(tag);
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Renames a tag
	 * @param tag		The tag to be renamed
	 * @param newText	The new name for the tag
	 */
	public void renameTag(Tag tag, String newText) {
		if (tags.contains(tag)) {
			tag.setTagName(newText);
			setChanged();
			notifyObservers();
		}
	}

}
