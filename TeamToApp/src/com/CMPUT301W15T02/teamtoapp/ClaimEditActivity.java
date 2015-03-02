// Source:
// http://www.botskool.com/geeks/how-create-date-picker-dialog-selecting-date-android 2015-03-01

package com.CMPUT301W15T02.teamtoapp;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ClaimEditActivity extends Activity {
	private final Context context = this;
	private TextView startDateTextView;
	private TextView endDateTextView;
	
	private DatePickerDialog startDatePickerDialog;
	private DatePickerDialog endDatePickerDialog;
	
	private Button pickStartBtn;
	private Button pickEndBtn;
	
	private ClaimController claimController = new ClaimController();
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_edit_delete_claim);
		  
		findViewsByIds();
        setListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_edit_delete_claim_menu, menu);
		return true;
	}
	
	public void onSaveButtonClick(MenuItem menu) {
		// Perform actions once claimant saves new claim
	}

	public void addDestinationOption(View view){
		// Pop up dialog to add another destination + reason.
		addDestination();
	}
	
	private void addDestination() {
		LayoutInflater add_dest = LayoutInflater.from(getBaseContext());
		View addDestView = add_dest.inflate(R.layout.add_destination_dialog, null);
		final EditText dest = (EditText) addDestView
				.findViewById(R.id.destinationEditText);
		final EditText reason = (EditText) addDestView
				.findViewById(R.id.reasonEditText);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(addDestView);
		builder.setPositiveButton("Save", new DialogInterface.OnClickListener () {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Save destination + reason
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
	
	private void findViewsByIds() {
		startDateTextView = (TextView) findViewById(R.id.startDateTitle);
        endDateTextView = (TextView) findViewById(R.id.endDateTitle);
        pickStartBtn = (Button) findViewById(R.id.startDateBtn);
        pickEndBtn = (Button) findViewById(R.id.endDateBtn);
	}
	
	
	private void setListeners() {
		
		pickStartBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startDatePickerDialog.show();
            }
        });
        

        pickEndBtn.setOnClickListener(new View.OnClickListener() {
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
				endDateTextView.setText(
			            new StringBuilder()
	                    // Month is 0 based so add 1
	                    .append(monthOfYear + 1).append("/")
	                    .append(dayOfMonth).append("/")
	                    .append(year).append(" "));
				
			}
			
		}, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
		
	}

	
}
