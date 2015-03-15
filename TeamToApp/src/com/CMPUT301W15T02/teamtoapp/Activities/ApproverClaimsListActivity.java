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
import com.CMPUT301W15T02.teamtoapp.Adapters.ApproverClaimListAdapter;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimListController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorOldestFirst;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * 
 * Activity that allows a user to look at all the different submitted claims
 * Not finished
 *
 */

public class ApproverClaimsListActivity extends Activity {

	final Context context = this;
	private ListView listView;
	private ClaimListController claimListController;
	private ApproverClaimListAdapter adapter;
	private ArrayList<Claim> submittedClaims;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claims_list);
		
		getModelObjects();
		findViewsByIds();
		setListeners();
		setUpAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.approver_claims_list_menu, menu);
		return true;
	}


	/**
	 * Get references to the controller and model objects
	 */
	private void getModelObjects() {
		claimListController = new ClaimListController();
		submittedClaims = claimListController.getSubmittedClaims();
	}
	
	/**
	 * Find the different views
	 */
	private void findViewsByIds() {
		listView = (ListView) findViewById(R.id.approverClaimListView);
	}
	
	private void setListeners() {
		
	}
	
	/**
	 * Set up the adapter and bind it to the list view
	 */
	private void setUpAdapter() {
		adapter = new ApproverClaimListAdapter(context, R.layout.approver_claims_list_rows, submittedClaims);
		adapter.sort(new ClaimComparatorOldestFirst());
		listView.setAdapter(adapter);
		
	}
	/**
	 * Switch back to claimant mode
	 * @param menu
	 */
	public void switchToClaimantOption(MenuItem menu) {
		// Need to do other stuff
		super.onBackPressed();
	}
	
	
}
