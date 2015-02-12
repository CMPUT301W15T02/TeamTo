/* Class for managing list of users
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

import java.util.ArrayList;

/*
 * Singleton to store a list of users
 * 
 * Using singleton because it will exist as along as the app stays in memory
 * and gives access to all of the claims and associated expenses throughout the app
 */
public class UserController {
	
	// Will need context to implement saving/loading from file but can probably just pass that to each method
	private static UserController instance = null;
	private ArrayList<User> users;
	
	// Private constructor called only if an instance has not been created
	private UserController(){
		users = new ArrayList<User>();
	};
	
	// Returns the instance of ClaimManager if it exists, otherwise calls the private constructor and returns that instance
	public static UserController getInstance() {
		if (instance==null) {
			instance = new UserController();
		}
		return instance;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}

}
