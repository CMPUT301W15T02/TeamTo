
//Source: 
// http://theopentutorials.com/tutorials/android/listview/android-custom-listview-with-image-and-text-using-arrayadapter/ 2015-03-01

package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClaimantClaimLVAdapter extends ArrayAdapter<Claim>{

	private Context context;
	private int loID;
	private ArrayList<Claim> list;

	public ClaimantClaimLVAdapter(Context context, int textViewResourceId, ArrayList<Claim> items) {

		super(context, textViewResourceId, items);
		this.context = context;
		this.loID = textViewResourceId;
		this.list  = items;

	}
	
	// Should this class be static?
	private class ViewHolder {
		//Expense expense;
		TextView claimNameTextView;
		TextView startDateTextView;
		TextView destinationsTextView;
		TextView statusTextView;
		
		//The following will be used later (need to add them in claimant_claims_list_rows.xml):
		//TextView txtTags;
		//TextView txtTotalCurr;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(loID, parent, false);
			holder = new ViewHolder();
			
			holder.claimNameTextView = (TextView) row.findViewById(R.id.claimantClaimNameView);
			holder.startDateTextView = (TextView) row.findViewById(R.id.claimantStartDateView);
			holder.destinationsTextView = (TextView) row.findViewById(R.id.claimantDestsView);
			holder.statusTextView = (TextView) row.findViewById(R.id.claimantStatusView);
			// holder.txtTags..., holder.txtTotalCurr..., etc.
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Claim claim = list.get(position);
		holder.claimNameTextView.setText(claim.getClaimName());
		holder.startDateTextView.setText(claim.getStartDate().toString());
		
		ArrayList<StringTuple> destStringTuple = claim.getDestinations();
		String allDest = "Destinations: \n";
		for (StringTuple s: destStringTuple)
	    {           
	        allDest += s.destination;
	        allDest += "\n";
	    }
		
		holder.destinationsTextView.setText(allDest);
		holder.statusTextView.setText(claim.getStatus().toString());
		
		return row;
	}
}
