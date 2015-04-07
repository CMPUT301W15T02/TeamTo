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

import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;

import android.test.AndroidTestCase;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;

/**
 * Tests the functionality of Claim.java
 */

public class ClaimTest extends AndroidTestCase {

	// Test ability to add claim to claim list
	public void testAddClaim() {
		MainManager.initializeContext(mContext);
		String defaultClaimName = "";
		Claim claim = new Claim();
		assertTrue("Default claim name not ''", claim.getClaimName().equals(defaultClaimName));
		
		String newClaimName = "newTestClaim";
		claim.setClaimName(newClaimName);
		assertTrue("Claim name not equal", claim.getClaimName().equals(newClaimName));
		
		String aSecondClaimName = "updatedTestClaim";
		claim.setClaimName(aSecondClaimName);
		assertTrue("Claim name not updated", claim.getClaimName().equals(aSecondClaimName));
	}
	
	// Test ability to obtain start and end dates for claim
	// and test updated dates being saved
	public void testClaimDates() {
		Claim claim = new Claim();
		GregorianCalendar startDate = new GregorianCalendar(2015, 1, 22);
		claim.setStartDate(startDate);
		GregorianCalendar endDate = new GregorianCalendar(2015, 1, 23);
		claim.setEndDate(endDate);
		assertTrue("Start date is not saved", claim.getStartDate() == startDate);
		assertTrue("End date is not saved", claim.getEndDate() == endDate);
		
		GregorianCalendar futureDate1 = new GregorianCalendar(2015, 3, 22);
		GregorianCalendar futureDate2 = new GregorianCalendar(2015, 3, 24);
		claim.setStartDate(futureDate1);
		assertTrue("Start date not updated", claim.getStartDate().equals(futureDate1));
		claim.setEndDate(futureDate2);
		assertTrue("End date not updated", claim.getEndDate().equals(futureDate2));
	}

	// Test whether total currencies are calculated correctly
	public void testTotalCurrencies() {
		Claim claim = new Claim();
		
		Expense expense1 = new Expense();
		Calendar date = Calendar.getInstance();
		String cat = "some category";
		String info = "descriptical text";
		Double amt = 70.00;
		Currency curr = Currency.getInstance("CAD");
		
		expense1.setDate(date);
		expense1.setCategory(cat);
		expense1.setDescription(info);
		expense1.setAmount(amt);
		expense1.setCurrency(curr);
		
		Expense expense2 = new Expense();
		Calendar date2 = Calendar.getInstance();
		String cat2 = "another category";
		String info2 = "some other text";
		Double amt2 = 30.00;
		Currency curr2 = Currency.getInstance("CAD");
		
		expense2.setDate(date2);
		expense2.setCategory(cat2);
		expense2.setDescription(info2);
		expense2.setAmount(amt2);
		expense2.setCurrency(curr2);
		
		// Add expense 1 and 2 to claim and check total currencies add up properly
		claim.addExpense(expense1);
		claim.addExpense(expense2);
		
		claim.setTotalCurrencies();
		assertTrue("expenses not adding up correctly", claim.getTotalCurrencies().get("CAD")==100.00);
	}
	
}










