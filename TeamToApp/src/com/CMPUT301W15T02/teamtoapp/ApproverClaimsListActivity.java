package com.CMPUT301W15T02.teamtoapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class ApproverClaimsListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claims_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_claims_list_menu, menu);
		return true;
	}

	public void filterByDate(MenuItem menu) {
		
	}
	
	public void onClaimClick() {
		// TODO: Go to ApproverExpenseListActivity.java
	}
	
	public void switchToClaimantOption(MenuItem menu) {
		// Not really sure if this is the correct way to do it.
		// Changes by approver should be saved before going back.
		super.onBackPressed();
	}
}
