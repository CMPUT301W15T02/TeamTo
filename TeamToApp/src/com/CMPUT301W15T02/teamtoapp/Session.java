package com.CMPUT301W15T02.teamtoapp;


public class Session {
	private User currentUser;
	private ClaimsList currentClaims;
	
	private static Session instance = null;

	private Session(){
		currentUser = new User(null);
		currentClaims = new ClaimsList();
	};

	public static Session getInstance() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public ClaimsList getCurrentClaims() {
		return currentClaims;
	}

	public void setCurrentClaims(ClaimsList currentClaims) {
		this.currentClaims = currentClaims;
	}
	
}
