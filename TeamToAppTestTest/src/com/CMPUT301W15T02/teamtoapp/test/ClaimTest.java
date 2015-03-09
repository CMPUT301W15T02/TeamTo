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
		GregorianCalendar date = new GregorianCalendar(2015,3,24);
		//assertTrue("", claim.getStartDate());
	}
	
	public void testGetDestinations() {
		ArrayList<StringTuple> destinationsList = new ArrayList<StringTuple>();
		Claim claim = new Claim();
		assertTrue("List of destinations not equal", claim.getDestinations().equals(destinationsList));
		
		String destination = "Hawaii";
		String reason = "business";
		StringTuple newDestination = new StringTuple(destination, reason);
		claim.addDestination(newDestination);
		assertTrue("StringTuples not equal", claim.getDestinations().equals(newDestination));
	}
	
}
