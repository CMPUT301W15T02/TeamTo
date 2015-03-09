package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

public class ClaimsList {

	private ArrayList<Claim> claims;
	
	
	public ClaimsList() {
		claims = new ArrayList<Claim>();
	}

	public ArrayList<Claim> getClaims() {
		return claims;
	}

	public void setClaims(ArrayList<Claim> claims) {
		this.claims = claims;
	}
	
	public void addClaim(Claim claim) {
		claims.add(claim);
	}
	
	public void removeClaim(Claim claim) {
		claims.remove(claim);
	}
	
	public Claim getClaim(Claim claim) {
		if (claims.contains(claim)) {
			return claim;
		} else {
			return null;
		}
	}
	
	// TODO: Need to test this in ClaimModelsTesting
	public Claim findClaimByID(String claimID) {
		for (Claim claim : claims) {
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
		return null; // Or should we return new Expense?
	}
	
}
