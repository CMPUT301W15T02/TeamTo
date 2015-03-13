package com.CMPUT301W15T02.teamtoapp.Utilities;

import java.util.Comparator;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;

public class ClaimComparatorNewestFirst implements Comparator<Claim> {

	@Override
	public int compare(Claim lhs, Claim rhs) {
		return rhs.getStartDate().compareTo(lhs.getStartDate());
	}

}
