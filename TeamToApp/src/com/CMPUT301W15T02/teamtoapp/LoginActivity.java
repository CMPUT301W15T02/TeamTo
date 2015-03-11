// LoginActivity

//Parses entered username to get specific claims and approvals

package com.CMPUT301W15T02.teamtoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main_activity);

		SharedPreferences settings = getSharedPreferences("data", MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		//Set "hasLoggedIn" to true
		editor.putBoolean("hasLoggedIn", true);

		// Commit the edits!
		editor.commit();
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
		
//TODO: Fix if/else block for username checking
		
		//if username blank
		if (usernameString.matches("")) {
			Toast.makeText(this, "Please enter username!", Toast.LENGTH_SHORT).show();

		}
		//if username exists
		else if (usernameString == ""){
			//placeholder
		}
		//else create new username
		//Requirements?  Length, content etc?
		else {
			
			//need some way to add username to session
			SessionController s_control = new SessionController();
			startActivity(intent);
		}
	}

}
