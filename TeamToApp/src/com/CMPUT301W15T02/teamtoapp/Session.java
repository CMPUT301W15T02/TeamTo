package com.CMPUT301W15T02.teamtoapp;


public class Session {
	private User currentUser;
	private AggregatedClaims currentClaims;
	
	private static Session instance = null;

	private Session(){
		currentUser = new User(null);
		currentClaims = new AggregatedClaims();
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

	public AggregatedClaims getCurrentClaims() {
		return currentClaims;
	}

	public void setCurrentClaims(AggregatedClaims currentClaims) {
		this.currentClaims = currentClaims;
	}
	
}
