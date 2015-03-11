package com.CMPUT301W15T02.teamtoapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ApproverExpenseListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_expense_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.approver_expense_list, menu);
		return true;
	}

}
