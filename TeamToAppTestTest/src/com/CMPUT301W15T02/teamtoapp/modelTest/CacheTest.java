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

import com.CMPUT301W15T02.teamtoapp.Model.Cache;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;

import android.test.AndroidTestCase;

/**
 * Tests the functionality of Cache.java
 */

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
