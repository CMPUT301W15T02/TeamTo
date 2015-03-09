/* Tests for Claim.java */

package com.CMPUT301W15T02.teamtoapp.test;

import com.CMPUT301W15T02.teamtoapp.Claim;
import junit.framework.TestCase;

public class ClaimTest extends TestCase {

	public void testAddClaim() {
		String claimName = "newClaim";
		Claim claim = new Claim();
		claim.setClaimName(claimName);
		assertTrue("Claim name not equal", claim.getClaimName().equals(claimName));
	}
	
	public void testGetClaimName() {
		
	}
}
