package com.CMPUT301W15T02.teamtoapp;

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
	
	public ClaimantExpenseListAdapter(Context context, int textViewResourceId, ArrayList<Expense> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.expenseList = objects;
	}
	
	private class ViewHolder {
		TextView expenseDescriptionTextView;
		// Need to add the rest
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
			// holder.txtTags..., holder.txtTotalCurr..., etc.
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Expense expense = expenseList.get(position);
		holder.expenseDescriptionTextView.setText(expense.getDescription());
		
		return row;
	}
	
}
