/* Tests for ClaimsList.java */

package com.CMPUT301W15T02.teamtoapp.test;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.ClaimList;
import junit.framework.TestCase;

public class ClaimListTest extends TestCase {

	public void testAddClaim() {
		ClaimList claims = new ClaimList();
		//assert("ClaimsList contains claim when not supposed to", claims.getClaims());
		
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		String claimName1 = "firstClaim";
		String claimName2 = "secondClaim";
		claim1.setClaimName(claimName1);
		claim2.setClaimName(claimName2);
		claims.addClaim(claim1);
		
	}
}
