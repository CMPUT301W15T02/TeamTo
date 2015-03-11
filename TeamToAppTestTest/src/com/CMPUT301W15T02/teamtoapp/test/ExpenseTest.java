package com.CMPUT301W15T02.teamtoapp.test;

import java.util.GregorianCalendar;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.Expense;

import junit.framework.TestCase;

public class ExpenseTest extends TestCase {
	

	public void testExpenseAmount() {
		Expense expense = new Expense();
		Double amount = 0.0;
		assertTrue("expense not initialized to zero", expense.getAmount().equals(0.0));
		
		expense.setAmount(100.00);
		assertTrue("expense not updated", expense.getAmount().equals(100.0));
		
		
	}
	
	
	public void testExpenseDate() {
		Expense expense = new Expense();
		GregorianCalendar currentDate = new GregorianCalendar();
		assertTrue("Expense not initialized with default date", expense.getDate().equals(currentDate));

		GregorianCalendar futureDate = new GregorianCalendar(2015, 3, 22);

		expense.setDate(futureDate);
		assertTrue("Start date not updated", expense.getDate().equals(futureDate));

	}
	
}
