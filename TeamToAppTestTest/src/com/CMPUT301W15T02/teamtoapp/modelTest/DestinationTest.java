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

package com.CMPUT301W15T02.teamtoapp.modelTest;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Model.Destination;


/**
 * Tests the functionality of StringTupleTest.java
 */

public class DestinationTest extends TestCase {

	// Test destination is initialized
	public void testCreateDestination() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		Destination tuple = new Destination(string1, string2, 51.0, -113.0);
		
		assertTrue("destination not initialized correctly", tuple.getDestination().equals(string1));
		assertTrue("reason not initialized correctly", tuple.getReason().equals(string2));
	}
	
	// Test destination object's ability to update
	public void testSetDestination() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		Destination tuple = new Destination(string1, string2, 51.0, -113.0);
		
		String newDest = "newDestination";
		tuple.setDestination(newDest);
		assertTrue("Destination not set", tuple.getDestination().equals(newDest));
	}
	
	// Test ability to set new reason
	public void testSetReason() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		Destination tuple = new Destination(string1, string2, 51.0, -113.0);
		
		String newReason = "newReason";
		tuple.setReason(newReason);

		assertTrue("Reason not set", tuple.getReason().equals(newReason));

		assertEquals("Reason not set", newReason, tuple.getReason());

	}
	
	// Test ability to set latitude and longitude as a part of the destination geolocation
	public void testSaveLatLng() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		
		// Must save as double
		double latitude = 51;
		double longitude = -113;
		Destination tuple = new Destination(string1, string2, latitude, longitude);
		
		assertEquals("Latitude not saved", latitude, tuple.latitude);

		assertEquals("Longitude not saved", longitude, tuple.longitude);
	}

}


