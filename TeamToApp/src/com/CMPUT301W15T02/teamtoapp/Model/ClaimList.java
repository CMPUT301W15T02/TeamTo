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
import java.util.Observer;

/**
 * Holds the current list of claims.
 *
 */

public class ClaimList extends Observable implements Observer {

	private ArrayList<Claim> claims;
	
	public ClaimList() {
		claims = new ArrayList<Claim>();
	}
	
	

	public ArrayList<Claim> getClaims() {
		return claims;
	}
	
	

	public void setClaims(ArrayList<Claim> claims) {
		this.claims = claims;
		setChanged();
		notifyObservers();
	}
	
	
	
	public void addClaim(Claim claim) {
		claims.add(claim);
		setChanged();
		notifyObservers();
	}
	
	
	
	public void removeClaim(Claim claim) {
		claims.remove(claim);
		setChanged();
		notifyObservers();
	}
	
	
	
	public Claim getClaim(Claim claim) {
		if (claims.contains(claim)) {
			return claim;
		} else {
			return null;
		}
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
	
	

	/**
	 * Notifies the ClaimList that a claim it was observing has changed.  
	 * 
	 * The ClaimList then notifies any of its listeners that it has changed as well
	 */
	@Override
	public void update(Observable observable, Object data) {
		setChanged();
		notifyObservers();
	}
	
}
