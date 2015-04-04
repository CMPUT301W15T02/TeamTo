/* Copyright 2015 Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.util.Log;

import com.CMPUT301W15T02.teamtoapp.Model.ApproverClaims;
import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.User;
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
 * @author Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 *
 */

public class ElasticSearchManager {

	/** Location of online server in which claims with their expenses will be saved. */
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t02/claim/";
	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t02/claim/_search";
	private static final String USER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t02/user/";
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
	 * Source: https://github.com/joshua2ua/AndroidElasticSearch/blob/
	 			master/src/ca/ualberta/ssrg/movies/es/ESMovieManager.java 2015-03-18
	 */
	public static Claim getClaim(String claimID) {
		Gson gson = new Gson();
		
		SearchHit<Claim> search_hit = null;
		
		// Create HttpClient to obtain URL based on claim ID
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + claimID);		
		
		HttpResponse response = null;
		
		try {
			
			// Try to get URL
			response = httpClient.execute(httpGet);
			Log.i("RESPONSE", response.getStatusLine().toString());
			
		} catch (ClientProtocolException e1) {
			throw new RuntimeException(e1);
			
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		// Create searchHitType Object to save result from search
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
		
		// Return resulting claim (if any)
		return search_hit.getSource();
	}

	/**
	 * Get all submitted claims for the approver (Approver mode)
	 * If the search does not specify fields, it searches on all the fields.
	 * 
	 * @see Source: https://github.com/joshua2ua/AndroidElasticSearch/blob/
	 	master/src/ca/ualberta/ssrg/movies/es/ESMovieManager.java 2015-03-18
	 */	
	public static void getSubmittedClaims() {
		String searchString = "SUBMITTED";
		ArrayList<Claim> submittedClaimsResult = new ArrayList<Claim>();
		Gson gson = new Gson();
		
		// Create HttpPost object to search for claims
		HttpPost searchRequest = new HttpPost(SEARCH_URL);
		
		if (searchString.equals(null) || searchString.equals("")) {
			// Default search = search all
			searchString = "*";
		}
		
		// Command object takes in claim status search string
		SimpleSearchCommand command = new SimpleSearchCommand(searchString);
		
		// Create query based on command
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
			// Search for claims that match the searchString (Status == "SUBMITTED")
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
					// Add each submitted claim to submittedClaimsResult ArrayList
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
		
		/* Call filterSubmittedClaims method to remove claims
		where claimant name == approver name */
		filterSubmittedClaims(submittedClaimsResult);
		
	}
	
	/**
	 * Filters out any claims where the claimant name == approver name.
	 * @param submittedClaimsResult - the list of submitted claims to be filtered.
	*/
	private static  void filterSubmittedClaims(
			ArrayList<Claim> submittedClaimsResult) {
		
		// Filter out claims where claimant name == approver name
		for (int i=0; i < submittedClaimsResult.size(); i++) {
			if (submittedClaimsResult.get(i).getUserName().equals(User.getInstance().getName())) {
				Log.i("UNWANTED USER",submittedClaimsResult.get(i).getUserName());
				submittedClaimsResult.remove(i);
			}
		}
		ApproverClaims.getInstance().setClaims(submittedClaimsResult);
	}


	/**
	 * Adds a claim to server (Claimant mode)
	 * 
	 * @param claim - the claim to be added to the elastic search server
	 * @see Source: https://github.com/CMPUT301F14T03/lotsofcodingkitty/blob/master/
	 * 		cmput301t03app/src/ca/ualberta/cs/cmput301t03app/datamanagers/ServerDataManager.java 2015-03-19
	*/
	public static void addClaim(final Claim claim) {
		Gson gson = new Gson();
		HttpClient httpClient = new DefaultHttpClient();
		try {

			// HttpPost for adding a claim
			HttpPost addRequest = new HttpPost(RESOURCE_URL + claim.getClaimId());
			StringEntity stringEntity = new StringEntity(gson.toJson(claim));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			// Create response object to add claim
			HttpResponse response = httpClient.execute(addRequest);

			// Check Status
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
	
	/**
	 * 	Deletes the claim with the specified ID
	 *  @param claimId - the claimID that identifies claim to be deleted
	 *  @see Source: https://github.com/CMPUT301F14T03/lotsofcodingkitty/blob/master/
	 *				 cmput301t03app/src/ca/ualberta/cs/cmput301t03app/datamanagers/ServerDataManager.java 2015-03-19
	 */
	public static void deleteClaim(final String claimId) {
		HttpClient httpClient = new DefaultHttpClient();
		try {

			// HTTP delete for deleting a claim
			HttpDelete deleteRequest = new HttpDelete(RESOURCE_URL + claimId);
			deleteRequest.setHeader("Accept", "application/json");

			// Create response object to delete claim
			HttpResponse response = httpClient.execute(deleteRequest);

			// Check Status
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
	
	
	/**
	 * Updates an existing claim by re-adding to server (overwrites old claim)
	 *  @param claim - the claim to be updated
	 */
	public static void updateClaim(Claim claim) {
		addClaim(claim);
	}
	
	
	/**
	 *  Save local user to the server (Claimant mode)
	 *  
	 */
	public static void saveUser() {
		Gson gson = new Gson();
		HttpClient httpClient = new DefaultHttpClient();
		try {

			// HttpPost for adding a claim
			HttpPost addRequest = new HttpPost(USER_URL + User.getInstance().getName());
			StringEntity stringEntity = new StringEntity(gson.toJson(User.getInstance()));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			// Create response object to save user
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
	
	/**
	 *  Load local user from the server (Claimant mode)
	 *  @param name - name of user required to load user
	 *  
	 */
	public static void loadUser(String name) {
		HttpParams httpParameters = new BasicHttpParams();
		
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		Gson gson = new Gson();

		SearchHit<User> search_hit = null;
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpGet httpGet = new HttpGet(USER_URL + name);		

		HttpResponse response = null;

		try {
			// Create response object to load user
			response = httpClient.execute(httpGet);
			Log.i("RESPONSE", response.getStatusLine().toString());

		} catch (ClientProtocolException e1) {
			LocalDataManager.loadUser();
			return;

		} catch (IOException e1) {
			LocalDataManager.loadUser();
			return;
		}

		Type searchHitType = new TypeToken<SearchHit<User>>() {}.getType();

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
		
		// Save result into User object
		User user = search_hit.getSource();
		if (user == null) {
			User.getInstance();
		} else {
			User.setUser(user);
		}



	}
	
	/**
	 *  Load user claims from the server (Claimant mode)
	 *  @param name - name of user required to load user's claims
	 *  
	 */
	public static void loadClaims(String username) {
		HttpParams httpParameters = new BasicHttpParams();
		
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		String searchString = username;
		ArrayList<Claim> userClaims = new ArrayList<Claim>();
		Gson gson = new Gson();

		// Create response object to load list of claims
		HttpPost searchRequest = new HttpPost(SEARCH_URL);

		if (searchString.equals(null) || searchString.equals("")) {
			// Default search: search all claims
			searchString = "*";
		}

		// Command object takes in username search string
		SimpleSearchCommand command = new SimpleSearchCommand(searchString);
		
		// Create query from command
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

		HttpClient httpClient = new DefaultHttpClient(httpParameters);

		HttpResponse response = null;
		try
		{
			// Create response object to load claims
			response = httpClient.execute(searchRequest);

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);

		} catch (ClientProtocolException e) {
			LocalDataManager.loadClaims();
			return;

		} catch (IOException e) {
			LocalDataManager.loadClaims();
			return;

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
					// Add each submitted claim to userClaims ArrayList
					for (int i = 0; i < hits.getHits().size(); i++) {
						SearchHit<Claim> searchHit = hits.getHits().get(i);
						Claim claim = searchHit.getSource();
						// Make username matches
						if (claim.getUserName().equals(username)) {
							userClaims.add(claim);
						}
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

		// Set ClaimList of user as the user claims
		ClaimList.getInstance().setClaims(userClaims);


	}


	
}
