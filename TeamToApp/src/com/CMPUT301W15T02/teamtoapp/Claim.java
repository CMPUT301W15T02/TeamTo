package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/*
 * Stores all of the data related to a claim
 */

public class Claim {

	// Using enumerated type since status can only take a small number of values and strings were ugly last time
	enum Status {IN_PROGRESS, SUBMITTED, RETURNED, APPROVED}
	
	private String name;
	private Calendar startDate;
	private Calendar endDate;
	private HashMap<String, String> destinations;
	private ArrayList<Expense> expenses;
	private Status status;
	// Debating adding a uniqueID to each claim so that you could simple pass in the unique ID
	
	
	// Just have default values for now, might want to create another constructor of change this around
	public Claim() {
		name = "";
		setStartDate(Calendar.getInstance());
		setEndDate(Calendar.getInstance());
		setDestinations(new HashMap<String, String>());
		expenses = new ArrayList<Expense>();
		status = Status.IN_PROGRESS;
	}
	
	public void addExpense(Expense expense) {
		expenses.add(expense);
	}
	
	public void removeExpense(Expense expense) {
		expenses.remove(expense);
	}

	public void addClaimName(String new_name) {
		// TODO Auto-generated method stub
		this.name = new_name;
		
	}

	public String getClaimName() {
		// TODO Auto-generated method stub
		return name;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public HashMap<String, String> getDestinations() {
		return destinations;
	}

	public void setDestinations(HashMap<String, String> destinations) {
		this.destinations = destinations;
	}
	
	
	
}
