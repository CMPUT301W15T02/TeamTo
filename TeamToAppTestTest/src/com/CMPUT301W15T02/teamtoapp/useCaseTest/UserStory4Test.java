package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;

import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;

import junit.framework.TestCase;

public class UserStory4Test extends TestCase {
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

	// Add Expense (UC 4.1)
	public void testAddExpense() {
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

		claim.addExpense(expense);
		// Assert the expense exists in the claim
		assertTrue("Expense is not added.", claim.getExpenses().contains(expense));
	}
	
	public void testControllerAddExpense() {
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim();
		claimList.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
		
		Expense expense = new Expense();
		expense.setDescription("Some cool descriptuon");
		claimController.addExpense(expense);
		assertTrue("Controller adding expense", claimController.getExpenses().contains(expense));
	}
	
	// US 04.04 (UC 4.1.2)
	public void testCheckCompleteFlag() {
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim();
		claimList.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
		assertFalse("Claim intially incomplete", claimController.checkClaimInfoComplete());
		claimController.setClaimName("Some name");
		claimController.addDestination("some destination", "some reason");
		assertTrue("Claim complete after description and destination entered", claimController.checkClaimInfoComplete());
	}
	
	// US 4.06 (UC 4.3)
	public void testEditExpense() {
		
		Expense expense = new Expense();
		
		String info = "textual description";
		expense.setDescription(info);
		assertEquals("Changing expense working properly",
				info, expense.getDescription());
	}
	
	// US 4.07 (UC 4.2)
	public void testDeleteExpense() {
		
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
		
		claim.addExpense(expense);
		assertTrue("Claim contains expense", claim.getExpenses().contains(expense));
		// deleting expense
		claim.removeExpense(expense);
		assertFalse("Expense is still there!", claim.getExpenses().contains(expense));
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
}
