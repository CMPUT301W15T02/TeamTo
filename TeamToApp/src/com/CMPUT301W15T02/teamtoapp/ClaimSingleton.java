package com.CMPUT301W15T02.teamtoapp;

public class ClaimSingleton {
	
	private static ClaimSingleton instance = null;
	private Claim claim;
	
	private ClaimSingleton() {
	}
	
	public static ClaimSingleton getInstance() {
		if (instance == null) {
			instance = new ClaimSingleton();
		}
		return instance;
	}

	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}
}
