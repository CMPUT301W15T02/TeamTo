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
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Session;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorOldestFirst;

import android.R.integer;

public class ClaimListController {

	private ClaimList claims;
	private String userName;
	
	public ClaimListController() {
		claims = Session.getInstance().getCurrentClaims();
		userName = Session.getInstance().getCurrentUser().getName();
	}
	
	public void addClaim(Claim claim) {
		claim.setUser(userName);
		claims.addClaim(claim);
		claim.addObserver(claims);
	}
	
	public void removeClaim(Claim claim) {
		claims.removeClaim(claim);
		claim.deleteObserver(claims);
	}
	
	public Claim getClaim(int position) {
		return claims.getClaims().get(position);
	}

	public ArrayList<Claim> getClaims() {
		return claims.getClaims();
	}
	
	public void addObserverToClaimList(Observer observer) {
		claims.addObserver(observer);
	}
	
	public void removeObserverFromClaimList(Observer observer) {
		claims.deleteObserver(observer);
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
	
	
}
