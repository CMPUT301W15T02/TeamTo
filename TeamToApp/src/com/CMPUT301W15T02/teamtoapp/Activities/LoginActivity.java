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


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Model.User;

/**
 * Activity that user will see upon first starting app,
 * prompts for login and saves username for next session
 * 
 * @authors Michael Stensby, Kyle Carlstrom, Raman Dhatt
 */

public class LoginActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	private Handler handler;
	ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Obtain SharedPreferences object, and set default values for hasLoggedIn
		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
		boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
		
		// Obtain username
		String usernameString = settings.getString("username", null);
		MainManager.initializeContext(getApplicationContext());
		
		// Create Handler 
		handler = new Handler(Looper.getMainLooper()) {

			@Override
			public void handleMessage(Message msg) {
				
				// Create intent and start activity
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, ClaimantClaimsListActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
			
		};
		
		if (hasLoggedIn)  //Go directly to main activity
		{
			User.getInstance().setName(usernameString);
	        MainManager.loadUserAndClaims(usernameString, handler);
			
		} else {
			setContentView(R.layout.login_main_activity);
		}
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
	/**
	 * This method makes sure user has entered a username,
	 * and that the username is saved in the MainManager.
	 * 
	 * If username already exists, MainManager will load user 
	 * and claim information.
	 * 
	 * @param view 
	 */
	public void onLoginButtonClicked(View view) {
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
			
			// Load existing user and claim information
			User.getInstance().setName(usernameString);
			MainManager.loadUserAndClaims(usernameString, handler);
		}
	}

}
