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

/**
*Parses entered username to get specific claims and approvals
*/

package com.CMPUT301W15T02.teamtoapp.Activities;


import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
import com.CMPUT301W15T02.teamtoapp.LocalDataManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Model.Cache;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.User;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity that user will see upon first starting app,
 * prompts for login and saves username for next session
 */
public class LoginActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
		boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
		final String usernameString = settings.getString("username", null);
		MainManager.initializeContext(getApplicationContext());
		
		
		if (hasLoggedIn)  //Go directly to main activity
		{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Cache.getInstance().loadRemovals();
					Cache.getInstance().loadUpdates();
					for (Claim claim: Cache.getInstance().getUpdates()) {
						ElasticSearchManager.updateClaim(claim);
					}
					for (Claim claim: Cache.getInstance().getRemovals()) {
						ElasticSearchManager.deleteClaim(claim.getClaimId());
					}
					Cache.getInstance().clearCache();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					MainManager.loadUser(usernameString);
					MainManager.loadClaims(usernameString);
					handler.sendEmptyMessage(0);
					
				}
			}).start();
			
		} else {
			setContentView(R.layout.login_main_activity);
		}
		
		
		handler = new Handler(Looper.getMainLooper()) {

			@Override
			public void handleMessage(Message msg) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, ClaimantClaimsListActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
			
		};

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	// Comment
	public void onLoginButtonClicked(View view) {
		Intent intent = new Intent(LoginActivity.this, ClaimantClaimsListActivity.class);
		EditText name = (EditText) findViewById(R.id.username);
		final String usernameString = name.getText().toString();
		
		
		//if username blank
		if (usernameString.matches("")) {
			Toast.makeText(this, "Please enter username!", Toast.LENGTH_SHORT).show();

		}
		else {
			//User has successfully logged in, save this information
			SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("hasLoggedIn", true);
			editor.putString("username", usernameString);
			editor.commit();
			LocalDataManager.saveUser(User.getInstance());
			LocalDataManager.saveClaims();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					MainManager.loadUser(usernameString);
					MainManager.loadClaims(usernameString);
					handler.sendEmptyMessage(0);
					
				}
			}).start();
		}
	}
	
	public void onGoogleMapBtnClicked(View view) {
		Intent intent = new Intent(LoginActivity.this, GoogleMapActivity.class);
		startActivity(intent);
	}

}
