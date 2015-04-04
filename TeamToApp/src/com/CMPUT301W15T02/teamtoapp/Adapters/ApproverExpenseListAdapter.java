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
 * A customized adapter for the approver's list of expenses. 
 * 
 *  @authors Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 *
 */

public class ApproverExpenseListAdapter extends ArrayAdapter<Expense>{

	private Context context;
	private int layoutId;
	private ArrayList<Expense> expenseList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	private NumberFormat numformatter = new DecimalFormat("#0.00");
	
	
	/**
	 * ApproverExpenseListAdapter constructor
	 * 
	 * @param context - context of application
	 * @param textViewResourceId - ID of textview resource
	 * @param objects - list of expense of the submitted claim
	 */
	public ApproverExpenseListAdapter(Context context, int textViewResourceId,
			ArrayList<Expense> objects) {
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
		TextView approverExpenseDescriptionTextView;
		TextView approverExpenseDateTextView;
		TextView approverCategoryTextView;
		TextView approverCurrencyTextView;
		TextView approverPhotoReceiptTextView;

	}
	
	/**
	 * getView method updates the expense list view of the approver 
	 * from the information of a submitted claim only
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
			
			// Initialize textview holders to textview id's from approver_expense_list_rows.xml
			holder.approverExpenseDescriptionTextView = (TextView) row.findViewById(R.id.approverExpenseDescriptionTextView);
			holder.approverExpenseDateTextView = (TextView) row.findViewById(R.id.approverExpenseDateTextView);
			holder.approverCategoryTextView = (TextView) row.findViewById(R.id.approverCategoryTextView);
			holder.approverCurrencyTextView = (TextView) row.findViewById(R.id.approverCurrencyTextView);
			holder.approverPhotoReceiptTextView = (TextView) row.findViewById(R.id.approverPhotoReceiptTextView);
			
			// Set tag for holder
			row.setTag(holder);
			
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Expense expense = expenseList.get(position);
		
		// Set text for description of expense
		if (expense.getDescription().trim().isEmpty()) {
			holder.approverExpenseDescriptionTextView.setText("No Description");
		} else {
			holder.approverExpenseDescriptionTextView.setText(expense.getDescription());
		}
		
		// Set text for expense date
		holder.approverExpenseDateTextView.setText(formatter.format(expense.getDate().getTime()));
		
		// Set text for expense category
		holder.approverCategoryTextView.setText(expense.getCategory());
		
		// Set text for amount based on currency
		holder.approverCurrencyTextView.setText(numformatter.format(expense.getAmount()).toString()+" "+expense.getCurrency());
		
		// Set text for whether/not photo is available
		if (expense.getPhoto() != null) {
			holder.approverPhotoReceiptTextView.setText("Photo");
		}
		
		return row;
	}

}
