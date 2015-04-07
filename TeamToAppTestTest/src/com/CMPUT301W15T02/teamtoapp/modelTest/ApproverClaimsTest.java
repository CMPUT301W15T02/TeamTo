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

package com.CMPUT301W15T02.teamtoapp.modelTest;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Model.ApproverClaims;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;

/**
 * Tests the functionality of ApproverClaims.java
 */

public class ApproverClaimsTest extends TestCase {

	public void testFindClaimByID() {
		// Grab approver claims, add a claim to the list, then check if it's possible
		// find that claim by ID
		ApproverClaims approverClaims = ApproverClaims.getInstance();
		Claim claim = new Claim();
		String claimID = claim.getClaimId();
		approverClaims.getClaims().add(claim);
		assertEquals("Finding claim by ID", claim, approverClaims.findClaimByID(claimID));
	}
	
	public void testFindExpenseByID() {
		// Grab approver claims, add a claim with an expense to the list, then check if it's possible
		// find that expense of the claim by expense ID
		ApproverClaims approverClaims = ApproverClaims.getInstance();
		Claim claim = new Claim();
		Expense expense = new Expense();
		String expenseID = expense.getExpenseId();
		claim.addExpense(expense);
		approverClaims.getClaims().add(claim);
		assertEquals("Finding expense by ID", expense, approverClaims.findExpenseByID(expenseID));
		
	}
	
}
