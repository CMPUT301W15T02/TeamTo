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


//Source: 
// http://theopentutorials.com/tutorials/android/listview/android-custom-listview-with-image-and-text-using-arrayadapter/ 2015-03-01

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

public class ClaimantClaimListAdapter extends ArrayAdapter<Claim>{

	private Context context;
	private int layoutId;
	private ArrayList<Claim> claimsList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public ClaimantClaimListAdapter(Context context, int textViewResourceId, ArrayList<Claim> items) {

		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.claimsList  = items;

	}
	
	private class ViewHolder {
		
		TextView claimNameTextView;
		TextView startDateTextView;
		TextView destinationsTextView;
		TextView statusTextView;
		TextView claimantTotalCurrencyView;
		
		// TODO: The following will be used later (need to add them in claimant_claims_list_rows.xml):
		//TextView txtTags;
		//TextView txtTotalCurr;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutId, parent, false);
			holder = new ViewHolder();
			
			holder.claimNameTextView = (TextView) row.findViewById(R.id.claimantClaimNameView);
			holder.startDateTextView = (TextView) row.findViewById(R.id.claimantStartDateView);
			holder.destinationsTextView = (TextView) row.findViewById(R.id.claimantListDestsView);
			holder.statusTextView = (TextView) row.findViewById(R.id.claimantStatusView);
			holder.claimantTotalCurrencyView = (TextView) row.findViewById(R.id.claimantTotalCurrencyView);
			//TODO: holder.txtTags..., 
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Claim claim = claimsList.get(position);
		holder.claimNameTextView.setText(claim.getClaimName());
		holder.startDateTextView.setText(formatter.format(claim.getStartDate().getTime()));
		
		ArrayList<StringTuple> destStringTuple = claim.getDestinations();
		String allDest = "Destinations: \n";
		for (StringTuple s: destStringTuple)
	    {           
	        allDest += s.destination;
	        allDest += "\n";
	    }
		
		holder.destinationsTextView.setText(allDest);
		holder.statusTextView.setText("Status: "+claim.getStatus().toString());
		
		return row;
	}
}
