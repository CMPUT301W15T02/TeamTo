package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

import android.R.integer;

public class ClaimListController {

	private ClaimsList claims;
	
	public ClaimListController() {
		claims = Session.getInstance().getCurrentClaims();
	}
	
	public void addClaim(Claim claim) {
		claims.addClaim(claim);
	}
	
	public void removeClaim(Claim claim) {
		claims.removeClaim(claim);
	}
	
	public Claim getClaim(int position) {
		return claims.getClaims().get(position);
	}

	public ArrayList<Claim> getClaims() {
		return claims.getClaims();
	}
	
}
