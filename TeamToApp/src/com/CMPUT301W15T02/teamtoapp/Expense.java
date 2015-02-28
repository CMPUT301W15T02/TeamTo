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

/*
 * Stores all of the data related to an expense
 * Note: for each method in this class, might need to check current status of its claim
 * to check if changes are allowed to be made.
 */

public class Expense {

	private Calendar date;
	private String description;
	private Currency currency;
	private String category;
	private Double amount;
	private boolean complete;
	public String photo_uri;
	
	
	public Expense() {
		date = Calendar.getInstance();
		setDescription("");
		setCurrency(Currency.getInstance("CAD"));
		setCategory("supplies");
		setAmount(0.0);
		setComplete(false);
	}

	

	public Calendar getDate() {
		return date;
	}



	public void setDate(Calendar date) {
		this.date = date;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Currency getCurrency() {
		return currency;
	}



	public void setCurrency(Currency currency) {
		this.currency = currency;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public boolean isComplete() {
		return complete;
	}



	public void setComplete(boolean complete) {
		this.complete = complete;
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
