// Source:
// http://www.botskool.com/geeks/how-create-date-picker-dialog-selecting-date-android 2015-03-01

package com.CMPUT301W15T02.teamtoapp;

import java.util.Calendar;

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
	private TextView startDate;
	private TextView endDate;
	private int _day;
	private int _month;
	private int _year;
	private Button pickStartBtn;
	private Button pickEndBtn;
	
	static final int DATE_DIALOG_ID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_edit_delete_claim);
		 
        startDate = (TextView) findViewById(R.id.startDateTitle);
        endDate = (TextView) findViewById(R.id.endDateTitle);
        pickStartBtn = (Button) findViewById(R.id.startDateBtn);
        pickEndBtn = (Button)findViewById(R.id.endDateBtn); 
 

        pickStartBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        

        pickEndBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
 
        /** Current Date as default for start and end dates */
        final Calendar cal = Calendar.getInstance();
        _year = cal.get(Calendar.YEAR);
        _month = cal.get(Calendar.MONTH);
        _day = cal.get(Calendar.DAY_OF_MONTH);
 
        updateStartDateDisplay();
        updateEndDateDisplay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_edit_delete_claim_menu, menu);
		return true;
	}
	
	public void onSaveButtonClick(MenuItem menu) {
		// Perform actions once claimant saves new claim
	}

	public void addDestinationOption(MenuItem menu){
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
	
	
    /** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener startDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
 
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    _year = year;
                    _month = monthOfYear;
                    _day = dayOfMonth;
                    updateStartDateDisplay();
                }
            };
    
    private DatePickerDialog.OnDateSetListener endDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
         
                 public void onDateSet(DatePicker view, int year,
                                       int monthOfYear, int dayOfMonth) {
                	 _year = year;
                	 _month = monthOfYear;
                	 _day = dayOfMonth;
                	 updateEndDateDisplay();
                 }
            };
            
    /** Update Start Date View */
    private void updateStartDateDisplay() {
    	startDate.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(_month + 1).append("/")
                    .append(_day).append("/")
                    .append(_year).append(" "));
    }
    
    /** Update End Date View */
    private void updateEndDateDisplay() {
    	endDate.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(_month + 1).append("/")
                    .append(_day).append("/")
                    .append(_year).append(" "));
    }
     
 
    /** Create a new dialog for date picker depending on start or end date */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        startDateSetListener,
                        _year, _month, _day);
        }
        return new DatePickerDialog(this,
                endDateSetListener,
                _year, _month, _day);
    }
	
}
