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
import android.widget.ListView;
import android.widget.Toast;

public class ClaimantClaimsListActivity extends Activity {
	// For now, pretend new session controller when user reached this activity for the first time.
	private SessionController s_control;
	final Context context = this;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claims_list);
		
		// For now, pretend new session controller when user reached this activity for the first time every time. (no saving yet)
		// Don't have a listener yet for the adapter, so cannot view claimant's claim list yet.
		s_control = new SessionController();
		final ArrayList<Claim> clist = new ArrayList<Claim>(s_control.claims.getClaims());
		final ClaimantClaimLVAdapter adapter = new ClaimantClaimLVAdapter(context, R.layout.claimant_claims_list_rows, clist);
		lv = (ListView) findViewById(R.id.claimantClaimListView);
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
		// Claimant clicks "+" from action bar to add new claim 
		// Automatically save new blank claim in claim list view.
		// In progress ...
		
		// Shows username is saved!
		Toast.makeText(getBaseContext(), s_control.user.getName(), Toast.LENGTH_SHORT).show();
		Claim new_claim = new Claim();
		s_control.claims.addClaim(new_claim);
		// Shows claim name == "New Claim"!
		Toast.makeText(getBaseContext(), s_control.claims.getClaim(new_claim).getClaimName(), Toast.LENGTH_SHORT).show();

	}

	public void filterByDate(MenuItem menu) {
		
	}
	
	public void onClaimClick() {
		// Claimant clicks on existing claim to go to claim overview.
		// UI: claimant_claim_overview.xml (not created yet)
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
