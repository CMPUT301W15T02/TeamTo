/* Expense class
 * 
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
package com.CMPUT301W15T02.teamtoapp;

import java.util.Calendar;
import java.util.Currency;

/*
 * Stores all of the data related to an expense
 */

public class Expense {

	private Calendar date;
	private String description;
	private Currency currency;
	private String category;
	private Double amount; // Might want to use something like BigDecimal... not sure
	private boolean complete;
	
	// Just default values for now
	public Expense() {
		date = Calendar.getInstance();
		description = "";
		currency = Currency.getInstance("CAD"); // Considering using Currency.getInstance(Locale.getDefault())...
		category = "supplies";
		amount = 0.0;
		complete = false; // Not sure about whether to set this or have the app automatically set it depending on context
	}
}
