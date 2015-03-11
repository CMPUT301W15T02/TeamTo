package com.CMPUT301W15T02.teamtoapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClaimantExpenseListAdapter extends ArrayAdapter<Expense> {

	private Context context;
	private int layoutId;
	private ArrayList<Expense> expenseList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	
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
		// TODO: Still need to add tags.
	}
	
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
		holder.currencyTextView.setText(expense.getAmount().toString()+" "+expense.getCurrency());
		
		return row;
	}
	
}
