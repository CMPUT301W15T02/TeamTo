package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClaimantDestinationsListAdapter extends ArrayAdapter<StringTuple> {

	private Context context;
	private int layoutID;
	private ArrayList<StringTuple> destinationsList;
	
	public ClaimantDestinationsListAdapter(Context context, int textViewResourceId, ArrayList<StringTuple> items) {
		
		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutID = textViewResourceId;
		this.destinationsList = items;
		
	}
	
	private class ViewHolder {
		
		TextView destinationTextView;
		TextView reasonTextView;
		
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutID, parent, false);
			holder = new ViewHolder();
			
			holder.destinationTextView = (TextView) row.findViewById(R.id.claimantDestsView);
			holder.reasonTextView = (TextView) row.findViewById(R.id.claimantReasonView);
			
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		StringTuple destination_reason = destinationsList.get(position);
		holder.destinationTextView.setText(destination_reason.destination);
		holder.reasonTextView.setText(destination_reason.reason);
		
		
		return row;
	}

		
}
