package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Destination;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;
import com.CMPUT301W15T02.teamtoapp.Model.User;

import junit.framework.TestCase;

public class UseCase10Test extends TestCase {
	// UC 10.0
	public void testExpenseLocation() {
		Expense expense = new Expense();
		expense.setLatitude(95.2);
		expense.setLongitude(34.6);
		
		assertEquals(95.2, expense.getLatitude());
		assertEquals(34.6, expense.getLongitude());
	}
	// UC 10.1
	public void testDestinationLocation() {
		Destination destination = new Destination("Bahamas", "personal", 3.74, -12.94);
		Claim claim = new Claim();
		claim.addDestination(destination);
		assertEquals("Destination added", 1, claim.getDestinations().size());
		
		assertEquals("Destination added", destination, claim.getDestinations().get(0));
		
		claim.removeDestination(destination);
		assertEquals("Destination removed", 0, claim.getDestinations().size());
		assertFalse("Destination removed", claim.getDestinations().contains(destination));
		
	}
	
	// UC 10.2
	public void testHomeLocation() {
		User user = User.getInstance();
		user.setHomeLatitude(43);
		user.setHomeLongitude(87);
		
		assertEquals("Latitude correct", 43, user.getHomeLatitude());
		assertEquals("Longitude correct", 87, user.getHomeLongitude());
	}
	
}
