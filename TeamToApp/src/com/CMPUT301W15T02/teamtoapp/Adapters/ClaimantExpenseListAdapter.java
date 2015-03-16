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

import java.security.acl.Owner;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;
import com.CMPUT301W15T02.teamtoapp.R.id;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * 
 * A customized adapter for the claimant's list of expenses. 
 *
 */
public class ClaimantExpenseListAdapter extends ArrayAdapter<Expense> {

	private Context context;
	private int layoutId;
	private ArrayList<Expense> expenseList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	private NumberFormat numformatter = new DecimalFormat("#0.00");
	
	public ClaimantExpenseListAdapter(Context context, int textViewResourceId, ArrayList<Expense> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.expenseList = objects;
	}
	
	private class ViewHolder {
		TextView expenseDescriptionTextView;
		TextView expenseDateTextView;
		TextView categoryTextView;
		TextView currencyTextView;
	}
	//The method that update the Expense list of the claimant once an expense has been created or changes have been made to an expense
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutId, parent, false);
			holder = new ViewHolder();
			
			holder.expenseDescriptionTextView = (TextView) row.findViewById(R.id.expenseDescriptionTextView);
			holder.expenseDateTextView = (TextView) row.findViewById(R.id.expenseDateTextView);
			holder.categoryTextView = (TextView) row.findViewById(R.id.categoryTextView);
			holder.currencyTextView = (TextView) row.findViewById(R.id.currencyTextView);
			row.setTag(holder);
			
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Expense expense = expenseList.get(position);
		if (expense.getDescription().trim().isEmpty()) {
			holder.expenseDescriptionTextView.setText("No Description");
		} else {
			holder.expenseDescriptionTextView.setText(expense.getDescription());
		}
		holder.expenseDateTextView.setText(formatter.format(expense.getDate().getTime()));
		holder.categoryTextView.setText(expense.getCategory());
		holder.currencyTextView.setText(numformatter.format(expense.getAmount()).toString()+" "+expense.getCurrency());
		
		return row;
	}
	
}
