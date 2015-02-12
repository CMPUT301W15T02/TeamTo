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
import java.util.UUID;

/*
 * Stores all of the data related to a claim
 * Note: for each method in this class, might need to check current claim status
 * to check if changes are allowed to be made.
 */

public class Claim {

	// Using enumerated type since status can only take a small number of values and strings were ugly last time
	enum Status {IN_PROGRESS, SUBMITTED, RETURNED, APPROVED}
	
	private String name;
	private Calendar startDate;
	private Calendar endDate;
	private ArrayList<StringTuple> destinations;
	private ArrayList<Expense> expenses;
	private Status status;
	private ArrayList<String> tags;
	// Debating adding a uniqueID to each claim so that you could simple pass in the unique ID
	
	
	// Just have default values for now, might want to create another constructor of change this around
	public Claim() {
		name = "";
		setStartDate(Calendar.getInstance());
		setEndDate(Calendar.getInstance());
		setDestinations(new ArrayList<StringTuple>());
		expenses = new ArrayList<Expense>();
		status = Status.IN_PROGRESS;
		tags = new ArrayList<String>();
	}
	
	public int getTagsListSize(){
		return tags.size();
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}
	

	public void setTags(String tags) {
		this.tags.add(tags);
	}
	
	public void removeTags(String tags){
		this.tags.remove(tags);
	}

	
	public void addExpense(Expense expense) {
		this.expenses.add(expense);
	}
	
	
	public void removeExpense(Expense expense) {
		this.expenses.remove(expense);
	}

	
	public void addClaimName(String new_name) {
		this.name = new_name;
		
	}

	
	public String getClaimName() {
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

	
	public ArrayList<StringTuple> getDestinations() {
		return destinations;
	}

	
	public void setDestinations(ArrayList<StringTuple> destinations) {
		this.destinations = destinations;
	}

	
	public void addDestination(StringTuple new_tuple) {
		// Q: Should I worry about duplicate key  (i.e. destination) entries? 
		// A: changed to an arrayList of tuples
		this.destinations.add(new_tuple);
		
	}

	public Boolean verifyDestination(StringTuple check_tuple) {
		return destinations.contains(check_tuple);
	}
	
	
	public void setClaimName(String new_name) {
		this.name = new_name;
	}

	
	public boolean isExpense(Expense expense) {
		return this.expenses.contains(expense);
	}

	
	public Expense getExpense(Expense expense) throws IllegalStateException{
		if (this.isExpense(expense)) {
			return expense;
		} else {
			throw new IllegalStateException("This expense doesn't exist!");
		}
	}
	
	
	
}
