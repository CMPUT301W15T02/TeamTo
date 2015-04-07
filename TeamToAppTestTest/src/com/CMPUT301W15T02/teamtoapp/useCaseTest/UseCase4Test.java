/* 
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

package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;

import junit.framework.TestCase;
import android.test.AndroidTestCase;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;

/**
 * Tests use cases 4.X
 */

public class UseCase4Test extends AndroidTestCase {
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
	
	// US 4.02 (UC 4.1.1 & 4.2)
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
	
	// US 4.03 (UC 4.1.1 & 4.2)
	public void testCurrency() {
		Expense expense = new Expense();
		List<Currency> currenciesList = Arrays.asList(CAD, USD, EUR, GBP, JPY, CNY);
		Currency currString = currenciesList.get(3);
		expense.setCurrency(currString);
		assertEquals(GBP, expense.getCurrency());
	}

	// Add Expense (UC 4.1)
	public void testAddExpense() {
		MainManager.initializeContext(mContext);
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

	// UC 4.1
	public void testControllerAddExpense() {
		MainManager.initializeContext(mContext);
		ClaimList claimList = ClaimList.getInstance();
		Claim claim = new Claim();
		claimList.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
		
		Expense expense = new Expense();
		expense.setDescription("Some cool descriptuon");
		claimController.addExpense(expense);
		assertTrue("Controller adding expense", claimController.getExpenses().contains(expense));
		ClaimList.tearDownForTesting();
	}
	
	// US 04.04 (UC 4.1.2)
	public void testCheckCompleteFlag() {
		MainManager.initializeContext(mContext);
		ClaimList claimList = ClaimList.getInstance();
		Claim claim = new Claim();
		claimList.addClaim(claim);
		ClaimController claimController = new ClaimController(claim.getClaimId());
		assertFalse("Claim intially incomplete", claimController.checkClaimInfoComplete());
		claimController.setClaimName("Some name");
		claimController.addDestination("some destination", "some reason", 52.0, -113.0);
		assertTrue("Claim complete after description and destination entered", claimController.checkClaimInfoComplete());
		ClaimList.tearDownForTesting();
	}
	// US 4.06 (UC 4.4)
	public void testEditExpense() {
		
		Expense expense = new Expense();
		
		String info = "textual description";
		expense.setDescription(info);
		assertEquals("Changing expense working properly",
				info, expense.getDescription());
	}
	
	// US 4.07 (UC 4.3)
	public void testDeleteExpense() {
		MainManager.initializeContext(mContext);
		
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
		MainManager.initializeContext(mContext);
		Claim claim = new Claim();
		Expense expense1 = new Expense();
		Expense expense2 = new Expense();
		expense2.setAmount(100.0);
		claim.addExpense(expense1);
		claim.addExpense(expense2);
		assertEquals("Expenses in right order?", expense1, claim.getExpenses().get(0));
		assertEquals("Expenses added in right order?", expense2, claim.getExpenses().get(1));
	}
	
	// UC 4.5
	public void testExpenseLocation() {
		Expense expense = new Expense();
		expense.setLatitude(95.2);
		expense.setLongitude(34.6);

		assertEquals(95.2, expense.getLatitude());
		assertEquals(34.6, expense.getLongitude());
	}
}
