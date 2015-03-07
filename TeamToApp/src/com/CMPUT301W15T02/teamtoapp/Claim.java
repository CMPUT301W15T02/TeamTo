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
import java.util.Observable;
import java.util.UUID;

import android.R.integer;
import android.content.Context;
import android.widget.Toast;

/*
 * Stores all of the data related to a claim
 * Note: for each method in this class, might need to check current claim status
 * to check if changes are allowed to be made.
 */

public class Claim extends Observable {

	
	public enum Status {IN_PROGRESS, SUBMITTED, RETURNED, APPROVED}
	private String c_name;
	private Calendar startDate;
	private Calendar endDate;
	private ArrayList<StringTuple> destinations;
	private ArrayList<Expense> expenses;
	private Status status;
	private String comment;
	private ArrayList<Tag> tags;
	private String userId;
	private String ClaimId;
	

	public Claim() {
		this.setClaimName("New Claim");
		this.startDate = Calendar.getInstance();
		this.endDate = Calendar.getInstance();
		this.destinations = new ArrayList<StringTuple>();
		this.expenses = new ArrayList<Expense>();
		status = Status.IN_PROGRESS;
		this.tags = new ArrayList<Tag>();
		this.comment = "";
		this.ClaimId = UUID.randomUUID().toString();
	}
	
	// Need to display claim name for custom claim list view (claimant, not for approver)
	public String getClaimName() {
		return c_name;
	}


	public void setClaimName(String c_name) {
		this.c_name = c_name;
		setChanged();
		notifyObservers();
	}


	public Calendar getStartDate() {
		return startDate;
	}

	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
		setChanged();
		notifyObservers();
	}


	public Calendar getEndDate() {
		return endDate;
	}


	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
		setChanged();
		notifyObservers();
	}


	public ArrayList<StringTuple> getDestinations() {
		return destinations;
	}


	public void setDestinations(ArrayList<StringTuple> destinations) {
		this.destinations = destinations;
		setChanged();
		notifyObservers();
	}


	public ArrayList<Expense> getExpenses() {
		return expenses;
	}


	public void setExpenses(ArrayList<Expense> expenses) {
		this.expenses = expenses;
		setChanged();
		notifyObservers();
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
		setChanged();
		notifyObservers();
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
		setChanged();
		notifyObservers();
	}


	public ArrayList<Tag> getTags() {
		return tags;
	}


	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
		setChanged();
		notifyObservers();
	}


	public String getUser() {
		return userId;
	}


	public void setUser(String user_id) {
		this.userId = user_id;
		setChanged();
		notifyObservers();
	}
	
	

	public void addTag(Tag tag) {
		this.tags.add(tag);
		setChanged();
		notifyObservers();
	}
	
	public void removeTag(Tag tag){
		this.tags.remove(tag);
		setChanged();
		notifyObservers();
	}

	
	public void addExpense(Expense expense) {
		this.expenses.add(expense);
		setChanged();
		notifyObservers();
	}
	
	
	public void removeExpense(Expense expense) {
		this.expenses.remove(expense);
		setChanged();
		notifyObservers();
	}	

	
	public void addDestination(StringTuple new_tuple) {
		this.destinations.add(new_tuple);
		setChanged();
		notifyObservers();
		
	}

	public Boolean verifyDestination(StringTuple check_tuple) {
		return destinations.contains(check_tuple);
	}
	

	
	public boolean isExpense(Expense expense) {
		return this.expenses.contains(expense);
	}
	
	// TODO: Need to do tests
	public int checkExpensesComplete() {
		int numIncomplete = 0;
		
		if (expenses.size() == 0) {
			return 0;
		} else {
			for (Expense expense : expenses) {
				// TODO: the isComplete method still needs implementation.
				if (expense.isComplete() == false) { numIncomplete++; }
			}
		}
		
		return numIncomplete;
	}
	
	// Up to caller to be sure not null
	public Expense getExpense(Expense expense) {
		if( this.expenses.size() == 0 ) {
			return null;
		} else {
			for(int i = 0; i < expenses.size(); i++) {
				Expense currentExpense = expenses.get(i);
				if( currentExpense.equals(expense) ) {
					return currentExpense;
				}
			}
			return null;
		}
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getClaimId() {
		return ClaimId;
	}
	
}
