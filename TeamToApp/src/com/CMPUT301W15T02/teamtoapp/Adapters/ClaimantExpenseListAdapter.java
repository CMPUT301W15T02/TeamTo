/* 
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

package com.CMPUT301W15T02.teamtoapp.Adapters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;

/**
 * 
 * A customized adapter for the claimant's list of expenses. 
 * 
 * @authors ichael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 *
 */

public class ClaimantExpenseListAdapter extends ArrayAdapter<Expense> {

	private Context context;
	private int layoutId;
	private ArrayList<Expense> expenseList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	private NumberFormat numformatter = new DecimalFormat("#0.00");
	
	/**
	 * ClaimantExpenseListAdapter constructor
	 * 
	 * @param context - context of application
	 * @param textViewResourceId - ID of textview resource
	 * @param objects - list of expenses of a claim
	 */
	public ClaimantExpenseListAdapter(Context context, int textViewResourceId, ArrayList<Expense> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.expenseList = objects;
	}
	
	
	/**
	 * ViewHolder class that contains TextViews
	 * for the custom expense list view
	 *
	 */
	private class ViewHolder {
		TextView expenseDescriptionTextView;
		TextView expenseDateTextView;
		TextView categoryTextView;
		TextView currencyTextView;
		TextView incompletenessTextView;
		TextView photoTextView;
		TextView expenseGeoLocationTextView;
	}
	
	
	/**
	 * The method that update the Expense list of the claimant once an expense 
	 *	has been created or changes have been made to an expense
	 *
	 * @param position - row of list view (expense)
	 * @param convertView - row to be converted
	 * @param parent - parent of View
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutId, parent, false);
			holder = new ViewHolder();
			
			// Initialize textview holders to textview id's from claimant_expense_list_rows.xml
			holder.expenseDescriptionTextView = (TextView) row.findViewById(R.id.expenseDescriptionTextView);
			holder.expenseDateTextView = (TextView) row.findViewById(R.id.expenseDateTextView);
			holder.categoryTextView = (TextView) row.findViewById(R.id.categoryTextView);
			holder.currencyTextView = (TextView) row.findViewById(R.id.currencyTextView);
			holder.incompletenessTextView = (TextView) row.findViewById(R.id.incompletenessTextView);
			holder.photoTextView = (TextView) row.findViewById(R.id.photoTextView);
			holder.expenseGeoLocationTextView = (TextView) row.findViewById(R.id.expenseGeoLocationTextView);
			
			// Set tag for holder
			row.setTag(holder);
			
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Expense expense = expenseList.get(position);
		
		// Set text for expense description
		if (expense.getDescription().trim().isEmpty()) {
			holder.expenseDescriptionTextView.setText("No Description");
		} else {
			holder.expenseDescriptionTextView.setText(expense.getDescription());
		}
		
		// Set text for expense date
		holder.expenseDateTextView.setText(formatter.format(expense.getDate().getTime()));
		
		// Set text for expense category
		holder.categoryTextView.setText(expense.getCategory());
		
		// Set text for expense amount based on currency
		holder.currencyTextView.setText(numformatter.format(expense.getAmount()).toString()+" "+expense.getCurrency());
		
		// Set text for expense incompleteness indicator
		if (expense.getComplete() == false) {
			holder.incompletenessTextView.setText("Incomplete");
		} else {
			holder.incompletenessTextView.setText("Complete");
		}
		
		// Set text for photo indicator
		if (expense.getPhoto() != null) {
			holder.photoTextView.setText("Photo Saved, ");
		} else {
			holder.photoTextView.setText("No Photo, ");
		}
		
		// Set text for geolocation indicator
		if (expense.getLatitude() == 0 && expense.getLongitude() == 0) {
			holder.expenseGeoLocationTextView.setText("No Location");
		} else {
			holder.expenseGeoLocationTextView.setText("Location Saved");
		}
		
		return row;
	}
	
}
