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
import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;
/**
 * 
 * A customized adapter for the claimant's destination list view
 * for the Create/Edit Claim interface (ClaimEditActivity.java)
 *
 */
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
	/**
	 * A method that updates the list view within the claim creation of the claimant once a claim is either edited or first created
	 */
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
