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
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;

/**
 * 
 * This adapter will provide a list of submitted claims for the approver. In progress.
 *
 */

public class ApproverClaimListAdapter extends ArrayAdapter<Claim>{
	
	private Context context;
	private int layoutId;
	private ArrayList<Claim> approverClaimList;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#0.00");

	public ApproverClaimListAdapter(Context context, int textViewResourceId,
			ArrayList<Claim> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.approverClaimList  = items;
	}

	private class ViewHolder {
		
		TextView claimantNameTextView;
		TextView startDateTextView;
		TextView destinationsTextView;
		TextView statusTextView; // Should show submitted only
		TextView totalCurrencyView;
		TextView approverNameTextView;
		
	}
	
	/**
	 * This method updates the list view of the approver with submitted claims only
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutId, parent, false);
			holder = new ViewHolder();
			
			holder.claimantNameTextView = (TextView) row.findViewById(R.id.claimantNameTextView);
			holder.startDateTextView = (TextView) row.findViewById(R.id.approverStartDateTextView);
			holder.destinationsTextView = (TextView) row.findViewById(R.id.approverDestsTextView);
			holder.statusTextView = (TextView) row.findViewById(R.id.approverStatusTextView);
			holder.totalCurrencyView = (TextView) row.findViewById(R.id.approverTotalCurrencyTextView);
			holder.approverNameTextView = (TextView) row.findViewById(R.id.approverNameTextView);

			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Claim claim = approverClaimList.get(position);
		// TODO: Need to fix claimant name
		holder.claimantNameTextView.setText(claim.getUserName());
		holder.startDateTextView.setText(formatter.format(claim.getStartDate().getTime()));
		
		// Format destinations
		ArrayList<StringTuple> destStringTuple = claim.getDestinations();
		String allDest = "";
		int i;
		for (i = 0; i < destStringTuple.size()-1 ; i++) {           
	        allDest += destStringTuple.get(i).destination;
	        allDest += ", ";
	    }
		if (destStringTuple.size() != 0) {
			allDest += destStringTuple.get(i).destination;
		}
		
		holder.destinationsTextView.setText(allDest);
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
		holder.totalCurrencyView.setText(totalCurrencyOuput);
		
		//TODO: Still need to do approver name
		
		return row;
	}
	
}










