package com.CMPUT301W15T02.teamtoapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ClaimantAddClaimActivity extends Activity {
	private final Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claimant_claim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_add_claim_menu, menu);
		return true;
	}
	
	public void onSaveNewClaimClick(MenuItem menu) {
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
}
