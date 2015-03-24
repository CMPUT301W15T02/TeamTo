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

// Source:
// http://www.botskool.com/geeks/how-create-date-picker-dialog-selecting-date-android 2015-03-01

package com.CMPUT301W15T02.teamtoapp.Activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Adapters.ClaimantDestinationsListAdapter;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Controllers.UserController;
import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;
import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * Activity for both editing and adding a claim
 *
 */

public class ClaimEditActivity extends Activity implements Listener {
	private final Context context = this;
	
	private Button startDateTextView;
	private Button endDateTextView;
	private EditText claimNameEditText;
	private ListView destinationsListView;
	private ClaimListController claimListController;
	private DatePickerDialog startDatePickerDialog;
	private DatePickerDialog endDatePickerDialog;
	private String claimID;
	private Button addTagButton;
	private ListView tagListView;
	
	private ClaimController claimController;
	
	private ArrayList<StringTuple> destinations;
	private ArrayAdapter<StringTuple> destinationsAdapter;
	private ArrayAdapter<Tag> tagsAdapter;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_edit_delete_claim);
		getModelObjects();
		findViewsByIds();
        setListeners();
        setUpAdapter();
        setFieldValues();
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_edit_delete_claim_menu, menu);
		return true;
	}
	
	/**
	 * Saves the current claim, mostly replicates functionality of onBackPressed
	 * because we are saving in place
	 * @param menu
	 */
	public void onSaveButtonClick(MenuItem menu) {
		onBackPressed();
		
	}

	
	public void addDestinationOption(View view){
		// Pop up dialog to add another destination + reason.
		addDestination();
	}
	
	
	@Override
	public void onBackPressed() {
		claimListController.claimEditBackPressed(claimController.getCurrentClaim());
		finish();
	}
	
	/**
	 * Add destination will show a dialog that will allow a user to add a new destination and associated reason
	 */
	private void addDestination() {
		LayoutInflater inflater = LayoutInflater.from(getBaseContext());
		View addDestView = inflater.inflate(R.layout.add_destination_dialog, null);
		final EditText destinationEditText = (EditText) addDestView
				.findViewById(R.id.destinationEditText);
		final EditText reasonEditText = (EditText) addDestView
				.findViewById(R.id.reasonEditText);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(addDestView);
		// Saves the destination and adds it to the claim
		builder.setPositiveButton("Save", new DialogInterface.OnClickListener () {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				String destination = destinationEditText.getText().toString();
				String reason = reasonEditText.getText().toString();
				destination.trim();
				reason.trim();
				
				// Assumption: Destination must be filled in before adding, adding reason is optional for now...
				if (destination.length() != 0) {
					claimController.addDestination(destination, reason);
					
				} else {
					Toast.makeText(context, "Must enter destination!", Toast.LENGTH_SHORT).show();
				}
			}
		})
		// Does nothing
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// Do nothing
			}
		}).create().show();
	}
	
	/**
	 * Gets all of the model objects, initializes the controllers, adds the activity as an observer to the current claim
	 */
	private void getModelObjects() {
		Intent intent = getIntent();
		claimID = (String) intent.getSerializableExtra("claimID");
		// Make new controller from the claimID that was passed in
		claimController = new ClaimController(claimID);
		destinations = claimController.getDestinations();
		// Make this activity observe the new claim
		claimController.addListenerToClaim(this);
		// Claim list controller will have a minor role of just adding the claim to the list of claims
		claimListController = new ClaimListController();
	}
	
	/**
	 * Gets all of the views needed for the activity
	 */
	private void findViewsByIds() {
		startDateTextView = (Button) findViewById(R.id.startDateTextView);
        endDateTextView = (Button) findViewById(R.id.endDateTextView);
        claimNameEditText = (EditText) findViewById(R.id.claimNameEditText);
        destinationsListView = (ListView) findViewById(R.id.destinationListView);
        addTagButton = (Button) findViewById(R.id.claimantAddTagsButton);
    	tagListView = (ListView) findViewById(R.id.claimantTagsListView);
	}
	
	/**
	 * Sets up all of the listeners on the different views
	 */
	private void setListeners() {
		
		/**
		 * Sets up a listener that will show a date picke dialog on click
		 */
		startDateTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startDatePickerDialog.show();
            }
        });
        
		/**
		 * Sets up a listener that will show a date picke dialog on click
		 */
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endDatePickerDialog.show();
            }
        });
		
		Calendar startDate = claimController.getStartDate();
		// Date picker dialog for start date
		startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				claimController.setStartDate(calendar);
			}
			// Set the default date in the dialog
		}, startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
		
		
		Calendar endDate = claimController.getEndDate();
		// Date picker dialog for end date
		endDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				claimController.setEndDate(calendar);
			}
			// Set the default date in the dialog
		}, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
		
		/**
		 * Saves the claim name
		 */
		claimNameEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Intentionally Blank
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Intentionally blank
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				claimController.setClaimName(s.toString());
				
			}
		});
		
		/**
		 * Sets a listener that allows users to delete destinations from the list
		 * May add functionality to edit destinations also
		 */
		destinationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position,
					long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ClaimEditActivity.this);
				builder.setMessage("Delete Destination?");
				builder
				// Delete the destination
				.setPositiveButton("Delete", new DialogInterface.OnClickListener () {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringTuple destination = claimController.getDestinations().get(position);
						claimController.removeDestination(destination);
					}
				})
				// Do nothing
				.setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do nothing
					}
					
				}).create().show();
			}
		});
		
		/**
		 * Button to add a tag to the current claim
		 */
		addTagButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get the list of available tags
				final ArrayList<Tag> tags = new UserController().getTags();
				
				// Make an array of the same size containing string representations of the tags
				CharSequence[] strings = new CharSequence[tags.size()];
				
				// Get the current tags on the claim
				ArrayList<Tag> claimTags = claimController.getTags();
				
				// Create a boolean array of the same size
				boolean[] boolArray = new boolean[tags.size()];
				
				// Add the tag from the user tags into the array of strings
				for (int i = 0; i < tags.size(); i++) {
					strings[i] = tags.get(i).toString();
					// If the tag is in the list of tags then set true
					if (claimTags.contains(tags.get(i))) {
						boolArray[i] = true;
						// Otherwise set false
					} else {
						boolArray[i] = false;
					}
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(ClaimEditActivity.this);
				builder.setTitle("Select tags").setMultiChoiceItems(strings, boolArray, new DialogInterface.OnMultiChoiceClickListener() {
					// Add or remove tags based on the boolean array
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) {
							claimController.addTag(tags.get(which));
						} else if (claimController.getTags().contains(tags.get(which))){
							claimController.removeTag(tags.get(which));
						}
					}
				}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Does nothing
						
					}
				}).create().show();
				
				
			}
		});
		
	}

	/**
	 * Sets up the adapters for the destinations and the list of tags
	 */
	private void setUpAdapter() {
		destinationsAdapter = new ClaimantDestinationsListAdapter(context, R.layout.claimant_destination_rows, destinations);
		destinationsListView.setAdapter(destinationsAdapter);
		tagsAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, claimController.getTags());
		tagListView.setAdapter(tagsAdapter);
	}
	
	
	/**
	 * Sets the values of the different fields
	 * Also calls updateValues() for fields that will be changed when a change happens to the model
	 */
	private void setFieldValues() {
		updateValues();
		if (claimController.getClaimName().equals("New Claim")) {
			claimNameEditText.setHint("Enter a claim name");
		} else {
			claimNameEditText.setText(claimController.getClaimName());
		}
	}
	
	/**
	 * Called when a change happens to the model that lets it know to update
	 */
	private void updateValues() {
		startDateTextView.setText(claimController.getStartDateFormatted());
		endDateTextView.setText(claimController.getEndDateFormatted());
	}

	
	/**
	 * The observed object calls this function to tell it to update
	 * It updates fields that might change and tells the adapters to refresh
	 */
	@Override
	public void update() {
		updateValues();
		destinationsAdapter.notifyDataSetChanged();
		tagsAdapter.notifyDataSetChanged();
		
	}
	

	
	/**
	 * Called when the activity is destroyed, thus we remove this activity as an observer
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		claimController.removeListenerFromClaim(this);
	}
	
	
}
