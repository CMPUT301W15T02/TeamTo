package com.CMPUT301W15T02.teamtoapp;

public class ExpenseSingleton {

	private static ExpenseSingleton instance = null;
	private Expense expense;
	
	private ExpenseSingleton() {}
	
	public static ExpenseSingleton getInstance() {
		if (instance == null) {
			instance = new ExpenseSingleton();
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
