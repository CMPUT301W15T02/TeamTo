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

import java.util.ArrayList;

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
import android.widget.Toast;

public class ClaimantClaimsListActivity extends Activity {
	
	private SessionController sessionController;
	final Context context = this;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claims_list);
		
		sessionController = new SessionController();
		final ArrayList<Claim> claimsList = new ArrayList<Claim>(sessionController.getClaims());
		final ClaimantClaimLVAdapter adapter = new ClaimantClaimLVAdapter(context, R.layout.claimant_claims_list_rows, claimsList);
		lv = (ListView) findViewById(R.id.claimantClaimListView);
		
		// Item in the list is clicked, user taken to expenseListActivity
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Claim claim = sessionController.getClaims().get(position);
				sessionController.setClaim(claim);
				Intent intent = new Intent(getBaseContext(), ClaimantExpenseListActivity.class);
				startActivity(intent);
			}
		});
		
		
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_claims_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addClaimOption (MenuItem menu) {
		Claim new_claim = new Claim();
		sessionController.addClaim(new_claim);
		Intent intent = new Intent(getBaseContext(), ClaimEditActivity.class);
		startActivity(intent);
	}

	public void filterByDate(MenuItem menu) {
		
	}
	
	public void onClaimLongClick() {
		// Claimant clicks on existing claim to edit/delete it.
		// UI: edit_delete_claimant_claim.xml
		// Pop up dialog to see if user wants to edit/delete a claim.
		
		// Need to grab selected claim first before dialog pop up.
		AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
		builder.setMessage("Edit or Delete Claim?");
				builder.setPositiveButton("Edit", new DialogInterface.OnClickListener () {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Go to ClaimEditActivity.java
				Intent intent = new Intent(ClaimantClaimsListActivity.this, ClaimEditActivity.class);
				startActivity(intent);
			}
		})
		
		.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// Delete selected claim
			}
		});
		
		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}
	
	public void switchToApproverOption(MenuItem menu) {
		// Switch to ApproverClaimListActivity.class.
		Intent intent = new Intent(ClaimantClaimsListActivity.this, ApproverClaimsListActivity.class);
		startActivity(intent);
	}
}
