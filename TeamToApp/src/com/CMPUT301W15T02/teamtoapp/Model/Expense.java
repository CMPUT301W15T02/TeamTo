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
import java.util.Currency;
import java.util.UUID;

import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;

/**
 * 
 * Contains all of the information necessary to record an expense
 * @author Kyle Carlstrom, Raman Dhatt
 *
 */

public class Expense {

	private Calendar date;
	private String description;
	private Currency currency;
	private String category;
	private Double amount;
	private boolean complete;
	private String photo = null;
	private String expenseId;
	private double latitude;
	private double longitude;
	protected transient ArrayList<Listener> listeners = null;
	
	/**
	 * Expense constructor
	 */
	public Expense() {
		date = Calendar.getInstance();
		currency = Currency.getInstance("CAD");
		category = "Other";
		amount = 0.0;
		complete = false;
		description = "";
		expenseId = UUID.randomUUID().toString();
		listeners = new ArrayList<Listener>();
		
	}

	
	
	public Calendar getDate() {
		return date;
	}

	
	
	public void setDate(Calendar date) {
		this.date = date;
		notifyListeners();

	}
	
	

	public String getDescription() {
		return description;
	}
	
	

	public void setDescription(String description) {
		this.description = description;
		notifyListeners();

	}
	
	

	public Currency getCurrency() {
		return currency;
	}

	
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
		notifyListeners();

	}
	
	

	public String getCategory() {
		return category;
	}
	
	

	public void setCategory(String category) {
		this.category = category;
		notifyListeners();

	}

	public Double getAmount() {
		return amount;
	}
	
	

	public void setAmount(Double amount) {
		this.amount = amount;
		notifyListeners();

	}
	
	

	public boolean getComplete() {
		return complete;
	}
	
	
	
	public void setComplete(boolean complete) {
		this.complete = complete;
		notifyListeners();

	}
	
	

	public String getExpenseId() {
		return expenseId;
	}
	
	

	public void addPhoto(String photo) {
		this.photo = photo;
		notifyListeners();
	}
	
	

	public String getPhoto() {
		return photo;
	}
	
	

	public void removePhoto() {
		this.photo = null;
		notifyListeners();
		
	}
	
	/**
	 * Notify listeners for any changes to the expenses
	 */
	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
	
	/**
	 * Add listener
	 * @param listener
	 */
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Remove listener
	 * @param listener
	 */
	public void removeListener(Listener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}



	public double getLatitude() {
		return latitude;
	}



	public void setLatitude(double latitude) {
		this.latitude = latitude;
		notifyListeners();
	}



	public double getLongitude() {
		return longitude;
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
		notifyListeners();
	}
	
	

}
