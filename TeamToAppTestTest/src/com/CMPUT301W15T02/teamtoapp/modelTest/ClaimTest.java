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
	
	public void testClaimDates() {
		Claim claim = new Claim();
		GregorianCalendar currentDate = new GregorianCalendar();
		assertTrue("Claim not instantiated with default start date", claim.getStartDate().equals(currentDate));
		assertTrue("Claim not instantiated with default end date", claim.getEndDate().equals(currentDate));
		
		GregorianCalendar futureDate1 = new GregorianCalendar(2015, 3, 22);
		GregorianCalendar futureDate2 = new GregorianCalendar(2015, 3, 24);
		claim.setStartDate(futureDate1);
		assertTrue("Start date not updated", claim.getStartDate().equals(futureDate1));
		claim.setEndDate(futureDate2);
		assertTrue("End date not updated", claim.getEndDate().equals(futureDate2));
	}

/*	
	public void testGetDestinations() {
		// Test with empty list
		ArrayList<Destination> destinationsList = new ArrayList<Destination>();
		Claim claim = new Claim();
		assertTrue("List of destinations not equal", claim.getDestinations().equals(destinationsList));
		
		// Test adding one destination
		String destination = "Hawaii";
		String reason = "business";
		Destination newDestination = new Destination(destination, reason);
		claim.addDestination(newDestination);
		assertTrue("StringTuples not equal", claim.getDestinations().get(0).equals(newDestination));
		
		// Test replacing entire destination list
		Destination dest1 = new Destination("SanFran", "Business1");
		Destination dest2 = new Destination("Chicago", "Business2");
		ArrayList<Destination> newDestinationsList = new ArrayList<Destination>();
		newDestinationsList.add(dest1);
		newDestinationsList.add(dest2);
		claim.setDestinations(newDestinationsList);
		assertTrue("Destinations lists aren't equal", claim.getDestinations().equals(newDestinationsList));
		
		// Test membership & remove
		assertTrue("Destinations list isn't saying it contains item it does", claim.getDestinations().contains(dest1));
		assertFalse("Destinations list is saying it contains item it does not", claim.getDestinations().contains(newDestination));
		claim.removeDestination(dest1);
		assertFalse("Destinations list is saying it contains item it does not", claim.getDestinations().contains(dest1));
	}
*/

	public void testTotalExpenses() {
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
		
		claim.addExpense(expense1);
		claim.addExpense(expense2);
		
		claim.setTotalCurrencies();
		assertTrue("expenses not adding up correctly", claim.getTotalCurrencies().get("CAD")==100.00);
	}
	
	// Same tests for tags, expenses, etc. but didn't implement because didn't see the point because the methods are identical?

}










