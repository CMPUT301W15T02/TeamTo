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
	private ArrayList<StringTuple> destinations;
	private ArrayList<Expense> expenses;
	private Status status;
	// Debating adding a uniqueID to each claim so that you could simple pass in the unique ID
	
	
	// Just have default values for now, might want to create another constructor of change this around
	public Claim() {
		name = "";
		setStartDate(Calendar.getInstance());
		setEndDate(Calendar.getInstance());
		setDestinations(new ArrayList<Claim.StringTuple>());
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

	public void addDestination(String destination, String reason) {
		// Q: Should I worry about duplicate key  (i.e. destination) entries? 
		// A: changed to an arrayList of tuples
		this.destinations.add(new StringTuple(destination, reason));
		
	}

	public Boolean verifyDestination(String a, String b) {
		// TODO Auto-generated method stub
		StringTuple temp = new StringTuple(a, b);
		return destinations.contains(temp);
	}
	
	
	// Tuple class, not sure if we should put this in its own file...
	public class StringTuple {
		String destination;
		String reason;
		
		public StringTuple(String a, String b) {
			this.destination = a;
			this.reason = b;
		}
	}
	
	
	
}
