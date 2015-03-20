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

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

public class LocalDataManager {

	
	private static final int _ERASE_FILE = 0;
	private static Context applicationContext;
	private static final String CLAIMFILE = "claims.sav";
	private static final String USERFILE = "user.sav";
	
	
	public LocalDataManager() {
		
	}
	
	public static void initializeContext(Context context) {
		applicationContext = context.getApplicationContext();
	}
	
	
	
	public void setClaims(ArrayList<Claim> claims) {
		ClaimList.getInstance().setClaims(claims);
	}
	
	
	public static void loadUser() {
		Gson gson = new Gson();
		User user;
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
		User.getInstance().setUser(user);
	}
	
	
	
	public void saveUser(User user){
		Gson gson = new Gson();
		try {
			FileOutputStream fos = applicationContext.openFileOutput(USERFILE,_ERASE_FILE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(user, osw);
			osw.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void loadClaims() {
		Gson gson = new Gson();
		ArrayList<Claim> claims;
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
		ClaimList.getInstance().setClaims(claims);
	}
	
	public static void saveClaims() {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = applicationContext.openFileOutput(CLAIMFILE,_ERASE_FILE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(ClaimList.getInstance().getClaims(), osw);
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void logClaim() {
		Gson gson = new Gson();
		Claim claim = new Claim();
		claim.setUserName("Kyle");
		String json = gson.toJson(claim);
		Log.i("GSON STUFF", json);
	}
}
