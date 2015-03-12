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
import java.util.Observer;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;
import com.CMPUT301W15T02.teamtoapp.Model.Session;
import com.CMPUT301W15T02.teamtoapp.Model.StringTuple;
import com.CMPUT301W15T02.teamtoapp.Model.Tag;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

public class ClaimController {
	
	private String claimID;
	private Claim currentClaim;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public ClaimController(String claimID) {
		this.currentClaim = Session.getInstance().getCurrentClaims().findClaimByID(claimID);
		this.claimID = claimID;
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
	}
	
	public void removeExpense(Expense expense) {
		currentClaim.removeExpense(expense);
	}
	
	public void addTag(Tag tag) {
		currentClaim.addTag(tag);
	}
	
	public void removeTag(Tag tag) {
		currentClaim.removeTag(tag);
	}
	
	public void submitClaim() {
		currentClaim.setStatus(Status.SUBMITTED);
	}
	
	public void returnClaim() {
		currentClaim.setStatus(Status.RETURNED);
	}
	
	public void approvedClaim() {
		currentClaim.setStatus(Status.APPROVED);
	}
	
	public void setStartDate(GregorianCalendar date) {
		currentClaim.setStartDate(date);
	}
	
	public Calendar getStartDate() {
		return currentClaim.getStartDate();
	}
	
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
	
	public String getEndDateFormatted() {
		return formatter.format(getEndDate().getTime());
	}
	
	public void setUser(String name) {
		currentClaim.setUser(name);
	}
	
	public void addComment(String comment) {
		currentClaim.setComment(comment);
	}
	
	public void addDestination(String destination, String reason) {
		StringTuple newDestination = new StringTuple(destination, reason);
		currentClaim.addDestination(newDestination);
	}
	
	public ArrayList<Expense> getExpenses() {
		return currentClaim.getExpenses();
	}
	
	public int checkExpensesComplete() {
		int numIncomplete = 0;
		
		if (currentClaim.getExpenses().size() == 0) {
			return 0;
		} else {
			for (Expense expense : currentClaim.getExpenses()) {
				if (expense.getComplete() == false) { numIncomplete++; }
			}
		}
		
		return numIncomplete;
	}
	
	public ArrayList<StringTuple> getDestinations() {
		return currentClaim.getDestinations();
	}
	
	public void removeDestination(StringTuple destination) {
		if (currentClaim.getDestinations().contains(destination)) {
			currentClaim.getDestinations().remove(destination);
		}
	}
	
	public void addObserverToClaim(Observer observer) {
		currentClaim.addObserver(observer);
	}
	
	public void removeObserverFromClaim(Observer observer) {
		currentClaim.deleteObserver(observer);
	}
	
	
	public ArrayList<Tag> getTags() {
		return currentClaim.getTags();
	}
	
}
