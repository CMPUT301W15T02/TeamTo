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

/*
 * Stores all of the data related to a claim
 * Note: for each method in this class, might need to check current claim status
 * to check if changes are allowed to be made.
 */

public class Claim {

	public enum Status {IN_PROGRESS, SUBMITTED, RETURNED, APPROVED}
	
	private String name;
	private Calendar startDate;
	private Calendar endDate;
	private ArrayList<StringTuple> destinations;
	private ArrayList<Expense> expenses;
	private Status status;
	private String comment;
	private ArrayList<Tag> tags;
	private String user_id;
	// Debating adding a uniqueID to each claim so that you could simply pass in the unique ID
	
	
	public Claim() {
		name = "";
		this.startDate = Calendar.getInstance();
		this.endDate = Calendar.getInstance();
		this.destinations = new ArrayList<StringTuple>();
		this.expenses = new ArrayList<Expense>();
		status = Status.IN_PROGRESS;
		this.tags = new ArrayList<Tag>();
		this.comment = "";
	}
	
	public int getTagsListSize(){
		return tags.size();
	}
	
	public ArrayList<Tag> getTags() {
		return tags;
	}
	

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	
	public void removeTags(Tag tags){
		this.tags.remove(tags);
	}

	
	public void addExpense(Expense expense) {
		this.expenses.add(expense);
	}
	
	
	public void removeExpense(Expense expense) {
		this.expenses.remove(expense);
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

	public ArrayList<Expense> getExpenseList() {
		return expenses;
	}
	public Expense getAExpense(Expense expense) throws IllegalStateException{
		if (this.isExpense(expense)) {
			return expense;
		} else {
			throw new IllegalStateException("This expense doesn't exist!");
		}
	}


	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}
	
	public boolean checkExpenseComplete(Expense expense) {
		// Even if everything is filled out, boolean must
		// be set to false as a reminder for the user.
		if (this.getExpenseList().contains(expense)) {
			if (this.status == Claim.Status.APPROVED) {
				expense.setComplete(true);
			} else if (this.status == Claim.Status.SUBMITTED) {
				expense.setComplete(false); // Or should it be true? ...
			} 
		}
		// if the expense does not exist, return nullpointer execption?
		return expense.isComplete();
	}
	
	
	
}
