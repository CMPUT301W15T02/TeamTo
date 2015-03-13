package com.CMPUT301W15T02.teamtoapp.Utilities;

import java.util.Comparator;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;

public class ClaimComparatorOldestFirst implements Comparator<Claim> {

	@Override
	public int compare(Claim lhs, Claim rhs) {
		return lhs.getStartDate().compareTo(rhs.getStartDate());
	}

}
