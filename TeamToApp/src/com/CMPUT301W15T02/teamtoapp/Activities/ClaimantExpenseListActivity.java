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

package com.CMPUT301W15T02.teamtoapp.Activities;

import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Adapters.ClaimantExpenseListAdapter;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClaimantExpenseListActivity extends Activity {

	private Context context = this;
	private ListView expenseListView;
	private TextView claimNameTextView;
	private TextView claimStartDateTextView;
	private TextView claimEndDateTextView;
	
	private String claimID;
	private ClaimController claimController;
	private ArrayList<Expense> expenses;
	private ClaimantExpenseListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_expense_list);
		getModelObjects();
		findViewsByIds();
		setFieldValues();
		setListeners();
		setUpAdapter();
		
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
			Expense expense = new Expense();
			claimController.addExpense(expense);
			Intent intent = new Intent(getBaseContext(), ExpenseEditActivity.class);
			intent.putExtra("expenseID", expense.getExpenseId());
			intent.putExtra("claimID", claimID);
			startActivity(intent);
			return true;
		} else if (id == R.id.submitClaimOption) {
			submitClaim();
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void getModelObjects() {
		Intent intent = getIntent();
		claimID = (String) intent.getSerializableExtra("claimID");
		claimController = new ClaimController(claimID);
		expenses = claimController.getExpenses();
	}
	
	private void findViewsByIds() {
		expenseListView = (ListView) findViewById(R.id.claimantExpenseListView);
		claimNameTextView = (TextView) findViewById(R.id.claimNameTextView);
		claimStartDateTextView = (TextView) findViewById(R.id.claimStartDateTextView);
		claimEndDateTextView = (TextView) findViewById(R.id.claimEndDateTextView);
	}
	
	private void setFieldValues() {
		claimNameTextView.setText(claimController.getClaimName());
		claimStartDateTextView.setText(claimController.getStartDateFormatted());
		claimEndDateTextView.setText(claimController.getEndDateFormatted());
	}
	
	private void setListeners() {
		expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Expense expense = claimController.getExpenses().get(position);
				Intent intent;
				if (claimController.isEditable()) {
					intent = new Intent(context, ExpenseEditActivity.class);
				} else {
					intent = new Intent(context, ExpenseViewActivity.class);
				}
				intent.putExtra("expenseID", expense.getExpenseId());
				startActivity(intent);
			}
			
		});
		expenseListView.setLongClickable(true);
		expenseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				if (claimController.isEditable()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ClaimantExpenseListActivity.this);
					builder.setMessage("Do you want to delete this Expense?");
					builder
					.setPositiveButton("No", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							//Closes the dialog box (Does nothing)

						}
					})
					.setNegativeButton("Yes", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id) {
							//Deletes the expense of the current user
							Expense expense = claimController.getExpenses().get(position);
							claimController.removeExpense(expense);
							adapter.notifyDataSetChanged();

						}
					}).create().show();
				} else {
					Toast.makeText(context, "Can't delete expenses when claim is submitted or approved", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		}
	
	private void setUpAdapter() {
		adapter = new ClaimantExpenseListAdapter(this, R.layout.claimant_expense_list_rows, expenses);
		expenseListView.setAdapter(adapter);
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
	
	private void submitClaim() {
		// TODO: Check claim and expenses depending on current status
		String result = claimController.checkBeforeSubmittingClaim();
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	}
		
}
