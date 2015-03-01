package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

import android.R.integer;

public class SessionController {
	
	private User user = Session.getInstance().getCurrentUser();
	private AggregatedClaims claims = Session.getInstance().getCurrentClaims();
	
	public SessionController() {}
	
	public void addTag(Tag tag) {
		user.addTag(tag);
	}
	
	public void removeTag(Tag tag) {
		user.removeTag(tag);
	}
	
	public void switchUserType() {
		if (user.getType()) {
			user.setType(false);
		} else {
			user.setType(true);
		}
	}
	
	public void addClaim(Claim claim) {
		claims.addClaim(claim);
		CurrentClaim.getInstance().setClaim(claim);
	}
	
	public void removeClaim(int position) {
		// Needs fixing, should just pass in position
		claims.getClaims().remove(position);
	}
	
	public void setClaim(Claim claim) {
		CurrentClaim.getInstance().setClaim(claim);
	}
	
	public ArrayList<Claim> getClaims() {
		return claims.getClaims();
	}
	
}
