package com.CMPUT301W15T02.teamtoapp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**Responsible for online data search using ElasticSearch. In progress.
 * 
 * Future claim-related methods: adding new claims to server (claimant), retrieving claims from server (claimant/approver),
 * deleting claims from server (claimant), editing claims from server (claimant/approver).
 * 
 * Adapted from AndroidElasticSearch by Joshua Campbell 2015-03-18
 *
 * @see https://github.com/joshua2ua/AndroidElasticSearch
 * 
 *  @author Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
 *
 */

public class ElasticSearchManager {

	/** Location of online server in which claims with their expenses will be saved. */
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301w15t02/claim";
	private static final String TEST_URL = "http://cmput301.softwareprocess.es:8080/testing/";
	private static final String TAG = "ClaimSearch"; // Not really sure what it's for yet, but I know we need one lol.
	private static Context applicationContext;
	private Gson gson;
	
	
	public ElasticSearchManager() {
		gson = new Gson();
	}
	
	
	public void initializeContext(Context context) {
		applicationContext = context.getApplicationContext();
	}
	

	/**getClaim() obtains a Claim from RESOURCE_URL (if it exists).
	 *
	 * @param claimID helps search for matching Claim object by claim ID.
	 * @return Claim object retrieved (if it exists).
	 */
	// Q: need to have an integer claim ID instead of a String claimID?
	// Source: https://github.com/joshua2ua/AndroidElasticSearch/blob/master/src/ca/ualberta/ssrg/movies/es/ESMovieManager.java
	public Claim getClaim(int id) {
		
		SearchHit<Claim> search_hit = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + id);		
		
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
			search_hit = gson.fromJson(
			new InputStreamReader(response.getEntity().getContent()),
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
}
