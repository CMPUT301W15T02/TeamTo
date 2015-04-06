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

package com.CMPUT301W15T02.teamtoapp.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *  Handles updating and removal of claims when an internet connection is established.
 *  
 *  @author Kyle Carlstrom
 */

public class Cache {

	private static Cache instance = null;
 	private static Context context;

 	private static String UPDATE_FILE_NAME = "update_claims_cache_file.txt";
 	private static String REMOVE_FILE_NAME = "remove_claims_cache_file.txt";
 	
 	private ArrayList<Claim> claimsToUpdate;
 	private ArrayList<Claim> claimsToRemove; 

 	/**
 	 * Private constructor for Cache() - singleton
 	 */
 	private Cache() {
		claimsToUpdate = new ArrayList<Claim>();
		claimsToRemove = new ArrayList<Claim>(); 
 	}
 	
 	/**
 	 * Initialize context
 	 * @param appcontext - context of application
 	 */
 	public static void initializeContext(Context appcontext) {
 		context = appcontext;
 	}
	
 	/** 
 	 * Checks if singleton is instantiated
 	 * @return instantiated instance
 	 */
 	public static Cache getInstance() {
		if (instance == null) {
			instance = new Cache();
		}
		return instance;
	}
 	
 	/** Adds claim to claimsToUpdate array only if claim is NOT already in claimsToUpdate array */
 	public void addUpdate(Claim claim) {
 		if (!claimsToUpdate.contains(claim)) {
 			claimsToUpdate.add(claim);
 			saveUpdates();
 		}
 	}
 	
 	/** 
 	 * Adds claim to claimsToRemove array only if claim is NOT already in claimsToRemove array & then
 	 * removes it from claimsToUpdate array.
 	 */
 	public void addRemoval(Claim claim) {
 		if (!claimsToRemove.contains(claim)) {
 			claimsToRemove.add(claim);
 			saveRemovals();
 		}
 		
 		if (claimsToUpdate.contains(claim)) {
 			claimsToUpdate.remove(claim);
 			saveUpdates();
 		}
 	}
 	
 	/** Clears both arrays */
 	public void clearCache() {
 		claimsToUpdate = new ArrayList<Claim>();
 		claimsToRemove = new ArrayList<Claim>();
 	}
 	
 	/** Saves claims to update */
 	public void saveUpdates() {
 		Gson gson = new Gson();
 		try {
 			FileOutputStream fos = context.openFileOutput(UPDATE_FILE_NAME,0);
 			OutputStreamWriter osw = new OutputStreamWriter(fos);
 			gson.toJson(claimsToUpdate, osw);
 			osw.flush();
 			osw.close();
 		} catch (FileNotFoundException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 	}

 	/** Saves claims to remove */
	public void saveRemovals() {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = context.openFileOutput(REMOVE_FILE_NAME,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claimsToRemove, osw);
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Loads updates from save; Initializes empty claimsToUpdate array if there are none. */
	public void loadUpdates() {
		Gson gson = new Gson();
		ArrayList<Claim> claims = new ArrayList<Claim>();
		try {
			FileInputStream fis = context.openFileInput(UPDATE_FILE_NAME);
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
		
		claimsToUpdate = claims;
	}
	
	/** Loads removals from save; Initializes empty claimsToRemove array if there are none. */
	public void loadRemovals() {
		Gson gson = new Gson();
		ArrayList<Claim> claims = new ArrayList<Claim>();
		
		try {
			FileInputStream fis = context.openFileInput(REMOVE_FILE_NAME);
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
		
		claimsToRemove = claims;
	}
	
	public ArrayList<Claim> getUpdates() {
		return claimsToUpdate;
	}
	
	public ArrayList<Claim> getRemovals() {
		return claimsToRemove;
	}

	public static void tearDownForTesting() {
		instance = null;
	}
 		
}
