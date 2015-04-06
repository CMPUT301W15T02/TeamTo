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
 * Destination object holds two strings to aid in the functionality of Claim.java.
 * It also has latitude and longitude to record location of destination. The latitude and
 * longitude values are obtained from DestinationGeoLocationActivity.java.
 * 
 * @author Kyle Carlstrom, Raman Dhatt
 */

public class Destination {
	
	public String destination;
	public String reason;
	public double latitude;
	public double longitude;
	
	/**
	 * Destination constructor
	 * @param dest - destination string
	 * @param reason - reason string
	 * @param latitude - double latitude value
	 * @param longitude - double longitude value
	 */
	public Destination(String dest, String reason, double latitude, double longitude) {
		this.destination = dest;
		this.reason = reason;
		this.latitude = latitude;
		this.longitude = longitude;
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
	
	/**
	 * ADd a geolocation based on longitude and latitude
	 * @param latitude
	 * @param longitude
	 */
	public void addGeolocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
}
