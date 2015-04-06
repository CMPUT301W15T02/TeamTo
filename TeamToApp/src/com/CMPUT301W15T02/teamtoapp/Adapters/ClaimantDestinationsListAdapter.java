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

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Model.Destination;

/**
 * 
 * A customized adapter for the claimant's destination list view
 * for the Create/Edit Claim interface.
 * 
 * @see ClaimEditActivity.java
 * @authors Kyle Carlstrom, Raman Dhatt
 *
 */

public class ClaimantDestinationsListAdapter extends ArrayAdapter<Destination> {

	private Context context;
	private int layoutID;
	private ArrayList<Destination> destinationsList;
	
	/**
	 * ClaimantDestinationListAdapter constructor
	 * 
	 * @param context - context of application
	 * @param textViewResourceId - ID of textview resource
	 * @param items - list of destination from the claim
	 */
	public ClaimantDestinationsListAdapter(Context context, int textViewResourceId, ArrayList<Destination> items) {
		
		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutID = textViewResourceId;
		this.destinationsList = items;
		
	}
	
	/**
	 * ViewHolder class that contains TextViews
	 * for the custom destination list view
	 *
	 */
	private class ViewHolder {
		
		TextView destinationTextView;
		TextView reasonTextView;
		
	}
	/**
	 * A method that updates the list view within the claim creation of the 
	 * claimant once a claim is either edited or first created
	 * 
	 * @param position - row of list view (destination, reason)
	 * @param convertView - row to be converted
	 * @param parent - parent of View
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		ViewHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutID, parent, false);
			holder = new ViewHolder();
			
			// Initialize textview holders to textview id's from claimant_destination_rows.xml
			holder.destinationTextView = (TextView) row.findViewById(R.id.claimantDestsView);
			holder.reasonTextView = (TextView) row.findViewById(R.id.claimantReasonView);
			
			// Set tag for holder
			row.setTag(holder);
			
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		Destination destination_reason = destinationsList.get(position);
		
		// Set text for destination
		holder.destinationTextView.setText(destination_reason.destination);
		
		// Set text for reason
		holder.reasonTextView.setText(destination_reason.reason);
		
		
		return row;
	}

		
}