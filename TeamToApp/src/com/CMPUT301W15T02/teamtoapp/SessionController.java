package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

import android.R.integer;

public class SessionController {
	
	private User user = Session.getInstance().getCurrentUser();
	private ClaimsList claims = Session.getInstance().getCurrentClaims();
	
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
		// TODO Need to set the current user name in the claim field, will do once we figure out login
		claims.addClaim(claim);
		ClaimSingleton.getInstance().setClaim(claim);
	}
	
	public void removeClaim(Claim claim) {
		claims.getClaims().remove(claim);
	}
	
	public void removeClaim(int position) {
		claims.getClaims().remove(position);
	}
	
	public void setClaim(Claim claim) {
		ClaimSingleton.getInstance().setClaim(claim);
	}
	
	public ArrayList<Claim> getClaims() {
		return claims.getClaims();
	}
	
}
