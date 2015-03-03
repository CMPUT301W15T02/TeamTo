package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ClaimantExpenseListActivity extends Activity {

	private ListView claimaintExpenseListView;
	private ClaimController claimController = new ClaimController();
	private ArrayList<Expense> expenses;
	private ClaimantExpenseListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_expense_list);
		expenses = claimController.getExpenses();
		claimaintExpenseListView = (ListView) findViewById(R.id.claimantExpenseListView);
		adapter = new ClaimantExpenseListAdapter(this, R.layout.claimant_expense_list_rows, expenses);
		claimaintExpenseListView.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claimant_expense_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.addExpenseMenuButton) {
			claimController.addExpense(new Expense());
			Intent intent = new Intent(getBaseContext(), ExpenseEditActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
}
