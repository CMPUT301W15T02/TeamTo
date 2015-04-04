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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Controls link between local storage and elastic search server.
 * LocalDataManager checks whether there is connectivity before manipulating data via server/local storage.
 * 
 * @authors Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 */

public class LocalDataManager {

	
	private static final int _ERASE_FILE = 0; // Erase a file if not empty
	private static Context applicationContext;
	private static final String CLAIMFILE = "claims.sav";
	private static final String USERFILE = "user.sav";
	
	/**
	 * Constructor
	 */
	public LocalDataManager() {
		
	}
	
	
	/**
	 * Initialize context - required for manipulating data
	 * 
	 * @param context - Context of application
	 */
	public static void initializeContext(Context context) {
		applicationContext = context.getApplicationContext();
	}
	
	

	
	
	/**
	 * Load user from local data storage from the USERFILE file
	 */
	@SuppressWarnings("static-access")
	public static void loadUser() {
		Gson gson = new Gson();
		User user;
		try {
			FileInputStream fis = applicationContext.openFileInput(USERFILE);
			Type dataType = new TypeToken<User>() {}.getType();
			
			// Read from USERFILE and close when done
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
	
	
	/**
	 * Save user in local data storage in the USERFILE file
	 */
	public static void saveUser(User user){
		Gson gson = new Gson();
		try {
			FileOutputStream fos = applicationContext.openFileOutput(USERFILE,_ERASE_FILE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			
			// Write from USERFILE, flush, and close when done
			gson.toJson(user, osw);
			osw.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Load list of claims from local data storage from the CLAIMFILE file
	 */
	public static void loadClaims() {
		Gson gson = new Gson();
		ArrayList<Claim> claims;
		try {
			FileInputStream fis = applicationContext.openFileInput(CLAIMFILE);
			Type dataType = new TypeToken<ArrayList<Claim>>() {}.getType();
			
			// Read from CLAIMFILE and close when done
			InputStreamReader isr = new InputStreamReader(fis);
			claims = gson.fromJson(isr, dataType);
			fis.close();
			
		} catch (FileNotFoundException e) {
			claims = null;
		} catch (IOException e) {
			claims = null;
		}
		
		// If no claims exist, create new empty list of claims
		if (claims == null) {
			claims = new ArrayList<Claim>();
		}
		
		// Set claim list for user
		ClaimList.getInstance().setClaims(claims);
	}
	
	
	/**
	 * Save list of claims in local data storage in the CLAIMFILE file
	 */
	public static void saveClaims() {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = applicationContext.openFileOutput(CLAIMFILE,_ERASE_FILE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			
			// Write from CLAIMFILE, flush, and close when done
			gson.toJson(ClaimList.getInstance().getClaims(), osw);
			osw.flush();
			osw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
