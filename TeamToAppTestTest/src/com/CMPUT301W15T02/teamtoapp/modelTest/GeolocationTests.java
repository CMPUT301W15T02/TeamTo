package com.CMPUT301W15T02.teamtoapp.modelTest;

import android.location.Location;

import com.CMPUT301W15T02.teamtoapp.Controllers.ExpenseController;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;

import junit.framework.TestCase;

public class GeolocationTests extends TestCase {
	
	public void testExpenseLocation() {
		Expense expense = new Expense();
		expense.setLatitude(95.2);
		expense.setLongitude(34.6);
		
		assertEquals(95.2, expense.getLatitude());
		assertEquals(34.6, expense.getLongitude());
	}

}
