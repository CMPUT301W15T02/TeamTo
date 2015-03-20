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


// LoginActivity

//Parses entered username to get specific claims and approvals

package com.CMPUT301W15T02.teamtoapp.Activities;


import com.CMPUT301W15T02.teamtoapp.LocalDataManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Controllers.UserController;
import com.CMPUT301W15T02.teamtoapp.Model.User;

import android.os.Bundle;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main_activity);
		
		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
		boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
		String usernameString = settings.getString("username", null);
		MainManager.initializeContext(getApplicationContext());

		if (hasLoggedIn)  //Go directly to main activity
		{
			MainManager.loadUser();
			MainManager.loadClaims();
		    Intent intent = new Intent();
			intent.setClass(LoginActivity.this, ClaimantClaimsListActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void onLoginButtonClicked(View view) {
		Intent intent = new Intent(LoginActivity.this, ClaimantClaimsListActivity.class);
		EditText name = (EditText) findViewById(R.id.username);
		String usernameString = name.getText().toString();
		
//TODO: Complete username session link
		
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
			
			MainManager.loadUser();
			MainManager.loadClaims();
			User.getInstance().setName(usernameString);
			Toast.makeText(this, "Username:"+User.getInstance().getName(), Toast.LENGTH_SHORT).show();
			intent.putExtra("claimantName", usernameString);
			startActivity(intent);
			LoginActivity.this.finish();
		}
	}

}
