package com.CMPUT301W15T02.teamtoapp.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.User;
import com.CMPUT301W15T02.teamtoapp.UserController;
import com.CMPUT301W15T02.teamtoapp.Expense;

public class ExpenseTest extends TestCase {
	
	
	public void testExpense() {
	
		/*
		 * US04.01.01
		 * As a claimant, I want to make one or more expense items for an expense claim,
		 *  each of which has a date the expense was incurred, a category, a textual description,
		 *  amount spent, and unit of currency.
		*/
		User user = new User("John");
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
		
		user.addClaim(claim);
		user.getClaim(claim).addExpense(expense);
		// Assert the expense does exist in the claim
		assertTrue("Expense is not added.", user.getClaim(claim).isExpense(expense));
		
		
		/*US04.06.01
		 * As a claimant, I want to edit an expense item while changes are allowed. 
		 * (Haven't checked for status)
		*/
		user.getClaim(claim).getExpense(expense).setDescription("blehhh");
		assertEquals("blehhh",
				user.getClaim(claim).getExpense(expense).getDescription());
		
		
		/*US04.07.01
		 * As a claimant, I want to delete an expense item while changes are allowed.
		*/
		user.getClaim(claim).removeExpense(expense);
		assertFalse("Expense is still there!", user.getClaim(claim).isExpense(expense));
		
		/* UC 6.0 attach photo receipt
		 *  As a claimant I want to attach a photo receipt	
		 */
		String photoPath = "sdcard/photos/cats.jpg";
		expense.addPhoto(photoPath);
		assertEquals("Photo added to expense?", photoPath, expense.getPhoto());
		
		// UC 6.2 delete photo receipt
		expense.removePhoto();
		assertNull("Photo deleted from expense?", expense.getPhoto());
		
	}
	

}
