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

package com.CMPUT301W15T02.teamtoapp.useCaseTest;

import java.util.Calendar;
import java.util.Currency;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Model.Expense;

/**
 * Tests use cases 6.X
 */

public class UseCase6Test extends TestCase {
	
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
		
		// Storing photo as Base64 string
		String photo = "CAT";
		expense.addPhoto(photo);
		assertEquals("Photo added to expense?", photo, expense.getPhoto());
		
		// UC 6.2 delete photo receipt
		expense.removePhoto();
		assertNull("Photo deleted from expense?", expense.getPhoto());
	}
	
}
