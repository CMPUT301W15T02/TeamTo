package com.CMPUT301W15T02.teamtoapp;

import java.util.Calendar;
import java.util.Currency;

public class ExpenseController {
	
	private Expense currentExpense;
	
	public ExpenseController() {
		this.currentExpense = ExpenseSingleton.getInstance().getExpense();
	}
	
	public void setDate(Calendar calendar) {
		currentExpense.setDate(calendar);
	}
	
	public void setCategory(String category) {
		currentExpense.setCategory(category);
	}
	
	public void setDescription(String description) {
		currentExpense.setDescription(description);
	}
	
	public void setAmount(Double amount) {
		currentExpense.setAmount(amount);
	}
	
	public void setCurrency(Currency currency) {
		currentExpense.setCurrency(currency);
	}
	
	public void setComplete(boolean is_complete) {
		currentExpense.setComplete(is_complete);
	}
	
	public void addPhoto(String photoPath) {
		currentExpense.addPhoto(photoPath);
	}
	
	public void removePhoto(String photoPath) {
		currentExpense.removePhoto();
	}
}
