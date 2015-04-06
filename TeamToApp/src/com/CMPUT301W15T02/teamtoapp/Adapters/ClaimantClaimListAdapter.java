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

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Destination;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;

/**
 * 
 * A customized adapter for the claimant's list of claims.
 * 
 * @see ClaimantClaimsListActivity.java
 * @see http://stackoverflow.com/questions/24769257/custom-listview-adapter-with-filter-android 2015-03-23 
 * @see http://stackoverflow.com/questions/5936912/how-to-find-the-distance-between-two-geopoints 2015-04-02
 * 
 * @authors  Kyle Carlstrom, Raman Dhatt
 *
 */

public class ClaimantClaimListAdapter extends ArrayAdapter<Claim>{

	private Context context;
	private int layoutId;
	// Filtered claim list for filtering tags
	private ArrayList<Claim> filteredClaimList = new ArrayList<Claim>(); 
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#0.00");
	
	// Location for user's home location 
	Location destLocation = new Location(Context.LOCATION_SERVICE);
	
	// Location for first destination of user's claim
	Location userLocation = new Location(Context.LOCATION_SERVICE);

	
	/**
	 * ClaimantClaimListAdapter constructor 
	 * 
	 * @param context - context of application
	 * @param textViewResourceId - ID of textview resource
	 * @param items - list of claimant's claims
	 */
	public ClaimantClaimListAdapter(Context context, int textViewResourceId, ArrayList<Claim> items) {

		super(context, textViewResourceId, items);
		this.context = context;
		this.layoutId = textViewResourceId;
		this.filteredClaimList = items;

	}
	
	/**
	 * ViewHolder class that contains TextViews
	 * for the claimant's claim list view
	 *
	 */
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
    
    
    
	/**
	 * getView method updates the view of the Claims list of the claimant once 
	 * created or shows the changes once they are edited
	 * 
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
			
			// Initialize textview holders to textview id's from claimant_claims_list_rows.xml
			holder.claimNameTextView = (TextView) row.findViewById(R.id.claimantClaimNameView);
			holder.startDateTextView = (TextView) row.findViewById(R.id.claimantStartDateView);
			holder.destinationsTextView = (TextView) row.findViewById(R.id.claimantListDestsView);
			holder.statusTextView = (TextView) row.findViewById(R.id.claimantStatusView);
			holder.totalCurrencyView = (TextView) row.findViewById(R.id.claimantTotalCurrencyView);
			holder.tagsTextView = (TextView) row.findViewById(R.id.claimantTagsListView); 
			
			// Set tag for holder
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		// These holders update the data for the recently made or changed claim for the claimant claims
		Claim claim = getItem(position);
		
		
		holder.claimNameTextView.setText(claim.getClaimName());
		holder.startDateTextView.setText(formatter.format(claim.getStartDate().getTime()));
		
		// Format destinations for output
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
		
		// Format tags for output
		ArrayList<Tag> tags = claim.getTags();
		String allTags = "";
		for (Tag tag: tags)
		{
			allTags += ""+ tag.getTagName()+ "  ";
		}
		
		// Set text for destinations fo claim
		holder.destinationsTextView.setText(allDest);
		
		// Set text for claim status
		holder.statusTextView.setText(claim.getStatus().toString());
		
		// Set text for tags of claim
		holder.tagsTextView.setText(allTags);
		
		// Obtain total currencies and display currencies with amount > 0.
		claim.setTotalCurrencies();
		String totalCurrencyOuput = totalCurrencyOuput(claim);
		// Set text of total currencies
		holder.totalCurrencyView.setText(totalCurrencyOuput);
		
		
		if (claim.getDestinations().size() > 0) {
			// If destinations exist, obtain the latitidue and longitude for the first destination
			double lat = claim.getDestinations().get(0).latitude;
			double lon = claim.getDestinations().get(0).longitude;
			
			destLocation.setLatitude(lat);
			destLocation.setLongitude(lon);
			
			// Obtain the distance between home location and destination location
			double distanceBetween = userLocation.distanceTo(destLocation);
			if (distanceBetween > 5000000) {
				// display distant row colour
				row.setBackgroundResource(R.drawable.listview_selector_distant);
			} else {
				
				// display nearby row colour
				row.setBackgroundResource(R.drawable.listview_selector_nearby);
			}
		}
		
		return row;
	}

	/**
	 * This method returns the total currencies of all expense of a claim,
	 * which will be part of the holder for the adapter.
	 * 
	 * @param claim - claim that has currencies in each of its expenses
	 * @return totalCurrencyOutput - string of total currencies
	 */
	private String totalCurrencyOuput(Claim claim) {
		SortedMap<String, Double> map = Collections.synchronizedSortedMap(claim
				.getTotalCurrencies());
		String totalCurrencyOuput = "";
		for (String key : map.keySet()) {
			totalCurrencyOuput += df.format(map.get(key)) + " "
					+ key.toString() + ", ";
		}
		if (totalCurrencyOuput.length() > 3) {
			totalCurrencyOuput = totalCurrencyOuput.substring(0,
					totalCurrencyOuput.length() - 2);
		}
		return totalCurrencyOuput;
	}
	
	

}
