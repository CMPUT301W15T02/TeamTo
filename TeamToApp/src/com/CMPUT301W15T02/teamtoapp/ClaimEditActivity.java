// Source:
// http://www.botskool.com/geeks/how-create-date-picker-dialog-selecting-date-android 2015-03-01

package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClaimEditActivity extends Activity {
	private final Context context = this;
	
	private TextView startDateTextView;
	private TextView endDateTextView;
	private EditText claimNameEditText;
	private ListView destinationsListView;
	
	private DatePickerDialog startDatePickerDialog;
	private DatePickerDialog endDatePickerDialog;
	private String claimID;
	
	private ClaimController claimController;
	
	private ArrayList<StringTuple> destinations;
	ArrayAdapter<StringTuple> adapter;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_edit_delete_claim);
		
		getModelObjects();
		findViewsByIds();
        setListeners();
        setUpAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_edit_delete_claim_menu, menu);
		return true;
	}
	
	public void onSaveButtonClick(MenuItem menu) {
		// TODO Save all input once claimant saves new/existing editable claim
		super.onBackPressed();
	}

	
	public void addDestinationOption(View view){
		// Pop up dialog to add another destination + reason.
		addDestination();
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
					adapter.notifyDataSetChanged(); // TODO Have this call automatically when model changes
					// DONE: made string representation display with custom list view adapter.
					
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
	
	
	private void getModelObjects() {
		Intent intent = getIntent();
		claimID = (String) intent.getSerializableExtra("claimID");
		claimController = new ClaimController(claimID);
		destinations = claimController.getDestinations();
	}
	
	
	private void findViewsByIds() {
		startDateTextView = (TextView) findViewById(R.id.startDateTextView);
        endDateTextView = (TextView) findViewById(R.id.endDateTextView);
        claimNameEditText = (EditText) findViewById(R.id.claimNameEditText);
        destinationsListView = (ListView) findViewById(R.id.destinationListView);
        
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
				Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				claimController.setStartDate(calendar);
				// Probably won't need this once mvc is working
				// TODO Save Start Date
				startDateTextView.setText(
			            new StringBuilder()
	                    // Month is 0 based so add 1
	                    .append(monthOfYear + 1).append("/")
	                    .append(dayOfMonth).append("/")
	                    .append(year).append(" "));
			}
			
		}, startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
		
		Calendar endDate = claimController.getEndDate();
		endDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				claimController.setEndDate(calendar);
				// TODO Save End Date
				endDateTextView.setText(
			            new StringBuilder()
	                    // Month is 0 based so add 1
	                    .append(monthOfYear + 1).append("/")
	                    .append(dayOfMonth).append("/")
	                    .append(year).append(" "));
				
			}
			
		}, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
		
		// TODO: What if user edited existing claim and saved a blank claim name? Save "Existing Claim" as default.
		claimNameEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				claimController.setClaimName(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Intentionally blank
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// Intentionally blank
				
			}
		});
		
	}

	
	private void setUpAdapter() {
		adapter = new ClaimantDestinationsListAdapter(context, R.layout.claimant_claims_list_rows, destinations);
		destinationsListView.setAdapter(adapter);
	}
	
}
