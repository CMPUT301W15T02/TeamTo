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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
import com.CMPUT301W15T02.teamtoapp.LocalDataManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Adapters.ApproverClaimListAdapter;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorNewestFirst;

/**
 * 
 * Activity that allows a user to look at all the different submitted claims
 * Not finished
 *
 */

public class ApproverClaimsListActivity extends Activity {

	final Context context = this;
	private ListView listView;
	private ApproverClaimListAdapter adapter;
	private ArrayList<Claim> submittedClaims;
	ProgressDialog dialog;
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claims_list);
		findViewsByIds();
		dialog = new ProgressDialog(ApproverClaimsListActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
		getModelObjects();
		setListeners();
		
		handler = new Handler(Looper.getMainLooper()) {
	        @Override
	        public void handleMessage(Message msg) {
	        	ClaimList.getInstance().setClaims(submittedClaims);
	        	adapter = new ApproverClaimListAdapter(context, R.layout.approver_claims_list_rows, ClaimList.getInstance().getClaims());
	        	listView.setAdapter(adapter);
	        	adapter.sort(new ClaimComparatorNewestFirst());
	        	adapter.notifyDataSetChanged();
	        	dialog.dismiss();
	        }
		};
		
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
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				submittedClaims = ElasticSearchManager.getSubmittedClaims();
				handler.sendEmptyMessage(0);
				
				
			}
		}).start();
	}
	
	/**
	 * Find the different views
	 */
	private void findViewsByIds() {
		listView = (ListView) findViewById(R.id.approverClaimListView);
	}
	
	private void setListeners() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Claim claim = ClaimList.getInstance().getClaims().get(position);
				Intent intent = new Intent(ApproverClaimsListActivity.this, ApproverExpenseListActivity.class);
				intent.putExtra("claimID", claim.getClaimId());
				startActivity(intent);
			}
		});
	}
	
	
	/**
	 * Switch back to claimant mode
	 * @param menu
	 */
	public void switchToClaimantOption(MenuItem menu) {
		LocalDataManager.loadClaims();
		super.onBackPressed();
	}
	
	
}
