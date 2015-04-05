/* Copyright 2015 Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
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
package com.CMPUT301W15T02.teamtoapp.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Adapters.ClaimantClaimListAdapter;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Controllers.UserController;
import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorNewestFirst;


/**
 * The activity that the claimant will come to if they have already logged in.
 * Contains a list of their claims and has the ability to add new claims, manage tags,
 * and switch to approver mode
 * 
 * @authors Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt
 */

public class ClaimantClaimsListActivity extends Activity implements Listener {
	
	private ClaimListController claimListController;
	final Context context = this;
	private ListView listView;
	private ClaimantClaimListAdapter adapter;
	private ArrayList<String> mSelectedItems;
	private UserController userController;
	
	private static final int GET_GEOLOCATION_REQUEST_CODE = 112;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* Set up all model objects, widgets, field values, 
		 * listeners, and claim list adapter
		 */
		setContentView(R.layout.claimant_claims_list);
		getModelObjects();
		findViewsByIds();
		setUpAdapter();
		setListeners();
	}
	
	
	
	/**
	 * Initializes all of the model objects and controllers needed throughout the activity
	 */
	private void getModelObjects() {
		// Initialize objects
		// Set the current user so it can be added to claims
		mSelectedItems = new ArrayList<String>();
		claimListController = new ClaimListController();
		claimListController.addListenerToClaimList(this);
		userController = new UserController();
	}
	
	/**
	 * Finds all of the necessary views need for the activity
	 */
	private void findViewsByIds() {
		listView = (ListView) findViewById(R.id.claimantClaimListView);
	}
	
	/**
	 * Sets up all of the listeners needed for the activity
	 */
	private void setListeners() {
		/**
		 * When an item in the list is clicked, user taken to expenseListActivity
		 */
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Claim claim = adapter.getItem(position);
				Intent intent = new Intent(ClaimantClaimsListActivity.this, ClaimantExpenseListActivity.class);
				intent.putExtra("claimID", claim.getClaimId());
				startActivity(intent);
			}
		});
		/**
		 * When an item in the list is long clicked, a dialog is shown that allows the user to edit or delete the claim
		 * Dialog is only shown if the claim is currently editable, otherwise it toasts a warning		
		 */
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				// Get the current claim
				ClaimController claimController = new ClaimController(adapter.getItem(position).getClaimId());
				// Check if it is editable, if it is then show the edit/delete dialog
				if (claimController.isEditable()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ClaimantClaimsListActivity.this);
					builder.setMessage("Edit or Delete Claim?");
					builder
					.setPositiveButton("Edit", new DialogInterface.OnClickListener () {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// When edit is clicked add an extra to the intent and start the ClaimEditActivity
							Claim claim = adapter.getItem(position);
							Intent intent = new Intent(ClaimantClaimsListActivity.this, ClaimEditActivity.class);
							intent.putExtra("claimID", claim.getClaimId());
							startActivity(intent);
						}
					})
					.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// When delete is clicked remove the current claim
							Claim claim = adapter.getItem(position);
							claimListController.removeClaim(claim);
							
							if (mSelectedItems.size() > 0) {
								setUpFilteredAdapter();
							}
						}
					}).create().show();
				} else {
					// If the expense is not editable then alert the user
					Toast.makeText(context, "Cannot edit/delete this claim", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
	}
	
	/**
	 * Filter by tags method
	 */
	private void filterByTags() {

		// going to be using this tomorrow: http://stackoverflow.com/questions/5122974/multiple-choice-searchable-listview
		// 2015-03-24
		AlertDialog.Builder builder = new AlertDialog.Builder(ClaimantClaimsListActivity.this);
		// Get the list of available tags
		mSelectedItems.clear();
		ArrayList<Tag> tags = User.getInstance().getTags();
		final CharSequence[] strings = new CharSequence[tags.size()];
		final boolean[] boolArray = new boolean[tags.size()];
		for (int i = 0; i < tags.size(); i++) {
			strings[i] = tags.get(i).toString();
			boolArray[i] = false;
		}
		builder.setMultiChoiceItems(strings, boolArray, new DialogInterface.OnMultiChoiceClickListener() {
			// Add or remove tags based on the boolean array
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					mSelectedItems.add((String) strings[which]);
					
				} else if (mSelectedItems.contains(strings[which])) {
					mSelectedItems.remove(strings[which]);
				}
				for (int i = 0; i < boolArray.length; i++){
					boolArray[i] = false;
				}
			}
		}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Does nothing
				if (mSelectedItems.size() > 0) {
					setUpFilteredAdapter();
				} else {
					setUpAdapter();
				}
				
			}
		}).create().show();
		
		
	}

	
	/**
	 * Sets up claim list adapter and binds it to the current list view
	 */
	private void setUpAdapter() {
		adapter = new ClaimantClaimListAdapter(context, R.layout.claimant_claims_list_rows, claimListController.getClaims());
		adapter.sort(new ClaimComparatorNewestFirst());
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}
	
	/**
	 * Sets up claim list adapter for filtered claims. Originally had filter but that no longer works
	 */
	private void setUpFilteredAdapter() {
		adapter = new ClaimantClaimListAdapter(context, R.layout.claimant_claims_list_rows, claimListController.getFilteredClaims(mSelectedItems));
		adapter.sort(new ClaimComparatorNewestFirst());
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_claims_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.tagsManagerButton) {
			// Switch to TagManagerActivity
			Intent intent = new Intent(getBaseContext(), TagManagerActivity.class);
			startActivity(intent);
		} else if (id == R.id.addClaimOp) {
			// Go to ClaimEditActivity to add new claim.
			Intent intent = new Intent(getBaseContext(), ClaimEditActivity.class);
			intent.putExtra("claimID", "");
			startActivity(intent);	
		} else if (id == R.id.refreshClaims) {
			if (MainManager.isNetworkAvailable(getApplicationContext())) {
				// Cache.getInstance().updateAndClear();
				Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "No network access", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.filterByTags) {
			filterByTags();
		} else if (id == R.id.setHomeLocation) {
			onGoogleMapBtnClicked();
		}
		return super.onOptionsItemSelected(item);
	}
	

	
	/**
	 * Button that switches the claimant to the approver mode only if there is internet connectivity
	 * @param menu	the button that was clicked
	 */
	public void switchToApproverOption(MenuItem menu) {
		// Switch to ApproverClaimListActivity.class if online
		if (MainManager.isNetworkAvailable(this)) {
			Intent intent = new Intent(ClaimantClaimsListActivity.this, ApproverClaimsListActivity.class);
			startActivity(intent);
			
		}
		else {
			// Alert the user that there is no internet access
			Toast.makeText(this, "No internet access!", Toast.LENGTH_SHORT).show();
		}

	}
	
	/**
	 * Button that allows user to set their home location on click
	 */
	public void onGoogleMapBtnClicked() {
		Intent intent = new Intent(ClaimantClaimsListActivity.this, HomeGeoLocationActivity.class);
		intent.putExtra("latitude", userController.getHomeLatitude());
		intent.putExtra("longitude", userController.getHomeLongitude());
		startActivityForResult(intent, GET_GEOLOCATION_REQUEST_CODE);
	}
	
	
	

	/**
	 * Receives the resulting latitude and longtitude 
	 * values from the HomeGeoLocation activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (requestCode == GET_GEOLOCATION_REQUEST_CODE && resultCode == RESULT_OK) {
			 
			 // Save longitude and latitude in user model via user controller
			 double latitude = data.getDoubleExtra("latitude", 0.0);
			 double longitude = data.getDoubleExtra("longitude", 0.0);
			 Log.i("LATITUDE", String.valueOf(latitude));
			 Log.i("LONGITUDE", String.valueOf(longitude));
			 userController.setHomeLatitude(latitude);
			 userController.setHomeLongitude(longitude);
			 
			 // Save changes of the user location
			 MainManager.saveUser();
		 }
	}



	@Override
	protected void onResume() {
		super.onResume();
		/* Setting up the adapter again to get rid of 
		 * any filtering that may have occurred
		 */
		setUpAdapter();
	}

	/**
	 * Called when the activity is destroyed
	 * Observer is removed because it is no longer needed
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		claimListController.removeListenerFromClaimList(this);
	}


	
	/**
	 * Observer functions that is called when the model changed
	 * Updates the list view if anything has changed
	 */
	@Override
	public void update() {
		adapter.notifyDataSetChanged();
		// Sort list view by start date
		adapter.sort(new ClaimComparatorNewestFirst());
	}
	
	
	
	
	
	
}
