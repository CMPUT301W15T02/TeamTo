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
import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;
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

/**
 * 
 * Activity where a user can see the overview of their claim including all of their expenses.
 * Claimants can add, delete, and edit the expenses when the claim is editable
 *
 */

public class ClaimantExpenseListActivity extends Activity implements Listener {

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
		getMenuInflater().inflate(R.menu.claimant_expense_list_menu, menu);
		MenuItem addButton = menu.findItem(R.id.addExpenseMenuButton);
		if (!claimController.isEditable()) {
			addButton.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.addExpenseMenuButton) {
			// Send in blank expense, will create a new expense if null
			Intent intent = new Intent(getBaseContext(), ExpenseEditActivity.class);
			intent.putExtra("expenseID", ""); // Send in null
			intent.putExtra("claimID", claimID);
			startActivity(intent);
			return true;
		} else if (id == R.id.submitClaimOption) {
			submitClaim();
			invalidateOptionsMenu();
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Gets the necessary objects and controllers
	 */
	private void getModelObjects() {
		Intent intent = getIntent();
		claimID = (String) intent.getSerializableExtra("claimID");
		claimController = new ClaimController(claimID);
		expenses = claimController.getExpenses();
		claimController.addListenerToClaim(this);
	}
	
	/**
	 * Finds the necessary views needed in this activity
	 */
	private void findViewsByIds() {
		expenseListView = (ListView) findViewById(R.id.claimantExpenseListView);
		claimNameTextView = (TextView) findViewById(R.id.claimNameTextView);
		claimStartDateTextView = (TextView) findViewById(R.id.claimStartDateTextView);
		claimEndDateTextView = (TextView) findViewById(R.id.claimEndDateTextView);
	}
	
	/**
	 * Set the default text of the fields
	 */
	private void setFieldValues() {
		claimNameTextView.setText(claimController.getClaimName());
		claimStartDateTextView.setText(claimController.getStartDateFormatted());
		claimEndDateTextView.setText(claimController.getEndDateFormatted());
	}
	
	/**
	 * Sets up the listeners
	 */
	private void setListeners() {
		/**
		 * When an item in the list is clicked it will take the claimant to a place where they can edit
		 * their expense (if it is editable) or where they can view their expense otherwise
		 */
		expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Expense expense = claimController.getExpenses().get(position);
				Intent intent;
				// If claim is editable then send them to editable view
				if (claimController.isEditable()) {
					intent = new Intent(context, ExpenseEditActivity.class);
					// Otherwise send them to non-editable view
				} else {
					intent = new Intent(context, ExpenseViewActivity.class);
				}
				// Pass in the expense that was clicked
				intent.putExtra("expenseID", expense.getExpenseId());
				startActivity(intent);
			}
			
		});
		
		/**
		 * When an item in the list is long clicked it will bring up a dialog to delete the expense
		 * if the claim is currently editable or it will toast them if it is not
		 */
		expenseListView.setLongClickable(true);
		expenseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				// Shows the dialog if it is editable
				if (claimController.isEditable()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ClaimantExpenseListActivity.this);
					builder.setMessage("Do you want to delete this Expense?");
					builder
					.setPositiveButton("No", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							// Closes the dialog box (Does nothing)

						}
					})
					.setNegativeButton("Yes", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id) {
							// Deletes the expense of the current
							Expense expense = claimController.getExpenses().get(position);
							claimController.removeExpense(expense);

						}
					}).create().show();
					// Toasts the user if it is not editable
				} else {
					Toast.makeText(context, "Cannot currently delete expenses", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		}
	
	/**
	 * Sets up the adapter for the list view and binds the list to it
	 */
	private void setUpAdapter() {
		adapter = new ClaimantExpenseListAdapter(this, R.layout.claimant_expense_list_rows, expenses);
		expenseListView.setAdapter(adapter);
	}
	
	
	/**
	 * Submits the claim (or not if not completed) and toasts the results to the user
	 */
	private void submitClaim() {
		String result = claimController.checkBeforeSubmittingClaim();
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Called when the activity is destroyed, used to remove the observer
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		claimController.removeListenerFromClaim(this);
	}

	/**
	 * Observer method that is called when something in the model (claim) changes
	 */
	@Override
	public void update() {
		adapter.notifyDataSetChanged();
		
	}
		
}
