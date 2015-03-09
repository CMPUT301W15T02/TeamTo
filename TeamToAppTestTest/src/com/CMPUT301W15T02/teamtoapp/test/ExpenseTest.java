package com.CMPUT301W15T02.teamtoapp.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.User;
import com.CMPUT301W15T02.teamtoapp.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Expense;

public class ExpenseTest extends TestCase {
	
	private static final Currency CAD = Currency.getInstance("CAD");
	private static final Currency USD = Currency.getInstance("USD");
	private static final Currency EUR = Currency.getInstance("EUR");
	private static final Currency GBP = Currency.getInstance("GBP");
	private static final Currency JPY = Currency.getInstance("JPY");
	private static final Currency CNY = Currency.getInstance("CNY");

	// US 4.01 (UC 4.1 & 4.3)
	public void testMakeExpense() {	
		Expense expense = new Expense();
		Calendar date = Calendar.getInstance();
		String cat = "some category from a category spinner";
		String info = "textual description";
		Double amt = 50.00;
		Currency curr = Currency.getInstance("USD");
		
		expense.setDate(date);
		expense.setCategory(cat);
		expense.setDescription(info);
		expense.setAmount(amt);
		expense.setCurrency(curr);
		
		assertNotNull(expense.getDate());
		assertNotNull(expense.getCategory());
		assertNotNull(expense.getDescription());
		assertNotNull(expense.getAmount());
		assertNotNull(expense.getCurrency());		
	}
	
	// US 4.02 (UC 4.1.1 & 4.3)
	public void testCategory() {
		Expense expense = new Expense();
		// We can use spinners, but using List here for testing purposes.
		List<String> categories = Arrays.asList("air fare", "ground transport", "vehicle rental",
				"private automobile", "fuel", "parking", "registration", "accomodation",
				"meal", "supplies");

		// Pretend user chose air fare.
		String cat = categories.get(0);
		expense.setCategory(cat);
		assertEquals(categories.get(0), expense.getCategory());
	}
	// US 4.03 (UC 4.1.1 & 4.3)
	public void testCurrency() {
		Expense expense = new Expense();
		List<Currency> currenciesList = Arrays.asList(CAD, USD, EUR, GBP, JPY, CNY);
		Currency currString = currenciesList.get(3);
		expense.setCurrency(currString);
		assertEquals(GBP, expense.getCurrency());
	}

/*
	// Add Expense (UC 4.1)
	public void addExpense() {
		User user = new User("New guy");
		Claim claim = new Claim();
		Expense expense = new Expense();
		Calendar date = Calendar.getInstance();
		String cat = "some category from a category spinner";
		String info = "textual description";
		Double amt = 50.00;
		Currency curr = Currency.getInstance("USD");
		
		expense.setDate(date);
		expense.setCategory(cat);
		expense.setDescription(info);
		expense.setAmount(amt);
		expense.setCurrency(curr);

		user.addClaim(claim);
		user.getClaim(claim).addExpense(expense);
		// Assert the expense exists in the claim
		assertTrue("Expense is not added.", user.getClaim(claim).isExpense(expense));
	}
	
	// US 04.04 (UC 4.1.2)
	public void testCheckCompleteFlag() {
		// Check if completeness of new expense is already set to false.
		User user = new User("John");
		Claim claim = new Claim();
		Expense expense = new Expense();
		
		user.addClaim(claim);
		user.getClaim(claim).addExpense(expense);
		// Checks if all information is filled in - should be false.
		assertFalse("Not complete", user.getClaim(claim).checkExpenseComplete(expense));
	}
	
	// US 4.06 (UC 4.3)
	public void testEditExpense() {
		
		User user = new User("John");
		Claim claim = new Claim();
		Expense expense = new Expense();
		
		String info = "textual description";
		expense.setDescription(info);
		
		user.addClaim(claim);
		user.getClaim(claim).addExpense(expense);
		user.getClaim(claim).getExpense(expense).setDescription("blehhh");
		assertEquals("blehhh",
				user.getClaim(claim).getExpense(expense).getDescription());
	}
	
	// US 4.07 (UC 4.2)
	public void testDeleteExpense() {
		
		User user = new User("John");
		Claim claim = new Claim();
		Expense expense = new Expense();
		Calendar date = Calendar.getInstance();
		String cat = "some category from a category spinner";
		String info = "textual description";
		Double amt = 50.00;
		Currency curr = Currency.getInstance("USD");
		
		expense.setDate(date);
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
	
	// UC 4.1 - UC 4.3
	public void testAddExpenseOrdering() {
		Claim claim = new Claim();
		Expense expense1 = new Expense();
		Expense expense2 = new Expense();
		expense2.setAmount(100.0);
		claim.addExpense(expense1);
		claim.addExpense(expense2);
		assertEquals("Expenses in right order?", expense1, claim.getExpenses().get(0));
		assertEquals("Expenses added in right order?", expense2, claim.getExpenses().get(1));
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
		
		expense.setDate(date);
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
	
*/
}
