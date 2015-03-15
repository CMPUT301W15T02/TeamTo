/* Session class
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

/**
 * 
 * Singleton that contains all information about the current session 
 * including the current users and the current list of claims
 *
 */

public class Session {
	private User currentUser;
	private ClaimList currentClaims;
	
	private static Session instance = null;

	private Session(){
		currentUser = new User(null);
		currentClaims = new ClaimList();
	};

	/**
	 * Contains the current instance of the singleton if it exists or returns a new instance
	 * @return
	 */
	public static Session getInstance() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public ClaimList getCurrentClaims() {
		return currentClaims;
	}

	public void setCurrentClaims(ClaimList currentClaims) {
		this.currentClaims = currentClaims;
	}
	
	// ONLY FOR TESTING
	public static void tearDownForTesting() {
		instance = null;
	}
	
}
