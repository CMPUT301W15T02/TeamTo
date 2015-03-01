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

public class User {
	private String name;
	private boolean type; // true for claimant, false for approver?
	private ArrayList<Tag> tags;
	
	public User(String string) {
		this.name = string;
		tags = new ArrayList<Tag>();
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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
	}
	
	
	
	public void addTag(Tag tag) {
		tags.add(tag);
	}


	
	public void removeTag(Tag tag) {
		tags.remove(tag);
	}
	
	

	public void editTag(Tag prev_tag, Tag new_tag) {
		if (tags.contains(prev_tag)) {
			int index = tags.indexOf(prev_tag);
			// Replace tag with new tag at original index
			tags.set(index, new_tag);
		}
	}





}
