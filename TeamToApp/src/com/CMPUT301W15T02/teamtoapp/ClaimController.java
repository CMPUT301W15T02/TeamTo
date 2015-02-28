package com.CMPUT301W15T02.teamtoapp;

import java.util.Calendar;

import com.CMPUT301W15T02.teamtoapp.Claim.Status;

public class ClaimController {
	
	private Claim currentClaim;
	
	public ClaimController(Claim claim) {
		this.currentClaim = claim;
	}
	
	public void addExpense(Expense expense) {
		currentClaim.addExpense(expense);
	}
	
	public void removeExpense(Expense expense) {
		currentClaim.removeExpense(expense);
	}
	
	public void addTag(Tag tag) {
		currentClaim.addTag(tag);
	}
	
	public void removeTag(Tag tag) {
		currentClaim.removeTag(tag);
	}
	
	public void submitClaim() {
		currentClaim.setStatus(Status.SUBMITTED);
	}
	
	public void returnClaim() {
		currentClaim.setStatus(Status.RETURNED);
	}
	
	public void approvedClaim() {
		currentClaim.setStatus(Status.APPROVED);
	}
	
	public void setStartDate(Calendar date) {
		currentClaim.setStartDate(date);
	}
	
	public void setEndDate(Calendar date) {
		currentClaim.setEndDate(date);
	}
	
	public void setUser(String name) {
		currentClaim.setUser(name);
	}
	
	public void addComment(String comment) {
		currentClaim.addComment(comment);
	}
	
	

}
