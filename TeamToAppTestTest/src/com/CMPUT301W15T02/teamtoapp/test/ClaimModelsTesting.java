package com.CMPUT301W15T02.teamtoapp.test;

import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.Expense;
import com.CMPUT301W15T02.teamtoapp.SessionController;

import junit.framework.TestCase;

public class ClaimModelsTesting extends TestCase {
	
	public void testAddClaim() {
		SessionController sessionController = new SessionController();
		Claim claim = new Claim();
		sessionController.addClaim(claim);
		assertEquals("Added to aggregated claims", 1, sessionController.getClaims().size());
		assertTrue("claim should exist", sessionController.getClaims().contains(claim));
		sessionController.removeClaim(claim);
		assertEquals("Check if no claims saved", 0, sessionController.getClaims().size());
	}
	
	public void testDeleteClaim() {
		SessionController sessionController = new SessionController();
		Claim claim = new Claim();
		assertEquals("Check if no claims saved", 0, sessionController.getClaims().size());
	}
	
	public void testSortClaimsByDate() {
		//SessionController sessionController = new SessionController();
	}
	
	public void testGetExpense() {
		Claim emptyClaim = new Claim();
		Expense expense = new Expense();
		assertNull("Not returning null if expense not in claim", emptyClaim.getExpense(expense));
		
		Claim nonEmptyClaim = new Claim();
		ArrayList<Expense> expenseList = new ArrayList<Expense>();
		expenseList.add(expense);
		nonEmptyClaim.addExpense(expense);
		assertTrue("Not returning correct expense", nonEmptyClaim.getExpense(expense).equals(expense));
	}
	
}
