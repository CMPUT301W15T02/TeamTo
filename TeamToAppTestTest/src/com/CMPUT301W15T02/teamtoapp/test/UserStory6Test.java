package com.CMPUT301W15T02.teamtoapp.test;

import java.util.Calendar;
import java.util.Currency;

import com.CMPUT301W15T02.teamtoapp.Model.Expense;

import junit.framework.TestCase;

public class UserStory6Test extends TestCase {
	// UC 6.0, UC 6.2 (UC 6.1 is simply viewing the screen)
	public void testAddAndDeletePhoto() {
		
		Expense expense = new Expense();
		Calendar date = Calendar.getInstance();
		String cat = "some category from a category spinner";
		String info = "textual description";
		Double amt = 50.00;
		Currency curr = Currency.getInstance("USD");
		
		expense.setDate(date);
		expense.setCategory(cat);
		expense.setDescription(info);
		expense.setAmount(amt);
		expense.setCurrency(curr);
		
		String photoPath = "sdcard/photos/cats.jpg";
		expense.addPhoto(photoPath);
		assertEquals("Photo added to expense?", photoPath, expense.getPhoto());
		
		// UC 6.2 delete photo receipt
		expense.removePhoto();
		assertNull("Photo deleted from expense?", expense.getPhoto());
		
	}
}
