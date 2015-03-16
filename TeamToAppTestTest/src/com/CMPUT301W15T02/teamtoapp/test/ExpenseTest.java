package com.CMPUT301W15T02.teamtoapp.test;

import com.CMPUT301W15T02.teamtoapp.Model.Expense;

import junit.framework.TestCase;

/**
 * Tests the functionality of Expense.java
 */

public class ExpenseTest extends TestCase {

	// TODO: Needs to be edited as addPhoto() functionality added
	public void testAddPhoto() {
		Expense expense = new Expense();
		String photoPath = "";
		expense.addPhoto(photoPath);
		assertNotNull("Photo wasn't added", expense.getPhoto());
	}
	
	// TODO: Needs to be edited as removePhoto() functionality added
	public void testRemovePhoto() {
		Expense expense = new Expense();
		expense.removePhoto();
		assertNull("Photo wasn't removed", expense.getPhoto());
	}
	
}
