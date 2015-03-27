package com.CMPUT301W15T02.teamtoapp.Commands;

import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;

public class deleteCommand implements Command {

	private String claimID;
	
	public deleteCommand(String claimID) {
		this.claimID = claimID;
	}
	
	
	@Override
	public void execute() {
		ElasticSearchManager.deleteClaim(claimID);
	}

}
