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
import java.util.Observer;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Session;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;

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
	
	public void renameTag(Tag tag, String newText) {
		if (tag.equals(null)) {
			return;
		}
		user.renameTag(tag, newText);
	}
	
	public Tag getTag(int position) {
		return user.getTags().get(position);
	}
	
	
	public void updateTags() {
		ArrayList<Tag> userTags = getTags(); // userTags
		ClaimListController claimListController = new ClaimListController();
		ArrayList<Claim> claims = claimListController.getClaims();
		ArrayList<Tag> newTags = new ArrayList<Tag>();
		for (Claim claim: claims) {
			for (Tag tag: claim.getTags()) {
				if (userTags.contains(tag)) {
					newTags.add(tag);
				}
			}
			claim.setTags(newTags);
		}
	}
	
}
