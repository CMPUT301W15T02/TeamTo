/* User class which consists a user's list of claims and personalized tags.
 * 
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
package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;
import java.util.Observable;

public class User extends Observable {
	
	private String name;
	private boolean type; // true for claimant, false for approver?
	private ArrayList<Tag> tags;
	
	
	public User(String string) {
		this.name = string;
		tags = new ArrayList<Tag>();
		tags.add(new Tag("Shopping"));
		tags.add(new Tag("Business"));
		tags.add(new Tag("Personal"));
		tags.add(new Tag("Recreation"));
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
	
	public void addTag(Tag tag) {
		if( !tagIsIn(tag) ) {
			tags.add(tag);
			setChanged();
			notifyObservers();
		}
	}
	
	// Helper method to check if tag already in tags (by name, not ID)
	public boolean tagIsIn(Tag tag) {
		for(Tag t : tags) {
			if(t.getTagName() == tag.getTagName()) {
				return true;
			}
		}
		return false;
	}

	public void removeTag(Tag tag) {
		tags.remove(tag);
		setChanged();
		notifyObservers();
	}
	
	public void renameTag(Tag tag, String newText) {
		tag.setTagName(newText);
		setChanged();
		notifyObservers();
	}

}
