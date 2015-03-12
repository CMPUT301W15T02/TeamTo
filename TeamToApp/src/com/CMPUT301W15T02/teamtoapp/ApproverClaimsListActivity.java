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

package com.CMPUT301W15T02.teamtoapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class ApproverClaimsListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claims_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_claims_list_menu, menu);
		return true;
	}

	public void filterByDate(MenuItem menu) {
		
	}
	
	public void onClaimClick() {
		// TODO: Go to ApproverExpenseListActivity.java
	}
	
	public void switchToClaimantOption(MenuItem menu) {
		// Not really sure if this is the correct way to do it.
		// Changes by approver should be saved before going back.
		super.onBackPressed();
	}
}
