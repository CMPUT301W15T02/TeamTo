package com.CMPUT301W15T02.teamtoapp.modelTest;

import com.CMPUT301W15T02.teamtoapp.Model.Cache;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;

import android.test.AndroidTestCase;

public class CacheTest extends AndroidTestCase {
	
	public void testAddUpdateToCache() {
		Cache.initializeContext(mContext);
		Claim claim = new Claim();
		Cache.getInstance().addUpdate(claim);
		assertEquals("Added correctly", 1, Cache.getInstance().getUpdates().size());
		Cache.tearDownForTesting();
	}
	
	
	public void testAddRemovalToCache() {
		Cache.initializeContext(mContext);
		Claim claim = new Claim();
		Cache.getInstance().addRemoval(claim);
		assertEquals("Added correctly", 1, Cache.getInstance().getRemovals().size());
		Cache.tearDownForTesting();
	}
	
	
	public void testDeleteFromUpdate() {
		Cache.initializeContext(mContext);
		Claim claim = new Claim();
		Cache.getInstance().addUpdate(claim);
		Cache.getInstance().addRemoval(claim);
		assertEquals("Added correctly", 0, Cache.getInstance().getUpdates().size());
		Cache.tearDownForTesting();
	}
	
	public void testUpdateSaving() {
		Cache.initializeContext(mContext);
		Claim claim = new Claim();
		Cache.getInstance().addUpdate(claim);
		Cache.tearDownForTesting();
		Cache.getInstance().loadUpdates();
		assertEquals("Saving updates working?", 1, Cache.getInstance().getUpdates().size());
		Cache.tearDownForTesting();
	}
	
	
	public void testRemovalSaving() {
		Cache.initializeContext(mContext);
		Claim claim = new Claim();
		Cache.getInstance().addRemoval(claim);
		Cache.tearDownForTesting();
		Cache.getInstance().loadRemovals();
		assertEquals("Saving updates working?", 1, Cache.getInstance().getRemovals().size());
		Cache.tearDownForTesting();
	}
	
	public void testClearCache() {
		Cache.initializeContext(mContext);
		Claim claim = new Claim();
		Claim other = new Claim();
		Cache.getInstance().addRemoval(claim);
		Cache.getInstance().addUpdate(other);
		assertEquals(1, Cache.getInstance().getUpdates().size());
		assertEquals(1, Cache.getInstance().getRemovals().size());
		
		Cache.getInstance().clearCache();
		assertEquals(0, Cache.getInstance().getUpdates().size());
		assertEquals(0, Cache.getInstance().getRemovals().size());
		Cache.tearDownForTesting();
	}
	
}
