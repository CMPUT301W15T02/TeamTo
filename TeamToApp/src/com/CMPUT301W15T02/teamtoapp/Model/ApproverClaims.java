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

import android.util.Log;

/**
 * Singleton of claims for the approver mode.
 * 
 * @author Kyle Carlstrom
 */

public class ApproverClaims {

	private ArrayList<Claim> claims;
	private static ApproverClaims instance = null;
	
	
	/**
	 * Private constructor for list of submitted claims
	 * - singleton
	 */
	private ApproverClaims() {
		claims = new ArrayList<Claim>();
	}
	
	
	/**
	 * Checks if singleton is instantiated
	 * @return instance of ApproveClaims
	 */
	public static ApproverClaims getInstance() {
		if (instance == null) {
			instance = new ApproverClaims();
		}
		return instance;
	}
	
	public void setClaims(ArrayList<Claim> claims) {
		this.claims = claims;

	}
	
	public ArrayList<Claim> getClaims() {
		return claims;
	}
	
	/** Searches for specific claim based on claimID */
	public Claim findClaimByID(String claimID) {
		Log.i("APPCLAIM", claimID);
		for (Claim claim : claims) {
			Log.i("LOOKCLAIM", claim.getClaimId());
			if (claim.getClaimId().equals(claimID)) {
				return claim;
			}
		}
		// if doesn't exist, return new claim.
		return new Claim();	
	}
	
	/** Searches for specific expense based on expenseID */
	public Expense findExpenseByID(String expenseID) {
		for (Claim claim : claims) {
			for (Expense expense: claim.getExpenses()) {
				if (expense.getExpenseId().equals(expenseID)) {
					return expense;
				}
			}
		}
		return new Expense();
	}
	
	/**
	 * Used for testing
	 */
	public void tearDownForTesting() {
		instance = null;
	}
	
}
