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

import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;

import junit.framework.TestCase;

/**
 * Tests the functionality of StringTupleTest.java
 */

public class StringTupleTest extends TestCase {
	
	public void testCreateTuple() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		StringTuple tuple = new StringTuple(string1, string2);
		
		assertTrue("destination not initialized correctly", tuple.getDestination().equals(string1));
		assertTrue("reason not initialized correctly", tuple.getReason().equals(string2));
	}
	
	public void testSetDestination() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		StringTuple tuple = new StringTuple(string1, string2);
		
		String newDest = "newDestination";
		tuple.setDestination(newDest);
		assertTrue("Destination not set", tuple.getDestination().equals(newDest));
	}
	
	public void testSetReason() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		StringTuple tuple = new StringTuple(string1, string2);
		
		String newReason = "newReason";
		tuple.setReason(newReason);
		// WHY IS THIS FAILING? You are comparing a destination and a reason
		assertEquals("Reason not set", newReason, tuple.getReason());
	}
	
}


