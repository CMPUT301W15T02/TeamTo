/* ClaimantClaimListActivity displays list of claims
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


package com.CMPUT301W15T02.teamtoapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ClaimantClaimsListActivity extends Activity {
	
	private ClaimListController claimListController;
	final Context context = this;
	private ListView listView;
	private ClaimantClaimListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claims_list);
		
		claimListController = new ClaimListController();
		
		findViewsByIds();
		setListeners();
		setUpAdapter();
	}
	
	private void findViewsByIds() {
		listView = (ListView) findViewById(R.id.claimantClaimListView);
	}
	
	private void setListeners() {
		// Item in the list is clicked, user taken to expenseListActivity
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Claim claim = claimListController.getClaim(position);
				Intent intent = new Intent(ClaimantClaimsListActivity.this, ClaimantExpenseListActivity.class);
				intent.putExtra("claimID", claim.getClaimId());
				startActivity(intent);
			}
		});
		// Brings up Edit or Delete dialog on long click		
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ClaimantClaimsListActivity.this);
				builder.setMessage("Edit or Delete Claim?");
				builder
				.setPositiveButton("Edit", new DialogInterface.OnClickListener () {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Claim claim = claimListController.getClaim(position);
						Intent intent = new Intent(ClaimantClaimsListActivity.this, ClaimEditActivity.class);
						intent.putExtra("claimID", claim.getClaimId());
						startActivity(intent);
					}
				})
				.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// Finished - can now delete claim
						// TODO: Need to do a test for deleting claim in TeamToAppTest
						Claim claim = claimListController.getClaim(position);
						claimListController.removeClaim(claim);
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
		adapter = new ClaimantClaimListAdapter(context, R.layout.claimant_claims_list_rows, claimListController.getClaims());
		listView.setAdapter(adapter);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_claims_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.tagsManagerButton) {
			Intent intent = new Intent(getBaseContext(), TagManagerActivity.class);
			startActivity(intent);
		} else if (id == R.id.addClaimOp) {
			Claim newClaim = new Claim();
			claimListController.addClaim(newClaim);
			Intent intent = new Intent(getBaseContext(), ClaimEditActivity.class);
			intent.putExtra("claimID", newClaim.getClaimId());
			startActivity(intent);	
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
		
	}

	public void filterByDate(MenuItem menu) {
		
	}
	
	
	public void switchToApproverOption(MenuItem menu) {
		// Switch to ApproverClaimListActivity.class.
		// TODO: Need way of checking if there is access to the internet
		Intent intent = new Intent(ClaimantClaimsListActivity.this, ApproverClaimsListActivity.class);
		startActivity(intent);
	}
}
