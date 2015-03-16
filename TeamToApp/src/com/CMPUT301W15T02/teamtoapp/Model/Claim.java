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

import com.CMPUT301W15T02.teamtoapp.Controllers.ExpenseController;

/**
 * Stores all of the data related to a claim including any associated expenses.
 * 
 */

public class Claim extends Observable implements Observer {

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
	}
	
	/**
	 *  Compiles the total currencies of all associated expenses into a TreeMap
	 */
	public void setTotalCurrencies() {
		currencyTotals.clear();
		for (Expense expense : this.expenses) {
			if (currencyTotals.containsKey(expense.getCurrency().toString())) {
				double amount = currencyTotals.get(expense.getCurrency().toString());
				amount = amount + expense.getAmount();
				currencyTotals.put(expense.getCurrency().toString(), amount);
			} else {
				currencyTotals.put(expense.getCurrency().toString(), expense.getAmount());
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public TreeMap<String, Double> getTotalCurrencies() {
		return currencyTotals;
	}
	
	public String getClaimName() {
		return claimName;
	}

	public void setClaimName(String claimName) {
		this.claimName = claimName;
		setChanged();
		notifyObservers();
	}

	public GregorianCalendar getStartDate() {
		return this.startDate;
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
		setChanged();
		notifyObservers();
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(GregorianCalendar endDate) {
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
	
	public void addDestination(StringTuple new_tuple) {
		this.destinations.add(new_tuple);
		setChanged();
		notifyObservers();
	}
	
	public void removeDestination(StringTuple destination) {
		this.destinations.remove(destination);
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

	public String getUser() {
		return userName;
	}

	public void setUser(String userName) {
		this.userName = userName;
		setChanged();
		notifyObservers();
	}
	
	public void addExpense(Expense expense) {
		expense.addObserver(this);
		this.expenses.add(expense);
		setChanged();
		notifyObservers();
	}
	
	public void removeExpense(Expense expense) {
		expense.deleteObserver(this);
		this.expenses.remove(expense);
		setChanged();
		notifyObservers();
	}	
	
	public boolean verifyExpense(Expense expense) {
		return this.expenses.contains(expense);
	}
	
	
	public String getClaimId() {
		return claimdID;
	}

	@Override
	public void update(Observable observable, Object data) {
		setChanged();
		notifyObservers();
	}
	
	
}
