package com.CMPUT301W15T02.teamtoapp.Commands;

import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;

public class updateCommand implements Command {

	private Claim claim;
	
	public updateCommand(Claim claim) {
		this.claim = claim;
	}
	
	@Override
	public void execute() {
		ElasticSearchManager.addClaim(claim);
	}

}
