package com.CMPUT301W15T02.teamtoapp;

public class CurrentClaim {
	
	private static CurrentClaim instance = null;
	private Claim claim;
	
	private CurrentClaim() {
	}
	
	public static CurrentClaim getInstance() {
		if (instance == null) {
			instance = new CurrentClaim();
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
