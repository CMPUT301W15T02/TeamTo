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
	
}
