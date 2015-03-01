package com.CMPUT301W15T02.teamtoapp;

public class SessionController {
	
	User user = Session.getInstance().getCurrentUser();
	AggregatedClaims claims = Session.getInstance().getCurrentClaims();
	
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
	
	public void removeClaim(Claim claim) {
		claims.removeClaim(claim);
	}
	
	public void selectClaim(Claim claim) {
		CurrentClaim.getInstance().setClaim(claim);
	}
}
