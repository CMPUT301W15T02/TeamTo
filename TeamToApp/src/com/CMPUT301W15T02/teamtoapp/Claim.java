/* Claim class
 * 
 * Copyright 2015 Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

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

	public void addDestination(String destination, String reason) {
		// Q: Should I worry about duplicate key  (i.e. destination) entries? 
		this.destinations.put(destination, reason);
		
	}

	public Boolean verifyDestination(String string) {
		// TODO Auto-generated method stub
		return destinations.containsKey(string);
	}
	
	
	
}
