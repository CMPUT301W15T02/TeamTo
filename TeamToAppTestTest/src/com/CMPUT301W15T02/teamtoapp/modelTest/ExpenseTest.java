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

import android.test.AndroidTestCase;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;

/**
 * Tests the functionality of Expense.java
 */

public class ExpenseTest extends AndroidTestCase {

	// TODO: Needs to be edited as addPhoto() functionality added (Nothing in it yet therefore not passing yet).
	public void testAddPhoto() {
		MainManager.initializeContext(mContext);
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
