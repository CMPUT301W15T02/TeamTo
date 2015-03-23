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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedMap;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
/**
 * 
 * A customized adapter for the claimant's list of claims.
 *
 */
// GOING TO BE USING THIS: http://stackoverflow.com/questions/24769257/custom-listview-adapter-with-filter-android 2015-03-23
public class ClaimantClaimListAdapter extends ArrayAdapter<Claim> implements Filterable{

	private Context context;
	private int layoutId;
	private ArrayList<Claim> originalClaimList = null;
	private ArrayList<Claim> filteredClaimList = null;
	private TagFilter myFilter = new TagFilter();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#0.00");

	public ClaimantClaimListAdapter(Context context, int textViewResourceId, ArrayList<Claim> items) {

		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.originalClaimList  = items;
		this.filteredClaimList = items;

	}
	
	private class ViewHolder {
		
		TextView claimNameTextView;
		TextView startDateTextView;
		TextView destinationsTextView;
		TextView statusTextView;
		TextView totalCurrencyView;
		TextView tagsTextView;
		
	}
	
    public int getCount() {
        return filteredClaimList.size();
    }
    
    public Claim getItem(int position) {
        return filteredClaimList.get(position);
    }
    
    public long getItemId(int position) {
        return position;
    }
    
    public Filter getFilter() {
    	return myFilter;
    }
    
    
	/**
	 * Updates the view of the Claims list of the claimant once created or shows the changes once they are edited
	 */
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
		
		// These holders update the data for the recently made or changed claim for the claimant claims
		Claim claim = originalClaimList.get(position);
		holder.claimNameTextView.setText(claim.getClaimName());
		holder.startDateTextView.setText(formatter.format(claim.getStartDate().getTime()));
		
		// This part of the code takes the updates from the destinations list view from the make a claim 
		// from the claimant and puts the new information to the claimants list view 
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
		// This part of the code takes the updates from the tags list view from the claimant claims 
		// and puts the new information to the claimants list view 
		ArrayList<Tag> tags = claim.getTags();
		String allTags = "";
		for (Tag tag: tags)
		{
			allTags += ""+ tag.getTagName()+ "  ";
		}
		
		holder.destinationsTextView.setText(allDest);
		holder.statusTextView.setText(claim.getStatus().toString());
		holder.tagsTextView.setText(allTags);
		
		// Set the total currencies first, then display currencies with amount > 0.
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
		
		return row;
	}
	
	
	   private class TagFilter extends Filter {

	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            filteredClaimList = (ArrayList<Claim>) results.values;
	            notifyDataSetChanged();
	        }

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO In progress
				return null;
			}

	    }

}
