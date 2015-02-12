package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

public class User {
	private String name;
	private ArrayList<Claim> claims;
	private ArrayList<String> tags;
	
	public User(String name) {
		this.name = name;
		claims = new ArrayList<Claim>();
		tags = new ArrayList<String>();
	}
	
	public void addClaim(Claim claim) {
		claims.add(claim);
	}
	
	public void removeClaim(Claim claim) {
		claims.remove(claim);
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}

	public void removeTag(String tag) {
		tags.remove(tag);
	}
	
	public Claim getClaim(Claim claim) {
		// Default implementation, will need to be fixed
		return claim;
	}

	public void submitClaim(Claim claim) {
		// TODO Auto-generated method stub
		
	}


}
