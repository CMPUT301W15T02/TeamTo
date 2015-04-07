package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Destination;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;
import com.CMPUT301W15T02.teamtoapp.Model.User;

import junit.framework.TestCase;

public class UseCase10Test extends TestCase {
	
	
	// UC 10.2
	public void testHomeLocation() {
		User user = User.getInstance();
		user.setHomeLatitude(43);
		user.setHomeLongitude(87);
		
		assertEquals("Latitude correct", 43.0, user.getHomeLatitude());
		assertEquals("Longitude correct", 87.0, user.getHomeLongitude());
	}
	
}
