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
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.UUID;

import com.CMPUT301W15T02.teamtoapp.Listener;

/**
 * Stores all of the data related to a claim including any associated expenses.
 * 
 * Claim watches its expenses and must be updated when an expense updates
 * 
 */

public class Claim implements Observer {

	/**
	 * 
	 * Enumerated type for the different Statuses of a claim
	 *
	 */
	public enum Status {IN_PROGRESS ("In Progress"), SUBMITTED ("Submitted"), RETURNED ("Returned"), APPROVED ("Approved");
	
		private final String name;
		
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
	private ArrayList<StringTuple> destinations;
	private ArrayList<Expense> expenses;
	private Status status;
	private String comment;
	private ArrayList<Tag> tags;
	private String userName;
	private String claimdID;
	private TreeMap<String, Double> currencyTotals;
	
	private transient ArrayList<Listener> listeners = null;
	
	
	public Claim() {
		this.claimName="";
		this.startDate = new GregorianCalendar();
		this.endDate = new GregorianCalendar();
		this.destinations = new ArrayList<StringTuple>();
		this.expenses = new ArrayList<Expense>();
		status = Status.IN_PROGRESS;
		this.tags = new ArrayList<Tag>();
		this.comment = "";
		this.claimdID = UUID.randomUUID().toString();
		currencyTotals = new TreeMap<String, Double>();
		listeners = new ArrayList<Listener>();
	}
	
	
	public String getClaimName() {
		return claimName;
	}
	
	

	public void setClaimName(String claimName) {
		this.claimName = claimName;
		notifyListeners();
	}
	
	

	public GregorianCalendar getStartDate() {
		return this.startDate;
	}
	
	

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
		notifyListeners();
	}
	
	

	public Calendar getEndDate() {
		return endDate;
	}
	
	

	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
		notifyListeners();
	}
	
	

	public ArrayList<StringTuple> getDestinations() {
		return destinations;
	}
	
	

	public void setDestinations(ArrayList<StringTuple> destinations) {
		this.destinations = destinations;
		notifyListeners();
	}
	
	
	
	public void addDestination(StringTuple new_tuple) {
		this.destinations.add(new_tuple);
		notifyListeners();
	}
	
	
	
	public void removeDestination(StringTuple destination) {
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
	
	// TODO
	public void addExpense(Expense expense) {
		//expense.addObserver(this);
		this.expenses.add(expense);
		notifyListeners();
	}
	
	
	// TODO
	public void removeExpense(Expense expense) {
		//expense.deleteObserver(this);
		this.expenses.remove(expense);
		notifyListeners();
	}	
	
	
	
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
		this.comment = comment;
		notifyListeners();
	}
	
	

	public ArrayList<Tag> getTags() {
		return tags;
	}
	
	

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
		notifyListeners();
	}
	
	
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
		notifyListeners();
	}
	
	
	
	public void removeTag(Tag tag){
		this.tags.remove(tag);
		notifyListeners();
	}
	
	

	public String getUser() {
		return userName;
	}
	
	

	public void setUser(String userName) {
		this.userName = userName;
		notifyListeners();
	}
	
	
	
	
	public String getClaimId() {
		return claimdID;
	}
	
	
	
	/**
	 *  Compiles the total currencies of all associated expenses into a TreeMap
	 *  The treemap consists of a string representing the currency code and the amount
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


	@Override
	public void update(Observable observable, Object data) {
		notifyListeners();
		
	}
	
	
}
