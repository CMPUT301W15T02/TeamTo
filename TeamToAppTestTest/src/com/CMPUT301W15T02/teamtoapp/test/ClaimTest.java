/* Claim tests for TeamToApp
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


package com.CMPUT301W15T02.teamtoapp.test;

import java.util.ArrayList;
import java.util.Calendar;

import junit.framework.TestCase;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.User;
import com.CMPUT301W15T02.teamtoapp.UserController;
import com.CMPUT301W15T02.teamtoapp.StringTuple;

// Source: https://www.youtube.com/watch?v=k9ZNbsc0Qgo 2015-02-08

public class ClaimTest extends TestCase {

	public void testAddClaim () {
		/*US01.01.01
		  As a claimant, I want make an expense claim that records my name, a starting date of travel, and an ending date of travel.
		  
		*/
		User user = new User("John");
		
		// Add new claim to manager - works
		Claim claim = new Claim();
		user.addClaim(claim);
		assertNotNull("manager has no claim!", user);
		
		// Save new information for claim, check if saved in user
		String name = "new claim";
		Calendar start_date = Calendar.getInstance();
		Calendar end_date = Calendar.getInstance();
		claim.addClaimName(name);
		claim.setStartDate(start_date);
		claim.setEndDate(end_date);
		assertTrue("Name is not equal", user.getClaim(claim).getClaimName() == name);
		assertTrue("Start date is not equal", user.getClaim(claim).getStartDate() == start_date);
		assertTrue("End date is not equal", user.getClaim(claim).getEndDate() == end_date);
		
		
		/*
		 * US01.02.01
		   As a claimant, I want an expense claim to record one or more destinations of 
		   travel and an associated reason for travel to each destination.
		*/
		String dest = "some destination";
		String reason = "some reason";
		StringTuple record = new StringTuple(dest, reason);
		claim.addDestination(record);
		
		// Fixed test case error due to changing destinations from HashMap to StringTuple object.
		assertTrue("No destination and reason were added.", user.getClaim(claim).verifyDestination(record));
	}
	
	public void testEditClaim() {
		User user = new User("Sarah");
		Claim claim = new Claim();
		claim.setStatus(Claim.Status.IN_PROGRESS);
		user.addClaim(claim); // Has default values
		/*
		 * US01.04.01
		 * As a claimant, I want to edit an expense claim while changes are allowed
		 * (Haven't checked status here)
		*/
		//manager.getClaim(claim).setClaimName("new claim name");
		claim.setClaimName("in progress");
		assertEquals("Name changed when in progress", "in progress", user.getClaim(claim).getClaimName());
		
		claim.setStatus(Claim.Status.SUBMITTED);
		claim.setClaimName("sumbitted name");
		assertTrue("Claim name has not changed.", user.getClaim(claim).getClaimName().equals("in progress"));
		
		claim.setStatus(Claim.Status.RETURNED);
		claim.setClaimName("returned name");
		assertEquals("Claim name changed when returned", "returned name", user.getClaim(claim).getClaimName());
	}
		
	public void testDeleteClaim() {
		User user = new User("Peter");
		Claim claim = new Claim();
		user.addClaim(claim);
		// Remove claim from manager - works
		user.removeClaim(claim);
		assertNull("user still has this claim!", user.getClaim(claim));
		
		/* US03.01.01
		 * As a claimant, I want to give an expense claim zero or more alphanumeric tags, 
		 * so that claims can be organized by me into groups.
		 * 
		 * US03.02.01
		 * As a claimant, I want to manage my personal use of tags by listing the available tags, 
         * adding a tag, renaming a tag, and deleting a tag.
         * -Able to add, remove, and delete tag all work as of (Feb 10th 2015 11:54am)
         *
         * - work on renaming later
		 */
	}
	
	public void testAddTags() {
		Claim claim = new Claim();
		String aTag = "default tag"; //Tag with just letters
		String bTag = "def4ult t4g"; //Tag with numbers and letters
		String cTag = "123456";      //Tag with just strictly numbers
		assertTrue("Tags list is empty!",claim.getTagsListSize() == 0);
		claim.setTags(aTag);
		assertTrue("Tags list contains one tag!", claim.getTagsListSize()==1);
		claim.removeTags(aTag);
		assertTrue("The tag has been removed!", claim.getTagsListSize()==0);
		assertTrue("Tags list is empty!",claim.getTagsListSize() == 0);
		claim.setTags(aTag);
		claim.setTags(bTag);
		assertTrue("Tags list contains two tags!", claim.getTagsListSize()==2);
		claim.setTags(cTag);
		assertTrue("Tags list contains three tags!", claim.getTagsListSize()==3);
	}
	
	public void testClaimStatuses() {
		User user = new User("Peter");
		Claim claim = new Claim();
		// UC 7.0 submit claim
		user.submitClaim(claim);
		assertEquals("Claim status submitted?", Claim.Status.SUBMITTED, claim.getStatus());
		
		// UC 8.0 return claim
		user.returnClaim(claim);
		assertEquals("Claim returned?", Claim.Status.RETURNED, claim.getStatus());
		
		// UC 8.1 approve claim
		user.submitClaim(claim);
		user.approveClaim(claim);
		assertEquals("Claim approved?", Claim.Status.APPROVED, claim.getStatus());

	}
	
	
	
}
