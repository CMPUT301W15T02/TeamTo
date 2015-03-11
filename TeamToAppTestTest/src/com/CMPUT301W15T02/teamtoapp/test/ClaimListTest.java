/* Tests for ClaimsList.java */

package com.CMPUT301W15T02.teamtoapp.test;

import com.CMPUT301W15T02.teamtoapp.Claim;
import com.CMPUT301W15T02.teamtoapp.ClaimList;
import junit.framework.TestCase;

public class ClaimListTest extends TestCase {

	public void testAddClaim() {
		ClaimList claims = new ClaimList();

		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		String claimName1 = "firstClaim";
		String claimName2 = "secondClaim";
		claim1.setClaimName(claimName1);
		claim2.setClaimName(claimName2);
		claims.addClaim(claim1);
		assertTrue("Claim not added properly", claims.getClaims().size() == 1);
		
		claims.addClaim(claim2);
		assertTrue("Claim not added properly", claims.getClaims().size() == 2);
	}
	
	public void testGetClaim() {
		ClaimList claims = new ClaimList();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		String claimName1 = "firstClaim";
		String claimName2 = "secondClaim";
		claim1.setClaimName(claimName1);
		claim2.setClaimName(claimName2);
		claims.addClaim(claim1);
		assertTrue("Didn't get right claim", claims.getClaim(claim1).equals(claim1));
		assertNull("Didn't get right claim", claims.getClaim(claim2));
		
		claims.addClaim(claim2);
		assertTrue("Didn't get right claim", claims.getClaim(claim2).equals(claim2));
	}
	
	public void testRemoveClaim() {
		ClaimList claims = new ClaimList();
		Claim claim1 = new Claim();
		Claim claim2 = new Claim();
		String claimName1 = "firstClaim";
		String claimName2 = "secondClaim";
		claim1.setClaimName(claimName1);
		claim2.setClaimName(claimName2);
		claims.addClaim(claim1);
		claims.addClaim(claim2);
	}
	
}
