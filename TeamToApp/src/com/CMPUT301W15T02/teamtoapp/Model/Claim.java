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

package com.CMPUT301W15T02.teamtoapp.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Observable;
import java.util.UUID;

import android.R.integer;
import android.content.Context;
import android.widget.Toast;

/*
 * Stores all of the data related to a claim
 * Note: for each method in this class, might need to check current claim status
 * to check if changes are allowed to be made.
 * 
 * http://stackoverflow.com/questions/25593768/difference-between-calendar-getinstance-and-gregoriancalendar-getinstance
 * We need to use GregorianCalendar as per the info in the link above!
 */

public class Claim extends Observable {

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

	private String c_name;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private ArrayList<StringTuple> destinations;
	private ArrayList<Expense> expenses;
	private Status status;
	private String comment;
	private ArrayList<Tag> tags;
	private String userId;
	private String ClaimId;
	private HashMap<Currency, Double> CurrencyTotals = new HashMap<Currency, Double>();
	
	
	public Claim() {
		this.setClaimName("New Claim");
		this.startDate = new GregorianCalendar();
		this.endDate = new GregorianCalendar();
		this.destinations = new ArrayList<StringTuple>();
		this.expenses = new ArrayList<Expense>();
		status = Status.IN_PROGRESS;
		this.tags = new ArrayList<Tag>();
		this.comment = "";
		this.ClaimId = UUID.randomUUID().toString();
	}
	
	// This method sets the total currencies for the claim list view.
	public void setTotalCurrencies() {
		
		Double totalCAD = 0.0;
		Double totalUSD = 0.0;
		Double totalEUR = 0.0;
		Double totalGBP = 0.0;
		Double totalCHF = 0.0;
		Double totalJPY = 0.0;
		Double totalCNY = 0.0;
		
		for (Expense expense : this.expenses) {
			if (expense.getAmount() != 0) {
				if (expense.getCurrency() == Currency.getInstance("CAD")) {
					totalCAD += expense.getAmount();
				} else if (expense.getCurrency() == Currency.getInstance("USD")) {
					totalUSD += expense.getAmount();
				} else if (expense.getCurrency() == Currency.getInstance("EUR")) {
					totalEUR += expense.getAmount();
				} else if (expense.getCurrency() == Currency.getInstance("GBP")) {
					totalGBP += expense.getAmount();
				} else if (expense.getCurrency() == Currency.getInstance("CHF")) {
					totalCHF += expense.getAmount();
				} else if (expense.getCurrency() == Currency.getInstance("JPY")) {
					totalJPY += expense.getAmount();
				} else if (expense.getCurrency() == Currency.getInstance("CNY")) {
					totalCNY += expense.getAmount();
				}
			}
		}
		CurrencyTotals.put(Currency.getInstance("CAD"), (double) (totalCAD));
		CurrencyTotals.put(Currency.getInstance("USD"), (double) (totalUSD));
		CurrencyTotals.put(Currency.getInstance("EUR"), (double) (totalEUR));
		CurrencyTotals.put(Currency.getInstance("GBP"), (double) (totalGBP));
		CurrencyTotals.put(Currency.getInstance("CHF"), (double) (totalCHF));
		CurrencyTotals.put(Currency.getInstance("JPY"), (double) (totalJPY));
		CurrencyTotals.put(Currency.getInstance("CNY"), (double) (totalCNY));
		notifyObservers();
	}
	
	public HashMap<Currency, Double> getTotalCurrencies() {
		return CurrencyTotals;
	}
	
	public String getClaimName() {
		return c_name;
	}

	public void setClaimName(String c_name) {
		this.c_name = c_name;
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

	public Boolean verifyDestination(StringTuple check_tuple) {
		return destinations.contains(check_tuple);
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
		return userId;
	}

	public void setUser(String user_id) {
		this.userId = user_id;
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
	
	public boolean verifyExpense(Expense expense) {
		return this.expenses.contains(expense);
	}
	
	
	public String getClaimId() {
		return ClaimId;
	}
	
	
}
