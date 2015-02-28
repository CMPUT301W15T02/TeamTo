package com.CMPUT301W15T02.teamtoapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ApproverClaimsListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claims_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_claims_list_menu, menu);
		return true;
	}

}
