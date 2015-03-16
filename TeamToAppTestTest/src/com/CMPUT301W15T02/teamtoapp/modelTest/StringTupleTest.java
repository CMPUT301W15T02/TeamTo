package com.CMPUT301W15T02.teamtoapp.modelTest;

import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;

import junit.framework.TestCase;

public class StringTupleTest extends TestCase {
	
	public void testCreateTuple() {
	
	String string1 = new String("destination");
	String string2 = new String("reason");
	StringTuple tuple = new StringTuple(string1, string2);
	assertTrue("destination not initialized correctly", tuple.getDestination().equals("destination"));
	assertTrue("reason not initialized correctly", tuple.getReason().equals("destination"));
	}
	
	public void testSetDestination() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		StringTuple tuple = new StringTuple(string1, string2);
		tuple.setDestination("newDestination");
		assertTrue("destination not set", tuple.getDestination().equals("newDestination"));
	}
	
	public void testSetReason() {
		String string1 = new String("destination");
		String string2 = new String("reason");
		StringTuple tuple = new StringTuple(string1, string2);
		tuple.setReason("newReason");
		assertTrue("reason not set", tuple.getDestination().equals("newReason"));
	}
}


