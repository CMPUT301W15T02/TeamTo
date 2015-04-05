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


import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;

/**
 * 
 * Controller for editing/adding/removing claims from the list of claims
 * 
 * @author Kyle Carlstrom, Raman Dhatt
 *
 */

public class ClaimListController {

	private ClaimList claims;
	private String userName;
	
	public ClaimListController() {
		claims = ClaimList.getInstance();
		userName = User.getInstance().getName();
	}
	
	
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Save new claim into local storage/ online server
	 * @param claim - new claim to be added and saved
	 */
	public void addClaim(Claim claim) {
		claim.setUserName(userName);
		claims.addClaim(claim);
		MainManager.addClaim(claim);
	}
	
	
	/**
	 * Remove claim from local storage/ online server
	 * @param claim - existing claim to be removed
	 */
	public void removeClaim(Claim claim) {
		claims.removeClaim(claim);
		MainManager.removeClaim(claim);
	}
	
	public Claim getClaim(int position) {
		return claims.getClaims().get(position);
	}

	public ArrayList<Claim> getClaims() {
		return claims.getClaims();
	}
	
	public ClaimList getClaimList() {
		return claims;
	}
	
	
	/**
	 * Adds a listener to the claim list
	 * @param listener - listener object to be added
	 */
	public void addListenerToClaimList(Listener listener) {
		claims.addListener(listener);
	}
	
	
	/**
	 * Removes listener to the claim list
	 * @param listener - listener object to be removed
	 */
	public void removeListenerFromClaimList(Listener listener) {
		claims.removeListener(listener);
	}
	

	/**
	 * If back button pressed from ClaimEditActivity.java
	 * Make sure to add the claim if name and/or destination is entered
	 * to the local storage/server
	 * @param claim - new/old claim to be checked to see if's been changed
	 */
	public void claimEditBackPressed(Claim claim) {
		if (claim.getClaimName().equals("") && claim.getDestinations().size() == 0) {
			// do nothing
		} else if (!getClaims().contains(claim)) {
			// If claim doesn't exist, add claim
			addClaim(claim);
			MainManager.addClaim(claim);
		} else {
			MainManager.updateClaim(claim);
		}
		return;
	}
	
	
	/**
	 * Grabs filtered claims based on tags selected by user
	 * 
	 * @param filterTags - tags chosen by user to filter claims
	 * @return newClaims - filtered claims
	 */
	public ArrayList<Claim> getFilteredClaims(ArrayList<String> filterTags) {
		ArrayList<Claim> newClaims = new ArrayList<Claim>();
		for (Claim claim: ClaimList.getInstance().getClaims()) {
			for (Tag tag : claim.getTags()) {
				// If filterTags list has the same tag as the claim
				// ad that claim into the new claim list to be returned
				if (filterTags.contains(tag.getTagName())) {
					newClaims.add(claim);
					break;
				}
			}
		}
		return newClaims;
	}
	
	
}
