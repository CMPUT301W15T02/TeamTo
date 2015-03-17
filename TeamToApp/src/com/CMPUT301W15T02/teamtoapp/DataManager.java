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

import android.content.Context;
import android.net.ConnectivityManager;

import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Session;
import com.CMPUT301W15T02.teamtoapp.Model.User;

public class DataManager {

	
	private static Context applicationContext;
	
	
	public void initializeContext(Context context) {
		applicationContext = context.getApplicationContext();
	}
	
	
	
	public void setUser(String name) {
		Session.getInstance().setCurrentUser(new User(name));
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
}
