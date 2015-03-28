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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Adapters.ApproverExpenseListAdapter;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;


/**
 * 
 * Activity to allow the approver to look at the different expenses of a submitted claim
 *
 */

public class ApproverExpenseListActivity extends Activity {

	private String claimID;
	private ArrayList<Expense> expenses;
	private ListView expenseListView;
	private ApproverExpenseListAdapter adapter;
	private Context context = this;
	private ClaimController claimController;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_expense_list);
		
		findViewsByIds();
		getModelObjects();
		setFieldValues();
		setListeners();
		setUpAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.approver_expense_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.approve_claim) {
			return true;
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
	}
	
	/**
	 * Finds the necessary views needed in this activity
	 */
	private void findViewsByIds() {
		expenseListView = (ListView) findViewById(R.id.approverExpenseListView);

	}
	
	/**
	 * Set the default text of the fields
	 */
	private void setFieldValues() {
		
	}
	
	
	/**
	 * Sets up the listeners for the expense list view
	 */
	private void setListeners() {
		expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Expense expense = claimController.getExpenses().get(position);
				Intent intent = new Intent(context, ExpenseViewActivity.class);
				intent.putExtra("expenseID", expense.getExpenseId());
				startActivity(intent);
				
			}
		});
		
	}
	
	private void setUpAdapter() {
		adapter = new ApproverExpenseListAdapter(context, R.layout.approver_expense_list_rows, expenses);
    	adapter.notifyDataSetChanged();    		
    	expenseListView.setAdapter(adapter);
	}
	
	
	
}
