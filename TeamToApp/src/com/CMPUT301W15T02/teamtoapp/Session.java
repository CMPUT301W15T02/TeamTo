package com.CMPUT301W15T02.teamtoapp;


public class Session {
	private User currentUser;
	private ClaimList currentClaims;
	
	private static Session instance = null;

	private Session(){
		currentUser = new User(null);
		currentClaims = new ClaimList();
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

	public ClaimList getCurrentClaims() {
		return currentClaims;
	}

	public void setCurrentClaims(ClaimList currentClaims) {
		this.currentClaims = currentClaims;
	}
	
}
