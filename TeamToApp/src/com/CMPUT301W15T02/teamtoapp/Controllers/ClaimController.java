/* 
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

package com.CMPUT301W15T02.teamtoapp.Controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;
import com.CMPUT301W15T02.teamtoapp.Model.Destination;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;

/** 
 * 
 * Claim controller is responsible for communicating from any associated views to the model
 * as well as attaching listeners to the model
 *
 */

public class ClaimController {
	

	private Claim currentClaim;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public ClaimController(String claimID) {
		this.currentClaim = ClaimList.getInstance().findClaimByID(claimID);
	}
	
	public Claim getCurrentClaim() {
		return currentClaim;
	}
	
	public String getClaimName() {
		return currentClaim.getClaimName();
	}
	
	public void setClaimName(String name) {
		currentClaim.setClaimName(name);
	}
	
	public void addExpense(Expense expense) {
		currentClaim.addExpense(expense);
		MainManager.updateClaim(currentClaim);
	}
	
	public void removeExpense(Expense expense) {
		currentClaim.removeExpense(expense);
		MainManager.updateClaim(currentClaim);
	}
	
	
	/**
	 * Responsible for submitting a claim for approval
	 * Currently the functionality is incomplete
	 * 
	 */
	public void submitClaim() {
		currentClaim.setStatus(Status.SUBMITTED);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				MainManager.updateClaim(currentClaim);
				
			}
		}).start();
	}
	
	/**
	 * Responsible for returning a claim that was submitted
	 * Currently the functionality is incomplete
	 * 
	 */
	public void returnClaim(String comment, String approver) {
		currentClaim.setStatus(Status.RETURNED);
		currentClaim.setComment(comment);
		currentClaim.setApproverName(approver);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ElasticSearchManager.updateClaim(currentClaim);
				
			}
		}).start();
	}
	
	/**
	 * Responsible for approving a claim that was submitted
	 * Currently the functionality is incomplete
	 * 
	 */
	public void approvedClaim(String comment, String approver) {
		currentClaim.setStatus(Status.APPROVED);
		currentClaim.setComment(comment);
		currentClaim.setApproverName(approver);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ElasticSearchManager.updateClaim(currentClaim);
				
			}
		}).start();
	}
	
	public void setStartDate(GregorianCalendar date) {
		currentClaim.setStartDate(date);
	}
	
	public Calendar getStartDate() {
		return currentClaim.getStartDate();
	}
	
	/**
	 * Gets the start date as a properly formatted string
	 * @return	A string representing the start date
	 */
	public String getStartDateFormatted() {
		return formatter.format(getStartDate().getTime());
	}
	
	// Check end date > start date?
	public void setEndDate(GregorianCalendar date) {
		currentClaim.setEndDate(date);
	}
	
	public Calendar getEndDate() {
		return currentClaim.getEndDate();
	}
	
	/**
	 * Gets the end date as a properly formatted string
	 * @return	A string representing the end date
	 */
	public String getEndDateFormatted() {
		return formatter.format(getEndDate().getTime());
	}
	
	public void setUserName(String name) {
		currentClaim.setUserName(name);
	}
	
	public void addComment(String comment) {
		currentClaim.setComment(comment);
	}
	
	public ArrayList<Expense> getExpenses() {
		return currentClaim.getExpenses();
	}
	
	/**
	 * Checks the number of expenses that are complete.
	 * Makes sure to count fields with missing values.
	 * @return	Number of incomplete expenses
	 */
	public int checkExpensesComplete() {
		int numIncomplete = 0;
		if (currentClaim.getExpenses().size() == 0) {
			return 0;
		} else {
			for (Expense expense : currentClaim.getExpenses()) {
				if (expense.getComplete() == false || expense.getAmount() == 0.0
						|| expense.getDescription().length() == 0) { numIncomplete++; }
			}
		}
		return numIncomplete;
	}
	
	/**
	 * Takes in two strings and makes a string tuple that is added to the claim
	 * @param destination	String representing the destination
	 * @param reason		String representing the reason for visiting the destination
	 */
	public void addDestination(String destination, String reason, double latitude, double longitude) {
		Destination newDestination = new Destination(destination, reason, latitude, longitude);
		currentClaim.addDestination(newDestination);
	}
	
	public ArrayList<Destination> getDestinations() {
		return currentClaim.getDestinations();
	}
	
	public void removeDestination(Destination destination) {
		if (currentClaim.getDestinations().contains(destination)) {
			currentClaim.getDestinations().remove(destination);
		}
	}
	
	
	/**
	 * Adds a listener to the current claim
	 * @param listener
	 */
	public void addListenerToClaim(Listener listener) {
		currentClaim.addListener(listener);
	}
	
	/**
	 * Removes a listener from the current claim
	 * @param listener
	 */
	public void removeListenerFromClaim(Listener listener) {
		currentClaim.removeListener(listener);;
	}
	
	
	public ArrayList<Tag> getTags() {
		return currentClaim.getTags();
	}
	
	/**
	 * Method to tell if a claim should be currently editable or not
	 * @return	boolean representing a claims editable status (true for editable, false otherwise)
	 */
	public boolean isEditable() {
		if (currentClaim.getStatus().equals(Status.IN_PROGRESS) || currentClaim.getStatus().equals(Status.RETURNED)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Helper method which checks if a claim is missing any information that should be added before it is submitted
	 * @return	false if incomplete, true if it is complete
	 */
	public boolean checkClaimInfoComplete() {
		// TODO: Need to do tests for this method.
		if (currentClaim.getClaimName().isEmpty()) {
			return false;
		}
		
		if (currentClaim.getDestinations().size() == 0) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Called when submitting a claim, will return a string representing the current outcome of the submission
	 * @return	information about current status of claim
	 */
	public String checkBeforeSubmittingClaim() {
		if (checkClaimInfoComplete() == false) {
			return "Claim information incomplete";
		} else if (getExpenses().size() == 0) {
			return "Claim has no expenses";
		}
		
		int numExpensesIncomplete = checkExpensesComplete();
		
		if (currentClaim.getStatus() == Status.IN_PROGRESS) {
			if (numExpensesIncomplete > 0) {
				return "Expenses are incomplete.";
			} else {
				submitClaim();
				return "Claim successfully submitted";
			}
			
		} else if (currentClaim.getStatus() == Status.APPROVED) {
			return "Claim was already approved.";
		} else if (currentClaim.getStatus() == Status.RETURNED) {
			return null;
		} else  {
			return "Claim already submitted.";
		}
	}

	public void addTag(String tagID) {
		currentClaim.addTag(tagID);
		
	}

	public void removeTag(String tagID) {
		currentClaim.removeTag(tagID);
		
	}
	
	
	public String getApproverName() {
		return currentClaim.getApproverName();
	}
	
	public String getApproverComment() {
		 return currentClaim.getComment();
	}
	
}
