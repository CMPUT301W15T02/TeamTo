package com.CMPUT301W15T02.teamtoapp.Model;

import java.util.ArrayList;

import android.util.Log;


public class ApproverClaims {

	private ArrayList<Claim> claims;
	private static ApproverClaims instance = null;
	
	
	private ApproverClaims() {
		claims = new ArrayList<Claim>();
	}
	
	
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
}
