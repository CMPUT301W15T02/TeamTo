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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Adapters.ApproverClaimListAdapter;
import com.CMPUT301W15T02.teamtoapp.Controllers.ApproverController;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Utilities.ClaimComparatorNewestFirst;

/**
 * 
 * Activity that allows a user to look at all the different submitted claims
 * Not finished
 * 
 * @authors Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 *
 */

public class ApproverClaimsListActivity extends Activity {

	final Context context = this;
	private ListView listView;
	private ApproverClaimListAdapter adapter;
	ProgressDialog dialog;
	Handler handler;
	ApproverController approverController;
	
	/**
	 * Set up all required ID's, model objects, and listeners in onCreate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claims_list);
		findViewsByIds();
		
		// Progress Dialog displayed for user
		dialog = new ProgressDialog(ApproverClaimsListActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading submitted claims. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        
        // Initialize handler
        handler = new Handler(Looper.getMainLooper()) {
	        @Override
	        public void handleMessage(Message msg) {
	        	// Set up approver claim list adapter with submitted claims from the approverController.
	        	approverController = new ApproverController();
	        	adapter = new ApproverClaimListAdapter(context, R.layout.approver_claims_list_rows, approverController.getClaims());
	        	listView.setAdapter(adapter);
	        	adapter.sort(new ClaimComparatorNewestFirst());
	        	adapter.notifyDataSetChanged();
	        	
	        	// Dismiss dialog once adapter is up-to-date
	        	dialog.dismiss();
	        }
		};
		getModelObjects();
		setListeners();
		
		
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
		MainManager.getSubmittedClaims(handler);
	}
	
	
	/**
	 * Find the different views
	 */
	private void findViewsByIds() {
		listView = (ListView) findViewById(R.id.approverClaimListView);
	}
	
	
	/**
	 * Set up Listeners for clicks/long-clicks on the listView
	 * 
	 * onItemClick: Switch to ApproverExpenseListActivity to 
	 * 				see list of expense of selected claim
	 * onItemLongClick: Display alertDialog to allow approver the 
	 * 					option to add a comment and approve/return selected claim 
	 */
	private void setListeners() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Grab claim position
				Claim claim = approverController.getClaim(position);
				
				// Set up intent to next activity and attach claimID of selected claim
				Intent intent = new Intent(ApproverClaimsListActivity.this, ApproverExpenseListActivity.class);
				intent.putExtra("claimID", claim.getClaimId());
				startActivity(intent);
			}
		});
		
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				LayoutInflater inflater = LayoutInflater.from(getBaseContext());
				View ApproveReturnDialogView = inflater.inflate(R.layout.approve_return_claim_dialog, null);

				// Initialize EditText for approverComment
				final EditText approverComment = (EditText) ApproveReturnDialogView.findViewById(R.id.approverCommentEditText);

				// Set up alertDialog
				AlertDialog.Builder builder = new AlertDialog.Builder(ApproverClaimsListActivity.this);
				builder.setView(ApproveReturnDialogView);
				builder.setPositiveButton("Approve", new DialogInterface.OnClickListener () {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						String comment = approverComment.getText().toString();
						
						// Ensure Approver has entered a comment before approving
						if (comment.length() != 0) {
							Claim claim = approverController.getClaim(position);
							approverController.approveClaim(claim, comment);
							adapter.notifyDataSetChanged();
						} else {
							Toast.makeText(context, "Must add comment", Toast.LENGTH_LONG).show();
						}
					}
				})
				.setNeutralButton("Return", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						String comment = approverComment.getText().toString();
						
						// Ensure Approver has entered a comment before returning
						if (comment.length() != 0 ) {
							Claim claim = approverController.getClaim(position);
							approverController.returnClaim(claim, comment);
							adapter.notifyDataSetChanged();
						} else {
							Toast.makeText(context, "Must add comment", Toast.LENGTH_LONG).show();
						}
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// Do nothing
					}
				}).create().show();
				return true;
			}
		});
	}
	
	
	/**
	 * Switch back to claimant mode
	 * @param menu - menuItem that allows switching 
	 * 				  back to ClaimantClaimsListActivity
	 */
	public void switchToClaimantOption(MenuItem menu) {
		super.onBackPressed();
	}
	
	
}
