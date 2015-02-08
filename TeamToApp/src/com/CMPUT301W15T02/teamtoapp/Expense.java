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
