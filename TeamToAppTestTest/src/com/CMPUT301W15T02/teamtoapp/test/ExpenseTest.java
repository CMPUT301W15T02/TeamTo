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
	
	// UC 4.0
	public void testAddExpense() {
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
		
	}
	
	// UC 4.0 - UC 4.2
	public void testAddExpenseOrdering() {
		Claim claim = new Claim();
		Expense expense1 = new Expense();
		Expense expense2 = new Expense();
		expense2.setAmount(100.0);
		claim.addExpense(expense1);
		claim.addExpense(expense2);
		assertEquals("Expenses in right order?", expense1, claim.getExpenseList().get(0));
		assertEquals("Expenses added in right order?", expense2, claim.getExpenseList().get(1));
	}
	
	// UC 4.2
	public void testEditExpense() {
		
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
		user.getClaim(claim).getAExpense(expense).setDescription("blehhh");
		assertEquals("blehhh",
				user.getClaim(claim).getAExpense(expense).getDescription());
	}
	
	// UC 4.1
	public void testDeleteExpense() {
		
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
		// deleting expense
		user.getClaim(claim).removeExpense(expense);
		assertFalse("Expense is still there!", user.getClaim(claim).isExpense(expense));
	}
	
	// UC 6.0, UC 6.2 (UC 6.1 is simply viewing the screen)
	public void testAddAndDeletePhoto() {
		
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
		String photoPath = "sdcard/photos/cats.jpg";
		expense.addPhoto(photoPath);
		assertEquals("Photo added to expense?", photoPath, expense.getPhoto());
		
		// UC 6.2 delete photo receipt
		expense.removePhoto();
		assertNull("Photo deleted from expense?", expense.getPhoto());
		
	}
	
	// UC 4.0, 4.2, 4.4
	public void testCheckCompleteFlag() {
		User user = new User("John");
		Claim claim = new Claim();
		Expense expense = new Expense();
		
		user.addClaim(claim);
		user.getClaim(claim).addExpense(expense);
		// Checks if all information is filled in - should be false.
		assertFalse("Not complete", user.getClaim(claim).checkExpenseComplete(expense));
	}
	

}
