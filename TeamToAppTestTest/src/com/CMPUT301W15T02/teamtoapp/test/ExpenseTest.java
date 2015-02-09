package com.CMPUT301W15T02.teamtoapp.test;

import java.util.Calendar;
import java.util.Currency;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.ClaimManager;
import com.CMPUT301W15T02.teamtoapp.Expense;

public class ExpenseTest extends TestCase {
	
	
	public void testExpense() {
	
		/*
		 * US04.01.01
		 * As a claimant, I want to make one or more expense items for an expense claim,
		 *  each of which has a date the expense was incurred, a category, a textual description,
		 *  amount spent, and unit of currency.
		*/
		ClaimManager manager = ClaimManager.getInstance();
		Claim claim = new Claim();
		
		Expense expense = new Expense();
		Calendar date = Calendar.getInstance();
		String cat = "some category from a category spinner";
		String info = "textual description";
		Double amt = 50.00;
		Currency curr = Currency.getInstance("USD");
		
		expense.addDate(date);
		expense.setCategory(cat);
		expense.setDescription(info);
		expense.setAmount(amt);
		expense.setCurrency(curr);
		
		manager.addClaim(claim);
		manager.getClaim(claim).addExpense(expense);
		// Assert the expense does exist in the claim
		assertTrue("Expense is not added.", manager.getClaim(claim).isExpense(expense));
		
		
		/*US04.06.01
		 * As a claimant, I want to edit an expense item while changes are allowed. 
		 * (Haven't checked for status)
		*/
		manager.getClaim(claim).getExpense(expense).setDescription("blehhh");
		assertEquals("blehhh",
				manager.getClaim(claim).getExpense(expense).getDescription());
		
		
		/*US04.07.01
		 * As a claimant, I want to delete an expense item while changes are allowed.
		*/
		manager.getClaim(claim).removeExpense(expense);
		assertFalse("Expense is still there!", manager.getClaim(claim).isExpense(expense));
		
	}
	

}
