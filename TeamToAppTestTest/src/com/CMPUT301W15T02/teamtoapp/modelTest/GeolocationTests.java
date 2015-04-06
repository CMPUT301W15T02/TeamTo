package com.CMPUT301W15T02.teamtoapp.modelTest;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Destination;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;
import com.CMPUT301W15T02.teamtoapp.Model.User;

public class GeolocationTests extends TestCase {
	
	public void testExpenseLocation() {
		Expense expense = new Expense();
		expense.setLatitude(95.2);
		expense.setLongitude(34.6);
		
		assertEquals(95.2, expense.getLatitude());
		assertEquals(34.6, expense.getLongitude());
	}
	
	public void testDestinationLocation() {
		Destination destination = new Destination("Bahamas", "personal", 3.74, -12.94);
		Claim claim = new Claim();
		claim.addDestination(destination);
		assertEquals("Destination not added", 1, claim.getDestinations().size());
		
		assertEquals("Destination don't match up", destination, claim.getDestinations().get(0));
		
		claim.removeDestination(destination);
		assertEquals("Destination not removed", 0, claim.getDestinations().size());
		assertFalse("Destination is still there", claim.getDestinations().contains(destination));
		
	}
	
	public void testHomeLocation() {
		User user = User.getInstance();
		user.setHomeLatitude(43.0);
		user.setHomeLongitude(87.0);
		
		assertEquals("Latitude incorrect", 43.0, user.getHomeLatitude());
		assertEquals("Longitude incorrect", 87.0, user.getHomeLongitude());
	}

}
