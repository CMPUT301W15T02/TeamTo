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
	/*
	 * String[] category = {"Air Fare","Ground Transport","Vehicle Rental","Private Automobile",
			"Fuel","Parking","Registration","Accommodation","Meal", "Supplies"};
	   
	   String[] currency = {"CAD", "USD", "EUR", "GBP", "CHF", "JPY","CNY"};
	   
	   not sure about this type of implementation change will an array list be
	   better even though editing and removing are not necessary?
	*/
	private Calendar date;
	private String description;
	private Currency currency;
	private String category;
	private Double amount; // Might want to use something like BigDecimal... not sure
	private boolean complete;
	
	// Just default values for now
	public Expense() {
		date = Calendar.getInstance();
		setDescription("");
		setCurrency(Currency.getInstance("CAD")); // Considering using Currency.getInstance(Locale.getDefault())...
		setCategory("supplies");
		setAmount(0.0);
		complete = false; // Not sure about whether to set this or have the app automatically set it depending on context
	}

	public void addDate(Calendar new_date) {
		this.date = new_date;
		
	}
	//Added a get date to allow the ability to edit it later
	public Calendar getDate() {
		return this.date;
		
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


}
