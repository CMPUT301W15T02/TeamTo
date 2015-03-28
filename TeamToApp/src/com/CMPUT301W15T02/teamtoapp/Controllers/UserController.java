/* 
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

package com.CMPUT301W15T02.teamtoapp.Controllers;

import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;


/**
 * 
 * Responsible for communicating between any associated views and the user object
 *
 */


public class UserController {
	
	private User user;
	
	public UserController() {
		user = User.getInstance();
	}
	
	/**
	 * Adds a tag to the current user
	 * @param tag
	 */
	public void addTag(Tag tag) {
		user.addTag(tag);
	}
	
	/**
	 * Removes a tag from the current user and updates all associated claims
	 * @param tag	the tag to be removed
	 */
	public void removeTag(Tag tag) {
		user.removeTag(tag);
	}
	
	/**
	 * Removes a tag from the current user and updates all associated claims
	 * @param tag	the position of the tag to be removed
	 */
	public void removeTag(int position) {
		user.removeTag(user.getTags().get(position));
	}
	
	public ArrayList<Tag> getTags() {
		return user.getTags();
	}
	
	/**
	 * Renames one of the users tags and updates all associated claims
	 * @param tag		tag to be renamed
	 * @param newText	new name for tag
	 */
	public void renameTag(Tag tag, String newText) {
		if (tag.equals(null)) {
			return;
		}
		user.renameTag(tag, newText);
	}
	
	public Tag getTag(int position) {
		return user.getTags().get(position);
	}
	

	
	/**
	 * Adds a listener to the current user
	 * @param listener
	 */
	public void addListenerToUser(Listener listener) {
		user.addListener(listener);
	}
	
	
	/**
	 * Removes a listener from the current user
	 * @param listener
	 */
	public void removeListenerFromUser(Listener listener) {
		user.removeListener(listener);
	}


	
}
