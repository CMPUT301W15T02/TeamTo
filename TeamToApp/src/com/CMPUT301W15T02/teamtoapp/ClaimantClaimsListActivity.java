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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ClaimantClaimsListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claims_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.claimant_claim_list_menu, menu);
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
		// Claimant clicks "+" from action bar to add new claim (Need to add to UML).
		// UI: create_claimant_claim.xml
		Intent intent = new Intent(ClaimantClaimsListActivity.this, ClaimantAddClaimActivity.class);
		startActivity(intent);
	}
	
	public void filterByClaimOption(MenuItem menu) {
		
	}
	
	public void onClaimClick() {
		// Claimant clicks on existing claim to go to claim overview.
		// UI: claimant_claim_overview.xml
	}
	
	public void onClaimLongClick() {
		// Claimant clicks on existing claim to edit/delete it.
		// UI: edit_delete_claimant_claim.xml
	}
	
	public void switchToApproverOption(MenuItem menu) {
		// Switch to ApproverClaimListActivity.class.
		Intent intent = new Intent(ClaimantClaimsListActivity.this, ApproverClaimsListActivity.class);
		startActivity(intent);
	}
}
