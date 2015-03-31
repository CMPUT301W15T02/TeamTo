/* 
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
 * Simple tuple that holds two strings to aid in the functionality of Claim.java
 *
 */

public class Destination {
	
	public String destination;
	public String reason;
	public double latitude;
	public double longitude;
	
	
	public Destination(String a, String b) {
		this.destination = a;
		this.reason = b;
	}
	
	public String getDestination() {
		return this.destination;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	public void setDestination(String newDestination) {
		this.destination = newDestination;
	}
	
	public void setReason(String newReason) {
		this.reason = newReason;
	}
	
}
