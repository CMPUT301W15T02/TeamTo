/* Expense class
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

import java.util.Calendar;
import java.util.Currency;
import java.util.Observable;
import java.util.UUID;

/*
 * Stores all of the data related to an expense
 * Note: for each method in this class, might need to check current status of its claim
 * to check if changes are allowed to be made.
 */

public class Expense extends Observable {

	private Calendar date;
	private String description;
	private Currency currency;
	private String category;
	private Double amount;
	private boolean complete;
	public String photo_uri;
	private String expenseId;
	
	
	public Expense() {
		date = Calendar.getInstance();
		currency = Currency.getInstance("CAD");
		category = "other";
		amount = 0.0;
		complete = false;
		description = "";
		expenseId = UUID.randomUUID().toString();
	}

	

	public Calendar getDate() {
		return date;
	}



	public void setDate(Calendar date) {
		this.date = date;
		setChanged();
		notifyObservers();
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
		setChanged();
		notifyObservers();
	}



	public Currency getCurrency() {
		return currency;
	}



	public void setCurrency(Currency currency) {
		this.currency = currency;
		setChanged();
		notifyObservers();
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
		setChanged();
		notifyObservers();
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
		setChanged();
		notifyObservers();
	}


	public boolean getComplete() {
		return complete;
	}
	

	public void setComplete(boolean complete) {
		this.complete = complete;
		setChanged();
		notifyObservers();
	}
	


	public String getExpenseId() {
		return expenseId;
	}



	public void addPhoto(String photoPath) {
		// Not sure how to add photos
		
	}

	public String getPhoto() {
		// TODO Incomplete.
		return null;
	}

	public void removePhoto() {
		// TODO Incomplete - make sure claim is editable before removing photo.
		
	}


}
