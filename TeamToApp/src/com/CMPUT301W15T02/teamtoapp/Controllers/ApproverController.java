package com.CMPUT301W15T02.teamtoapp.Controllers;

import java.util.ArrayList;

import android.R.integer;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.Model.ApproverClaims;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.User;
import com.CMPUT301W15T02.teamtoapp.Model.Claim.Status;

/**
 * Controller for the approver
 * 
 * @author Kyle Carlstrom
 *
 */
public class ApproverController {
	
	private ArrayList<Claim> claims;
	private String approverName;
	
	/**
	 * Constructor for controller contains list of submitted claims
	 * and approver name from user singleton
	 */
	public ApproverController() {
		claims = ApproverClaims.getInstance().getClaims();
		approverName = User.getInstance().getName();
	}

	public ArrayList<Claim> getClaims() {
		return claims;
	}

	public void setClaims(ArrayList<Claim> claims) {
		this.claims = claims;
	}
	
	
	public Claim getClaim(int position) {
		return claims.get(position);
	}
	
	
	/**
	 * Method for approving a selected claim by the approver
	 * @param claim - claim to be approved
	 * @param comment - non-empty comment added by approver
	 */
	public void approveClaim(Claim claim, String comment) {
		claim.setApproverName(User.getInstance().getName());
		claim.setComment(comment);
		claim.setStatus(Status.APPROVED);
		MainManager.ApproveClaim(claim);
	}
	
	
	/**
	 * Method for returning a selected claim by the approver
	 * @param claim - claim to be returned
	 * @param comment - non-empty comment added by approver
	 */
	public void returnClaim(Claim claim, String comment) {
		claim.setApproverName(User.getInstance().getName());
		claim.setComment(comment);
		claim.setStatus(Status.RETURNED);
		MainManager.ApproveClaim(claim);
	}

	public String getApproverName() {
		return approverName;
	}
	
	
	
}
