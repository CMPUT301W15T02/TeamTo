/* Tests for Claim.java */

package com.CMPUT301W15T02.teamtoapp.test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.StringTuple;

import junit.framework.TestCase;

public class ClaimTest extends TestCase {

	// TODO: may need to update as per comment under getClaimName() in Claim.java
	public void testAddClaim() {
		String defaultClaimName = "New Claim";
		Claim claim = new Claim();
		assertTrue("Default claim name not 'New Claim'", claim.getClaimName().equals(defaultClaimName));
		
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
	
	public void testGetDestinations() {
		// Test with empty list
		ArrayList<StringTuple> destinationsList = new ArrayList<StringTuple>();
		Claim claim = new Claim();
		assertTrue("List of destinations not equal", claim.getDestinations().equals(destinationsList));
		
		// Test adding one destination
		String destination = "Hawaii";
		String reason = "business";
		StringTuple newDestination = new StringTuple(destination, reason);
		claim.addDestination(newDestination);
		assertTrue("StringTuples not equal", claim.getDestinations().get(0).equals(newDestination));
		
		// Test replacing entire destination list
		StringTuple dest1 = new StringTuple("SanFran", "Business1");
		StringTuple dest2 = new StringTuple("Chicago", "Business2");
		ArrayList<StringTuple> newDestinationsList = new ArrayList<StringTuple>();
		newDestinationsList.add(dest1);
		newDestinationsList.add(dest2);
		claim.setDestinations(newDestinationsList);
		assertTrue("Destinations lists aren't equal", claim.getDestinations().equals(newDestinationsList));
		
		// Test membership & remove
		assertTrue("Destinations list isn't saying it contains item it does", claim.verifyDestination(dest1));
		assertFalse("Destinations list is saying it contains item it does not", claim.verifyDestination(newDestination));
		claim.removeDestination(dest1);
		assertFalse("Destinations list is saying it contains item it does not", claim.verifyDestination(dest1));
	}
	
}










