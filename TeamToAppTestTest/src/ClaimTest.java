import junit.framework.TestCase;
import com.CMPUT301W15T02.teamtoapp.Claim;


public class ClaimTest extends TestCase {

	public void testAddClaim() {
		String claimName = "claim1";
		Claim claim = new Claim();
		assertTrue("Claim name is not equal", claim.getClaimName().equals(claimName));
	}
		

}
