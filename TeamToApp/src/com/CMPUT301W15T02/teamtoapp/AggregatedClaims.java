package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

public class AggregatedClaims {

	ArrayList<Claim> claims;
	
	public AggregatedClaims() {
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
	
}
