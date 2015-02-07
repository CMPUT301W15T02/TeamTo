import java.util.ArrayList;

/*
 * Singleton to store a list of claims
 * 
 * Using singleton because it will exist as along as the app stays in memory
 * and gives access to all of the claims and associated expenses throughout the app
 */
public class ClaimManager {
	
	// Will need context to implement saving/loading from file but can probably just pass that to each method
	
	private static ClaimManager instance = null;
	private ArrayList<Claim> claims;
	
	// Private constructor called only if an instance has not been created
	private ClaimManager(){
		claims = new ArrayList<Claim>();
	};
	
	// Returns the instance of ClaimManager if it exists, otherwise calls the private constructor and returns that instance
	public static ClaimManager getInstance() {
		if (instance==null) {
			instance = new ClaimManager();
		}
		return instance;
	}
	
	public void addClaim(Claim claim) {
		claims.add(claim);
	}
	
	public void removeClaim(Claim claim) {
		claims.remove(claim);
	}
}
