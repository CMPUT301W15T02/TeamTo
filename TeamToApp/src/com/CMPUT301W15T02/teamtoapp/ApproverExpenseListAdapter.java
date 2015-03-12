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

package com.CMPUT301W15T02.teamtoapp;

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

/*
 * As an approver, I want to list all the expense items for a submitted claim, in order of entry, 
 * showing for each expense item: the date the expense was incurred, 
 * the category, the textual description, amount spent, unit of currency, 
 * and whether there is a photographic receipt.
 */
public class ApproverExpenseListAdapter extends ArrayAdapter<Expense>{

	private Context context;
	private int layoutId;
	private ArrayList<Expense> expenseList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	private NumberFormat numformatter = new DecimalFormat("#0.00");
	
	public ApproverExpenseListAdapter(Context context, int textViewResourceId,
			ArrayList<Expense> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.expenseList = objects;
	}
	
	private class ViewHolder {
		TextView approverExpenseDescriptionTextView;
		TextView approverExpenseDateTextView;
		TextView approverCategoryTextView;
		TextView approverCurrencyTextView;
		TextView approverPhotoReceiptTextView; // Should say yes/or no if it exists.

	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutId, parent, false);
			holder = new ViewHolder();
			
			holder.approverExpenseDescriptionTextView = (TextView) row.findViewById(R.id.approverExpenseDescriptionTextView);
			holder.approverExpenseDateTextView = (TextView) row.findViewById(R.id.approverExpenseDateTextView);
			holder.approverCategoryTextView = (TextView) row.findViewById(R.id.approverCategoryTextView);
			holder.approverCurrencyTextView = (TextView) row.findViewById(R.id.approverCurrencyTextView);
			holder.approverPhotoReceiptTextView = (TextView) row.findViewById(R.id.approverPhotoReceiptTextView);
			row.setTag(holder);
			
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Expense expense = expenseList.get(position);
		// TODO: do stuff here
		
		return row;
	}

}
