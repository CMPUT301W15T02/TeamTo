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

import android.R.integer;

public class User {
	private String username;
	private ArrayList<Claim> claims;
	private ArrayList<String> tags;
	
	public User(String username) {
		this.setUsername(username);
		claims = new ArrayList<Claim>();
		tags = new ArrayList<String>();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void addClaim(Claim claim) {
		claims.add(claim);
	}
	
	public void removeClaim(Claim claim) {
		if (claims.contains(claim)) {
			claims.remove(claim);
		}
	}
	
	public void returnClaim(Claim claim) {
		claim.setStatus(Claim.Status.RETURNED);
	}
	
	public void submitClaim(Claim claim) {
		
		claim.setStatus(Claim.Status.SUBMITTED);
	}
	
	public void approveClaim(Claim claim) {
		claim.setStatus(Claim.Status.APPROVED);
	}
	
	public String getATag(String tag) {
		// Return an existing tag
		if (tags.contains(tag)) {
			return tag;
		} else {
			return null;
		}
	}
	public ArrayList<String> getTags() {
		// Return the array list of tags
		return tags;
	}
	
	public void addTag(String tag) {
		// Add new tag created by the user
		tags.add(tag);
	}

	public void removeTag(String tag) {
		tags.remove(tag);
	}
	
	public void editTag(String prev_str, String new_str) {
		if (tags.contains(prev_str)) {
			int index = tags.indexOf(prev_str);
			// Replace tag with new tag at original index
			tags.set(index, new_str);
		}
	}
	
	public Claim getClaimPos(int position) {
		// Return claim from a specific position of the users' claim list
		return claims.get(position);
	}
	
	public Claim getClaim(Claim claim){
		// Return a claim requested, not specifying position
		if (claims.contains(claim)) {
			return claim;
		} else {
			return null;
		}
	}

	public ArrayList<Claim> getClaimsWithTags(String string) {
		ArrayList<Claim> claimsWithTags = new ArrayList<Claim>(); 
		for (int i = 0; i < claims.size(); i++) {
			// If claim has matching tag, add to list, then return the list.
			if (claims.get(i).getTags().contains(string)) {
				claimsWithTags.add(claims.get(i));
			}
		}
		return claimsWithTags;
	}
	
	// Not really sure how exactly we will save/load a users' claims at the moment.
	public void saveToCloud() {
		// TODO Incomplete.
		
	}

	public User loadFromCloud() {
		// TODO Incomplete. 
		return null;
	}


}
