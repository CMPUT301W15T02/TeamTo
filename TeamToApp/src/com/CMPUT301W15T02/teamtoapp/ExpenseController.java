/* 
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
import java.util.Observer;

public class ExpenseController {
	
	private Expense currentExpense;
	
	public ExpenseController(String expenseID) {
		this.currentExpense = Session.getInstance().getCurrentClaims().findExpenseByID(expenseID);
	}
	
	public void setDate(Calendar calendar) {
		currentExpense.setDate(calendar);
	}
	
	public Calendar getDate() {
		return currentExpense.getDate();
	}
	
	public void setCategory(String category) {
		currentExpense.setCategory(category);
	}
	
	public String getCategory() {
		return currentExpense.getCategory();
	}
	
	public void setDescription(String description) {
		// Automatically sets the expense completed, not sure if this should go in the model
		if (!currentExpense.getAmount().equals(0.0) && !description.equals("")) {
			currentExpense.setComplete(true);
		}
		currentExpense.setDescription(description);
	}
	
	public String getDescription() {
		return currentExpense.getDescription();
	}
	
	public void setAmount(Double amount) {
		// Automatically sets the expenses completed
		if (!currentExpense.getDescription().equals("") && !amount.equals(0.0)) {
			currentExpense.setComplete(true);
		}
		currentExpense.setAmount(amount);
	}
	
	public Double getAmount() {
		return currentExpense.getAmount();
	}
	
	public void setCurrency(Currency currency) {
		currentExpense.setCurrency(currency);
	}
	
	public Currency getCurrency() {
		return currentExpense.getCurrency();
	}
	
	public void setComplete(boolean is_complete) {
		currentExpense.setComplete(is_complete);
	}
	
	public boolean isComplete() {
		return currentExpense.getComplete();
	}
	
	
	public void addPhoto(String photoPath) {
		currentExpense.addPhoto(photoPath);
	}
	
	public void removePhoto(String photoPath) {
		currentExpense.removePhoto();
	}
	
	public Expense getExpense() {
		return currentExpense;
	}
	
	public void addObserverToExpense(Observer observer) {
		currentExpense.addObserver(observer);
	}
	
	public void removeObserverFromExpense(Observer observer) {
		currentExpense.deleteObserver(observer);
	}
	
	
	
}
