/* 
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

package com.CMPUT301W15T02.teamtoapp.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.UUID;

import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;

/**
 * Stores all of the data related to a claim including any associated expenses.
 * Claim watches its expenses and must be updated when an expense updates
 * 
 * @author Kyle Carlstrom, Raman Dhatt
 */

public class Claim implements Listener {

	/**
	 * Enumerated type for the different Statuses of a claim
	 */
	public enum Status {IN_PROGRESS ("In Progress"), SUBMITTED ("Submitted"), RETURNED ("Returned"), APPROVED ("Approved");
	
		private final String name;
	
		/**
		 * Name for each status type
		 * @param s
		 */
		private Status(String s) {
			name = s;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	private String claimName;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private ArrayList<Destination> destinations;
	private ArrayList<Expense> expenses;
	private Status status;
	private String comment;
	private ArrayList<String> tags;
	private String userName;
	private String claimdID;
	private String approverName;
	private TreeMap<String, Double> currencyTotals;
	
	private transient ArrayList<Listener> listeners = null;
	
	
	public Claim() {
		claimName="";
		startDate = new GregorianCalendar();
		endDate = new GregorianCalendar();
		destinations = new ArrayList<Destination>();
		expenses = new ArrayList<Expense>();
		status = Status.IN_PROGRESS;
		tags = new ArrayList<String>();
		comment = null;
		userName = User.getInstance().getName();
		claimdID = UUID.randomUUID().toString();
		currencyTotals = new TreeMap<String, Double>();
		listeners = new ArrayList<Listener>();
		approverName = "";
	}
	
	public String getClaimName() {
		return claimName;
	}

	/** Sets claimName to string other than default "" */
	public void setClaimName(String claimName) {
		this.claimName = claimName;
		notifyListeners();
	}

	public GregorianCalendar getStartDate() {
		return startDate;
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
		if (startDate.after(endDate)) {
			endDate = startDate;
		}
		notifyListeners();
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
		if (endDate.before(startDate)) {
			startDate = endDate;
		}
		notifyListeners();
	}
	
	public ArrayList<Destination> getDestinations() {
		return destinations;
	}
	
	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
		notifyListeners();
	}
	
	/** Adds Destination object to destinations array for claim */
	public void addDestination(Destination new_tuple) {
		this.destinations.add(new_tuple);
		notifyListeners();
	}
	
	/** Removes Destination object to destinations array for claim */
	public void removeDestination(Destination destination) {
		this.destinations.remove(destination);
		notifyListeners();
	}

	public ArrayList<Expense> getExpenses() {
		return expenses;
	}
	
	public void setExpenses(ArrayList<Expense> expenses) {
		this.expenses = expenses;
		notifyListeners();
	}
	
	/** Adds Expense object to expenses array for claim */
	public void addExpense(Expense expense) {
		expense.addListener(this);
		this.expenses.add(expense);
		notifyListeners();
	}
	
	/** Adds Expense object to expenses array for claim */
	public void removeExpense(Expense expense) {
		expense.removeListener(this);
		this.expenses.remove(expense);
		notifyListeners();
	}	
	
	/** Checks if given Expense object is in claim */
	public boolean verifyExpense(Expense expense) {
		return this.expenses.contains(expense);
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
		notifyListeners();
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		if (this.comment == null) {
			this.comment = "";
			this.comment += comment;
			this.comment += "\n";
		} else {
			this.comment = comment + "\n"+ this.comment + "\n";
		}
		notifyListeners();
	}
	
	public ArrayList<Tag> getTags() {
		ArrayList<Tag> newTags = new ArrayList<Tag>();
		for (String tagID : tags) {
			// make sure tag still exists in user, otherwise remove old tag
			Tag tag = User.getInstance().getTagByID(tagID);
			if (tag == null) {
				tags.remove(tag);
			} else {
				// if tag exists, then add it into newTags
				newTags.add(tag);
			}
		}
		return newTags;
	}
	
	// Required for approver claim list
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
		notifyListeners();
	}
	
	public String getClaimId() {
		return claimdID;
	}
	
	/**
	 *  Compiles the total currencies of all associated expenses into a TreeMap.
	 *  The TreeMap consists of a string representing the currency code and the amount
	 */
	public void setTotalCurrencies() {
		// Clear the current currencyTotals
		currencyTotals.clear();
		// Loop through all expenses of claim
		for (Expense expense : this.expenses) {
			// If it already exists in the dictionary then update it
			if (currencyTotals.containsKey(expense.getCurrency().toString())) {
				double amount = currencyTotals.get(expense.getCurrency().toString());
				amount = amount + expense.getAmount();
				currencyTotals.put(expense.getCurrency().toString(), amount);
				// Otherwise insert it in the dictionary with new value
			} else {
				currencyTotals.put(expense.getCurrency().toString(), expense.getAmount());
			}
		}
		notifyListeners();
	}
	
	public TreeMap<String, Double> getTotalCurrencies() {
		return currencyTotals;
	}

	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(Listener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/** 
	 * Uses Listener interface to notify listeners
	 * @see Listener.java
	 */
	@Override
	public void update() {
		notifyListeners();
	}

	/** Removes an existing tag from tags array */
	public void removeTag(String tagID) {
		if (tags.contains(tagID)) {
			tags.remove(tagID);
		}
		
		notifyListeners();
	}

	/** Adds tag to tags array */
	public void addTag(String tagID) {
		tags.add(tagID);
		notifyListeners();
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
}
