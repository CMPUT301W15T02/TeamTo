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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Adapters.ClaimantDestinationsListAdapter;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Controllers.UserController;
import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.R.id;
import com.CMPUT301W15T02.teamtoapp.R.layout;
import com.CMPUT301W15T02.teamtoapp.R.menu;

import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader.ForceLoadContentObserver;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.UpdateAppearance;
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
import android.widget.TextView;
import android.widget.Toast;

public class ClaimEditActivity extends Activity implements Observer {
	private final Context context = this;
	
	private Button startDateTextView;
	private Button endDateTextView;
	private EditText claimNameEditText;
	private ListView destinationsListView;
	private ClaimListController claimListController;
	private DatePickerDialog startDatePickerDialog;
	private DatePickerDialog endDatePickerDialog;
	private String claimID;
	//TODO: ADD FUNCTIONALITY TO TAGS BUTTON AND TO TAGS LIST reference 
	// http://theopentutorials.com/tutorials/android/listview/android-multiple-selection-listview/
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
		claimListController = new ClaimListController();
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
	
	public void onSaveButtonClick(MenuItem menu) {
		onBackPressed();
		
	}

	
	public void addDestinationOption(View view){
		// Pop up dialog to add another destination + reason.
		addDestination();
	}
	
	
	@Override
	public void onBackPressed() {
		// Makes sure a claim is deleted only when nothing entered.
		if (claimController.getCurrentClaim().getClaimName().equals("") &&
				claimController.getDestinations().size() == 0) {
		} else if (!claimListController.getClaims().contains(claimController.getCurrentClaim())) {
			claimListController.addClaim(claimController.getCurrentClaim());
		}
		finish();
	}
	
	
	private void addDestination() {
		LayoutInflater inflater = LayoutInflater.from(getBaseContext());
		View addDestView = inflater.inflate(R.layout.add_destination_dialog, null);
		final EditText destinationEditText = (EditText) addDestView
				.findViewById(R.id.destinationEditText);
		final EditText reasonEditText = (EditText) addDestView
				.findViewById(R.id.reasonEditText);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(addDestView);
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
		
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// Do nothing
			}
		});
		
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	// Also where we set up observer
	private void getModelObjects() {
		Intent intent = getIntent();
		claimID = (String) intent.getSerializableExtra("claimID");
		claimController = new ClaimController(claimID);
		destinations = claimController.getDestinations();
		claimController.addObserverToClaim(this);
	}
	
	
	private void findViewsByIds() {
		startDateTextView = (Button) findViewById(R.id.startDateTextView);
        endDateTextView = (Button) findViewById(R.id.endDateTextView);
        claimNameEditText = (EditText) findViewById(R.id.claimNameEditText);
        destinationsListView = (ListView) findViewById(R.id.destinationListView);
        addTagButton = (Button) findViewById(R.id.claimantAddTagsButton);
    	tagListView = (ListView) findViewById(R.id.claimantTagsListView);
	}
	
	
	private void setListeners() {
		
		startDateTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startDatePickerDialog.show();
            }
        });
        

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endDatePickerDialog.show();
            }
        });
		
		Calendar startDate = claimController.getStartDate();
		
		startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				claimController.setStartDate(calendar);
			}
			
		}, startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
		
		Calendar endDate = claimController.getEndDate();
		endDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				claimController.setEndDate(calendar);
				
			}
			
		}, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
		
		// TODO: What if user edited existing claim and saved a blank claim name? Save "Existing Claim" as default.
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
		
		destinationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position,
					long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(ClaimEditActivity.this);
				builder.setMessage("Delete Destination?");
				builder
				.setPositiveButton("Delete", new DialogInterface.OnClickListener () {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringTuple destination = claimController.getDestinations().get(position);
						claimController.removeDestination(destination);
						destinationsAdapter.notifyDataSetChanged();
					}
				})
				.setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do nothing
					}
					
				});
				
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});
		
		addTagButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO make this less hack jobish
				UserController userController = new UserController();
				CharSequence[] strings = new CharSequence[userController.getTags().size()];
				final ArrayList<Tag> tags = userController.getTags();
				ArrayList<Tag> claimTags = claimController.getTags();
				boolean[] boolArray = new boolean[tags.size()];
				for (int i = 0; i < tags.size(); i++) {
					strings[i] = tags.get(i).toString();
					if (claimTags.contains(tags.get(i))) {
						boolArray[i] = true;
					} else {
						boolArray[i] = false;
					}
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(ClaimEditActivity.this);
				builder.setTitle("Select tags").setMultiChoiceItems(strings, boolArray, new DialogInterface.OnMultiChoiceClickListener() {
					
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
						// TODO
						
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).create().show();
				
				
			}
		});
		
	}

	private void setUpAdapter() {
		destinationsAdapter = new ClaimantDestinationsListAdapter(context, R.layout.claimant_destination_rows, destinations);
		destinationsListView.setAdapter(destinationsAdapter);
		tagsAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, claimController.getTags());
		tagListView.setAdapter(tagsAdapter);
	}
	
	private void setFieldValues() {
		updateValues();
		if (claimController.getClaimName().equals("New Claim")) {
			claimNameEditText.setHint("Enter a claim name");
		} else {
			claimNameEditText.setText(claimController.getClaimName());
		}
	}
	
	private void updateValues() {
		startDateTextView.setText(claimController.getStartDateFormatted());
		endDateTextView.setText(claimController.getEndDateFormatted());
	}

	@Override
	public void update(Observable observable, Object data) {
		updateValues();
		destinationsAdapter.notifyDataSetChanged();
		tagsAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		claimController.removeObserverFromClaim(this);
	}
	
	
	
}
