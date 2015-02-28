package com.CMPUT301W15T02.teamtoapp;

public class CurrentExpense {

	private static CurrentExpense instance = null;
	private Expense expense;
	
	private CurrentExpense() {}
	
	public static CurrentExpense getInstance() {
		if (instance == null) {
			instance = new CurrentExpense();
		}
		return instance;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}
	
}
