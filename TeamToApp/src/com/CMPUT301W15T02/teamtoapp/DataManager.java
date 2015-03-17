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

/**
 * Controls link between local storage and elastic search server
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.net.ConnectivityManager;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Session;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataManager {

	
	private static Context applicationContext;
	private static final String CLAIMFILE = "claims.sav";
	private static final String USERFILE = "user.sav";
	
	
	public DataManager() {
		
	}
	
	public void initializeContext(Context context) {
		applicationContext = context.getApplicationContext();
	}
	
	
	public void setUser(User user) {
		Session.getInstance().setCurrentUser(user);
	}
	
	public void setClaims(ArrayList<Claim> claims) {
		ClaimList claimList = new ClaimList();
		claimList.setClaims(claims);
		Session.getInstance().setCurrentClaims(claimList);
	}
	
	//Source: http://stackoverflow.com/a/9570292 2015-03-10
	/**
	 * Checks if there is network availability
	 * @param 	context	Current state of the application
	 * @return	boolean false if no connections, true otherwise
	 */
	public static boolean isNetworkAvailable(Context context) {
		return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}
	
	
	public void loadUser(String usernameString){
		Gson gson = new Gson();
		User user = new User(usernameString);
		try {
			FileInputStream fis = applicationContext.openFileInput(USERFILE);
			Type dataType = new TypeToken<User>() {}.getType();
			InputStreamReader isr = new InputStreamReader(fis);
			user = gson.fromJson(isr, dataType);
			fis.close();
			
		} catch (FileNotFoundException e) {
			user = null;
		} catch (IOException e) {
			user = null;
		}
		if (user == null) {
			user = new User(usernameString);
		}
		
		setUser(user);
	}
	
	
	
	public void saveUser(User user){
		Gson gson = new Gson();
		try {
			FileOutputStream fos = applicationContext.openFileOutput(USERFILE,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(user, osw);
			osw.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void loadClaims() {
		Gson gson = new Gson();
		ArrayList<Claim> claims = new ArrayList<Claim>();
		try {
			FileInputStream fis = applicationContext.openFileInput(CLAIMFILE);
			Type dataType = new TypeToken<ArrayList<Claim>>() {}.getType();
			InputStreamReader isr = new InputStreamReader(fis);
			claims = gson.fromJson(isr, dataType);
			fis.close();
			
		} catch (FileNotFoundException e) {
			claims = null;
		} catch (IOException e) {
			claims = null;
		}
		if (claims == null) {
			claims = new ArrayList<Claim>();
		}
		setClaims(claims);
	}
	
	public void saveClaims(ArrayList<Claim> claims) {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = applicationContext.openFileOutput(CLAIMFILE,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claims, osw);
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
