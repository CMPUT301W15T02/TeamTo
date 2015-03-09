package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.Claim.Status;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ClaimantExpenseListActivity extends Activity {

	private Context context = this;
	private ListView expenseListView;
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
	}
	
	private void setListeners() {
		expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Expense expense = claimController.getExpenses().get(position);
				Intent intent = new Intent(context, ExpenseEditActivity.class);
				intent.putExtra("expenseID", expense.getExpenseId());
				startActivity(intent);
			}
			
		});
		//Allows the deleting of expenses
		//Should be done, will do testing later
		expenseListView.setLongClickable(true);
		expenseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
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
				});
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
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
		// TODO Auto-generated method stub
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
	
	private void submitClaim() {
		// TODO: Check claim and expenses depending on current status
		// TODO: Need to do test for this method. If anyone has a better way to organize this code, go for it. :)
		
		if (checkClaimInfoComplete() == false) {
			Toast.makeText(context, "Claim information incomplete.", Toast.LENGTH_SHORT).show();
		} else if (claimController.getCurrentClaim().getExpenses().size() == 0) {
			Toast.makeText(context, "Claim has no expenses.", Toast.LENGTH_SHORT).show();
		}
		
		// If claim info complete and expenses are present, check for expense incompleteness depending on status
		com.CMPUT301W15T02.teamtoapp.Claim.Status currentStatus = claimController.getCurrentClaim().getStatus();
		
		if (currentStatus == com.CMPUT301W15T02.teamtoapp.Claim.Status.IN_PROGRESS) {
			// Gives number of incomplete expenses.
			int numExpensesIncomplete = claimController.getCurrentClaim().checkExpensesComplete();
			
			if (numExpensesIncomplete > 0) {
				Toast.makeText(context, "Expenses are incomplete.", Toast.LENGTH_SHORT).show();
			}
			
		} else if (currentStatus == com.CMPUT301W15T02.teamtoapp.Claim.Status.APPROVED) {
			Toast.makeText(context, "Claim was already approved.", Toast.LENGTH_SHORT).show();
			
		} else if  (currentStatus == com.CMPUT301W15T02.teamtoapp.Claim.Status.RETURNED) {
			// do something...
			
		} else {
			claimController.getCurrentClaim().setStatus(Status.SUBMITTED);
		}
	}
	
	private boolean checkClaimInfoComplete() {
		// TODO: Not sure how to check start date and end date.
		// TODO: Need to do tests for this method.
		Claim checkClaim = claimController.getCurrentClaim();
		if (checkClaim.getClaimName().isEmpty()) {
			return false;
		}
		
		if (checkClaim.getDestinations().size() == 0) {
			return false;
		}
		
		return true;
	}
	
	
	
}
