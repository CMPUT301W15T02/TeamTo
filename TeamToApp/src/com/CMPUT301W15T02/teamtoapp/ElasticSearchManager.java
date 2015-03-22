package com.CMPUT301W15T02.teamtoapp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**Responsible for online data search using ElasticSearch. In progress. All methods still need testing.
 * 
 * Future claim-related methods: adding new claims to server (claimant), retrieving claims from server (claimant/approver),
 * deleting claims from server (claimant), editing claims from server (claimant/approver).
 * 
 * Adapted some functionality from AndroidElasticSearch by Joshua Campbell 2015-03-18
 *
 * @see https://github.com/joshua2ua/AndroidElasticSearch
 * 
 *  @author Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 *
 */

public class ElasticSearchManager {

	/** Location of online server in which claims with their expenses will be saved. */
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t02/claim/";
	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t02/claim/_search";
	
	//private static final String TEST_URL = "http://cmput301.softwareprocess.es:8080/testing/claims/";
	//private static final String TEST_SEARCH_URL =  "http://cmput301.softwareprocess.es:8080/testing/claims/_search";
	
	private static final String TAG = "ClaimSearch"; // used for logcat.
	private static Context applicationContext;
	
	
	public ElasticSearchManager() {
	}
	
	
	public static void initializeContext(Context context) {
		applicationContext = context.getApplicationContext();
	}
	

	/**getClaim() obtains a Claim from RESOURCE_URL if it exists. (Claimant/approver mode)
	 *
	 * @param claimID helps search for matching Claim object by claim ID.
	 * @return Claim object retrieved (if it exists).
	 * TODO: Needs testing
	 */

	// Q: Not sure if it matters whether we have a String/Integer claimID?
	/* Source: https://github.com/joshua2ua/AndroidElasticSearch/blob/
	 master/src/ca/ualberta/ssrg/movies/es/ESMovieManager.java 2015-03-18*/
	public Claim getClaim(String claimID) {
		Gson gson = new Gson();
		
		SearchHit<Claim> search_hit = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + claimID);		
		
		HttpResponse response = null;
		
		try {
			response = httpClient.execute(httpGet);
			
		} catch (ClientProtocolException e1) {
			throw new RuntimeException(e1);
			
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		Type searchHitType = new TypeToken<SearchHit<Claim>>() {}.getType();
		
		try {
			search_hit = gson.fromJson(new InputStreamReader(response.getEntity().getContent()),
					searchHitType);
			
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
			
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
			
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return search_hit.getSource();
	}

	/**
	 * Get all submitted claims for the approver (Approver mode)
	 * If the search does not specify fields, it searches on all the fields.
	 * 
	 *  TODO: Needs testing
	 */	
	/* Source: https://github.com/joshua2ua/AndroidElasticSearch/blob/
	 master/src/ca/ualberta/ssrg/movies/es/ESMovieManager.java 2015-03-18*/
	public static ArrayList<Claim> getSubmittedClaims() {
		String searchString = "SUBMITTED";
		ArrayList<Claim> submittedClaimsResult = new ArrayList<Claim>();
		Gson gson = new Gson();
		
		HttpPost searchRequest = new HttpPost(SEARCH_URL);
		
		if (searchString.equals(null) || searchString.equals("")) {
			searchString = "*";
		}
		
		SimpleSearchCommand command = new SimpleSearchCommand(searchString);
		String query = gson.toJson(command);
		Log.i(TAG, "JSON COMMAND: " + query);
		
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(query);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpResponse response = null;
		try
		{
			response = httpClient.execute(searchRequest);
		
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
			
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
		
		/**
		* Parse the response of the search, then extract claims from esResponse
		* and add them into submittedClaimsResult ArrayList.
		*/
		Type searchResponseType = new TypeToken<SearchResponse<Claim>>() {}.getType();
		try {

			SearchResponse<Claim> esResponse = gson.fromJson(
					new InputStreamReader(response.getEntity().getContent()),
						searchResponseType);
			
			Hits<Claim> hits = esResponse.getHits();
			if (hits != null) {
				if (hits.getHits() != null) {
					for (int i = 0; i < hits.getHits().size(); i++) {
						SearchHit<Claim> searchHit = hits.getHits().get(i);
						Claim claim = searchHit.getSource();
						submittedClaimsResult.add(claim);
					}
				}
			}
			
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
			
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
			
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
		
		return submittedClaimsResult;
		
	}
	
	/**
	* adds a claim to server (Claimant mode)
	* @param claim - the claim to be added to the elastic search server
	* 
	*  TODO: Needs testing
	*/
	/*Source:
	 * https://github.com/CMPUT301F14T03/lotsofcodingkitty/blob/master/
	 * cmput301t03app/src/ca/ualberta/cs/cmput301t03app/datamanagers/ServerDataManager.java 2015-03-19*/
	public static void addClaim(final Claim claim) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Gson gson = new Gson();
				HttpClient httpClient = new DefaultHttpClient();
				try {
					
					// HttpPost for adding a claim
					HttpPost addRequest = new HttpPost(RESOURCE_URL + claim.getClaimId());
					StringEntity stringEntity = new StringEntity(gson.toJson(claim));
					addRequest.setEntity(stringEntity);
					addRequest.setHeader("Accept", "application/json");
					
					HttpResponse response = httpClient.execute(addRequest);
					
					// Can use TAG to check status via logcat.
					String status = response.getStatusLine().toString();
					Log.i(TAG, status);
					
				} catch (JsonIOException e) {
					throw new RuntimeException(e);
					
				} catch (JsonSyntaxException e) {
					throw new RuntimeException(e);
					
				} catch (IllegalStateException e) {
					throw new RuntimeException(e);
					
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
			}
		}).start();
	}
	
	/**
	 * Deletes the claim with the specified ID
	 *  @param claimId - the claimID that identifies claim to be deleted
	 *  
	 *   TODO: Needs testing
	 */
	/*Source:
	 * https://github.com/CMPUT301F14T03/lotsofcodingkitty/blob/master/
	 * cmput301t03app/src/ca/ualberta/cs/cmput301t03app/datamanagers/ServerDataManager.java 2015-03-19*/
	public static void deleteClaim(final String claimId) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				HttpClient httpClient = new DefaultHttpClient();
				try {
					
					// HTTP delete for deleting a claim
					HttpDelete deleteRequest = new HttpDelete(RESOURCE_URL + claimId);
					deleteRequest.setHeader("Accept", "application/json");
					
					HttpResponse response = httpClient.execute(deleteRequest);
					
					// Can use TAG to check status via logcat.
					String status = response.getStatusLine().toString();
					Log.i(TAG, status);
					
				} catch (JsonIOException e) {
					throw new RuntimeException(e);
					
				} catch (JsonSyntaxException e) {
					throw new RuntimeException(e);
					
				} catch (IllegalStateException e) {
					throw new RuntimeException(e);
					
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
			}
		}).start();
	}
	
	public static void updateClaim(Claim claim) {
		// Updating the claim is the same as adding the claim
		addClaim(claim);
	}
	
	
}
