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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Controllers.ApproverController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Destination;

/**
 * 
 * This adapter will provide a list of submitted claims for the approver. 
 * 
 * @authors Christine Shaffer, Kyle Carlstrom, Raman Dhatt
 *
 */

public class ApproverClaimListAdapter extends ArrayAdapter<Claim>{
	
	private Context context;
	private int layoutId;
	private ArrayList<Claim> approverClaimList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#0.00");

	/**
	 * ApproverClaimListAdapter constructor
	 * 
	 * @param context - context of application
	 * @param textViewResourceId - ID of textview resource
	 * @param items - list of submitted claims where approver name != claimant name
	 */
	public ApproverClaimListAdapter(Context context, int textViewResourceId,
			ArrayList<Claim> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.approverClaimList  = items;
	}

	/**
	 * ViewHolder class that contains TextViews
	 * for the custom submitted claim list view
	 *
	 */
	private class ViewHolder {
		
		TextView claimantNameTextView;
		TextView startDateTextView;
		TextView destinationsTextView;
		TextView statusTextView; 
		TextView totalCurrencyView;
		TextView approverNameTextView;
		
	}
	
	/**
	 * This method updates the list view of the approver with submitted claims only
	 * @param position - row of list view (claim)
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
			
			// Initialize textview holders to textview id's from approver_claims_list_rows.xml
			holder.claimantNameTextView = (TextView) row.findViewById(R.id.claimantNameTextView);
			holder.startDateTextView = (TextView) row.findViewById(R.id.approverStartDateTextView);
			holder.destinationsTextView = (TextView) row.findViewById(R.id.approverDestsTextView);
			holder.statusTextView = (TextView) row.findViewById(R.id.approverStatusTextView);
			holder.totalCurrencyView = (TextView) row.findViewById(R.id.approverTotalCurrencyTextView);
			holder.approverNameTextView = (TextView) row.findViewById(R.id.approverNameTextView);
			
			// Set tag for holder
			row.setTag(holder);
			
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		// Set text for claimant name
		Claim claim = approverClaimList.get(position);
		holder.claimantNameTextView.setText(claim.getUserName());
		
		// Set text for start date
		holder.startDateTextView.setText(formatter.format(claim.getStartDate().getTime()));
		
		// Format destinations
		ArrayList<Destination> destStringTuple = claim.getDestinations();
		String allDest = "";
		int i;
		for (i = 0; i < destStringTuple.size()-1 ; i++) {           
	        allDest += destStringTuple.get(i).destination;
	        allDest += ", ";
	    }
		if (destStringTuple.size() != 0) {
			allDest += destStringTuple.get(i).destination;
		}
		
		// Set text for destinations
		holder.destinationsTextView.setText(allDest);
		
		// Set text for status (Submitted only)
		holder.statusTextView.setText(claim.getStatus().toString());
		
		// Format currencies
		claim.setTotalCurrencies();
		SortedMap<String, Double> map = Collections.synchronizedSortedMap(claim.getTotalCurrencies());
		
		String totalCurrencyOuput = "";
		
		for (String key : map.keySet()) {
			totalCurrencyOuput += df.format(map.get(key)) + " " + key.toString() + ", ";
		}
		if (totalCurrencyOuput.length() > 3) {
			totalCurrencyOuput = totalCurrencyOuput.substring(0, totalCurrencyOuput.length()-2);
		}
		
		// Set text for total currencies of expenses
		holder.totalCurrencyView.setText(totalCurrencyOuput);
		
		String approverName = claim.getApproverName();
		
		if (approverName != "") {
			// Set text for approver name
			holder.approverNameTextView.setText("Approver: "+ approverName);
		} else {
			holder.approverNameTextView.setText("Approver: N/A");
		}
		
		// Return claim row output
		return row;
	}
	
}










