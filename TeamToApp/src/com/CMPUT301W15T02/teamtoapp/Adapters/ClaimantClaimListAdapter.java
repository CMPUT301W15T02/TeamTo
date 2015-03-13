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

package com.CMPUT301W15T02.teamtoapp.Adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Activities.TagManagerActivity;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.R.id;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//This adapter customizes the claim list view for the claimant.
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
		TextView totalCurrencyView;
		TextView tagsTextView;
		
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
			holder.totalCurrencyView = (TextView) row.findViewById(R.id.claimantTotalCurrencyView);
			holder.tagsTextView = (TextView) row.findViewById(R.id.claimantTagsListView); 
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		//these holders update the data for the recently made or changed claim for the claimant claims
		Claim claim = claimsList.get(position);
		holder.claimNameTextView.setText(claim.getClaimName());
		holder.startDateTextView.setText(formatter.format(claim.getStartDate().getTime()));
		
		//This part of the code takes the updates from the destinations list view from the make a claim 
		//from the claimant and puts the new information to the claimants list view 
		
		ArrayList<StringTuple> destStringTuple = claim.getDestinations();
		String allDest = "Destinations: ";
		for (StringTuple s: destStringTuple)
	    {           
	        allDest += s.destination;
	        allDest += "\n";
	    }
		
		//This part of the code takes the updates from the tags list view from the claimant claims 
		// and puts the new information to the claimants list view 
		ArrayList<Tag> tags = claim.getTags();
		String allTags = "Tags: ";
		for (Tag tag: tags)
		{
			allTags += ""+ tag.getTagName()+ "  ";
		}
		
		holder.destinationsTextView.setText(allDest);
		holder.statusTextView.setText(claim.getStatus().toString());
		holder.tagsTextView.setText(allTags);
		
		// Set the total currencies first, then display currencies with amount > 0.
		claim.setTotalCurrencies();
		HashMap<Currency, Double> totalCurrencies = claim.getTotalCurrencies();
		String totalCurrencyOuput = "Currencies: \n";

		if (totalCurrencies.get(Currency.getInstance("CAD")) != 0) {
			totalCurrencyOuput += totalCurrencies.get(Currency.getInstance("CAD")).toString()+" CAD\n";
		}
		
		if (totalCurrencies.get(Currency.getInstance("USD")) != 0) {
			totalCurrencyOuput += totalCurrencies.get(Currency.getInstance("USD")).toString()+" USD\n";
		}
		
		if (totalCurrencies.get(Currency.getInstance("EUR")) != 0) {
			totalCurrencyOuput += totalCurrencies.get(Currency.getInstance("EUR")).toString()+" EUR\n";
		}

		if (totalCurrencies.get(Currency.getInstance("GBP")) != 0) {
			totalCurrencyOuput += totalCurrencies.get(Currency.getInstance("GBP")).toString()+" GBP\n";
		}
		
		if (totalCurrencies.get(Currency.getInstance("CHF")) != 0) {
			totalCurrencyOuput += totalCurrencies.get(Currency.getInstance("CHF")).toString()+" CHF\n";
		}
		
		if (totalCurrencies.get(Currency.getInstance("JPY")) != 0) {
			totalCurrencyOuput += totalCurrencies.get(Currency.getInstance("JPY")).toString()+" JPY\n";
		}
		
		if (totalCurrencies.get(Currency.getInstance("CNY")) != 0) {
			totalCurrencyOuput += totalCurrencies.get(Currency.getInstance("CNY")).toString()+" CNY\n";
		}
		
		holder.totalCurrencyView.setText(totalCurrencyOuput);

		return row;
	}
}
