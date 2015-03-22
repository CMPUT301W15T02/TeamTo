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

import android.util.Log;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;


/**
 * 
 * Controller for editing/adding/removing claims from the list of claims
 *
 */
public class ClaimListController {

	private ClaimList claims;
	private String userName;
	
	public ClaimListController() {
		claims = ClaimList.getInstance();
		userName = User.getInstance().getName();
	}
	
	// for testing purposes - UseCase1Test.java
	public String getUserName() {
		return userName;
	}
	
	// TODO clean this up
	public void addClaim(Claim claim) {
		claim.setUserName(userName);
		claims.addClaim(claim);
		MainManager.addClaim(claim);
	}
	
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
	
	
	public void addListenerToClaimList(Listener listener) {
		claims.addListener(listener);
	}
	
	public void removeListenerFromClaimList(Listener listener) {
		claims.removeListener(listener);
	}
	
	// DUMMY METHOD won't need this when the networking is working
	public ArrayList<Claim> getSubmittedClaims() {
		ArrayList<Claim> submittedClaims = new ArrayList<Claim>();
		for (Claim claim : claims.getClaims()) {
			if (claim.getStatus() == Status.SUBMITTED) {
				submittedClaims.add(claim);
			}
		}
		return submittedClaims;
	}

	public ArrayList<Tag> getClaimsWithTags(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Claim> getSubmittedClaimsFromMainManager() {
		return MainManager.getSubmittedClaims();
		
	}
	
	public void claimEditBackPressed(Claim claim) {
		if (claim.getClaimName().equals("") && claim.getDestinations().size() == 0) {
			// do nothing
		} else if (!getClaims().contains(claim)) {
			addClaim(claim);
			Log.i("CLAIMID",claim.getClaimId());
			MainManager.addClaim(claim);
		} else {
			MainManager.updateClaim(claim);
		}
		return;
	}
	
	
}
