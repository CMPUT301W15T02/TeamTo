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

import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;

/**
 * Holds the current list of claims.
 *
 */

public class ClaimList implements Listener {

	
	private ArrayList<Claim> claims;
	protected transient ArrayList<Listener> listeners = null;
	private static ClaimList instance = null;
	
	
	private ClaimList() {
		claims = new ArrayList<Claim>();
		listeners = new ArrayList<Listener>();
	}
	
	
	public static ClaimList getInstance() {
		if (instance == null) {
			instance = new ClaimList();
		}
		return instance;
	}
	
	
	// To prevent issues from serialization make sure all listeners are initialized
	// so that all changes are saved to the disk except the listeners themselves
	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	
	// This will be called inside activities when changes occur.
	protected void notifyListeners() {
		for (Listener listener: getListeners()) {
			listener.update();
		}	
	}
	
	
	public void addListener(Listener listener) {
		getListeners().add(listener);
	}
	
	
	public void removeListener(Listener listener) {
		getListeners().remove(listener);
	}
	

	public ArrayList<Claim> getClaims() {
		return claims;
	}
	
	

	public void setClaims(ArrayList<Claim> claims) {
		this.claims = claims;
		notifyListeners();

	}
	
	
	
	public void addClaim(Claim claim) {
		claim.addListener(this);
		claims.add(claim);
		// notify listeners that claim has been updated (addition)
		notifyListeners();

	}
	
	
	public void removeClaim(Claim claim) {
		claim.removeListener(this);
		claims.remove(claim);
		// notify listeners that claim has been updated (deleted)
		notifyListeners();

	}

	
	/**
	 * Responsible for finding a claim by its ID, if one is not found it returns a new claim
	 * @param claimID 	The ID of a claim
	 * @return 			A claim, either one that was found or a new claim
	 */
	public Claim findClaimByID(String claimID) {
		for (Claim claim : claims) {
			if (claim.getClaimId().equals(claimID)) {
				return claim;
			}
		}
		// if doesn't exist, return new claim.
		return new Claim();	
	}
	
	
	
	/**
	 * Responsible for finding an expense by its ID, if one is not found it returns a new claim
	 * @param expenseID The ID of a claim
	 * @return 			An expense, either one that was found or a new expense
	 */
	public Expense findExpenseByID(String expenseID) {
		for (Claim claim : claims) {
			for (Expense expense: claim.getExpenses()) {
				if (expense.getExpenseId().equals(expenseID)) {
					return expense;
				}
			}
		}
		return new Expense(); // Or should we return new Expense?
	}


	@Override
	public void update() {
		notifyListeners();
		
	}

	
	
}
