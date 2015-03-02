package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;
import java.util.Calendar;

import com.CMPUT301W15T02.teamtoapp.Claim.Status;

public class ClaimController {
	
	private Claim currentClaim;
	
	public ClaimController() {
		this.currentClaim = ClaimSingleton.getInstance().getClaim();
	}
	
	public void setClaimName(String name) {
		currentClaim.setClaimName(name);
	}
	
	public void setCurrentExpense(Expense expense) {
		ExpenseSingleton.getInstance().setExpense(expense);
	}
	
	
	public void addExpense(Expense expense) {
		currentClaim.addExpense(expense);
		ExpenseSingleton.getInstance().setExpense(expense);
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
	
	public Calendar getStartDate() {
		return currentClaim.getStartDate();
	}
	
	public void setEndDate(Calendar date) {
		currentClaim.setEndDate(date);
	}
	
	public Calendar getEndDate() {
		return currentClaim.getEndDate();
	}
	
	public void setUser(String name) {
		currentClaim.setUser(name);
	}
	
	public void addComment(String comment) {
		currentClaim.setComment(comment);
	}
	
	public void addDestination(String destination, String reason) {
		StringTuple newDestination = new StringTuple(destination, reason);
		currentClaim.addDestination(newDestination);
	}
	
	public ArrayList<Expense> getExpenses() {
		return currentClaim.getExpenses();
	}
	

}
