/*Copyright 2015 Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
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

package com.CMPUT301W15T02.teamtoapp.Activities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.CMPUT301W15T02.teamtoapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * GoogleMaps for user - saves default latitude longitude/ manually enter an address.
 * 
 * Sources:
 * @see http://www.newthinktank.com/2015/01/make-android-apps-23/ 2015-03-27
// @see http://www.newthinktank.com/2015/01/make-android-apps-24/ 2015-03-27
 * @see http://stackoverflow.com/questions/16005223/android-google-map-api-v2-current-location 2015-03-29
 * 
 * @authors Kyle Carlstrom, Raman Dhatt
 * */

public class HomeGeoLocationActivity extends Activity {

	static final LatLng defaultLatLng = new LatLng(53.523218900000, -113.526318599999970); // default when location N/A
	LatLng addressLatLng; // Store latitude and longitude for address entered by user
	private GoogleMap googleMap;
	private EditText addressEditText; // Address/marker entered by user
	private Marker marker = null; // Displays user location
	private Context context = this;
	Location location = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);
		// TODO get extra from intent
		// have it be one of homelocation, expenselocation, or destinationlocation
		// or something like that
		
		addressEditText = (EditText) findViewById(R.id.addressEditText);
		
		// Check status of Google play services
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		
        if (status != ConnectionResult.SUCCESS) { 
        	
        	// Google Play Services are not available - display dialog
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
            return;
 
        }
        

        // Google Play Services are available
        try {

        	// Initialize GoogleMap, grab from map fragment
        	if (googleMap == null) {
        		googleMap = ((MapFragment) getFragmentManager().
        				findFragmentById(R.id.map)).getMap();
        	}     

        	// Place dot on current location and add visible features
        	googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        	googleMap.setMyLocationEnabled(true);
        	googleMap.setBuildingsEnabled(true);

        	// Show zoom buttons
        	googleMap.getUiSettings().setZoomControlsEnabled(true);

        	// Getting LocationManager object from System Service LOCATION_SERVICE
        	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        	// Getting Current Location using GPS_PROVIDER
        	location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        	// Clear any existing markers

        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", 0.0);
        double longitude = intent.getDoubleExtra("longitude", 0.0);
        if (latitude != 0.0 || longitude != 0.0) {
        	LatLng passedLocation = new LatLng(latitude, longitude);
        	marker = googleMap.addMarker(new MarkerOptions().position(passedLocation));
        }
        
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng arg0) {
				if (marker == null) {
					marker = googleMap.addMarker(new MarkerOptions().position(arg0));
					marker.setDraggable(true);
				} else {
					marker.setPosition(arg0);
				}
			}
		});
        
        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
			
			@Override
			public boolean onMyLocationButtonClick() {
				if (location != null) {
					addressLatLng = new LatLng(location.getLatitude(), location.getLongitude());
					googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addressLatLng, 15));
		            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
					
				}
				return false;
			}
		});
        


	}
		
	
	/**
	 *  Called when getAddressButton is clicked
	 *  newAddress: address obtained from addressEditText
	 *  
	 *  Executes the ChangeMarker Class
	 * */
    public void showAddressMarker(View view) {
 
        // Get the street address entered
        String newAddress = addressEditText.getText().toString();
 
        if(!newAddress.isEmpty()){
 
            // Call for the AsyncTask to change location of the marker (ChangeMarker class made below)
            new ChangePosition().execute(newAddress);
 
        } else {
        	Toast.makeText(getApplicationContext(), "Please enter location", Toast.LENGTH_SHORT).show();
        }
 
    }
	
	
	/** 
	 * ChangeMarker class: 
	 * 
	 * Grabs new address along with 
	 * latitude and longitude (via getLatLong method).
	 * Then, post the marker on the screen of the new address.
	*/
    class ChangePosition extends AsyncTask<String, String, String> {
    	 
		@Override
        protected String doInBackground(String... params) {
 
            // Get the 1st address passed
            String uriString = params[0];
 
            // Replace the spaces with %20 
            // (cannot have spaces in URI to grab the latitude and longitude)
            uriString = uriString.replaceAll(" ","%20");
 
            // Call for the latitude and longitude and pass in the address.
            getLatLong(uriString);
 
            return null;
        }
		
		/**
		 * onPostExecute:
		 * 
		 * when latitude and longitude obtained,
		 * change the position of the marker,
		 * and change the title to the address entered by the user.
		 */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Change marker location on the screen
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addressLatLng, 15));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }
    
    /**
     * getLatLong:
     * 
     * Obtains latitude and longitude from the address parameter
     * by creating a uri based on the address and a JSON
     * object to save the resulting string entity from the uri 
     * 
     * @param address -  the address entered by the user, which 
     * 					is a part of the uri.
     */
    protected void getLatLong(String address){
    	 
        // Define the uri that is used to get lat and long for our address
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                address + "&sensor=false";
 
        // Use the get method to retrieve our data
        HttpGet httpGet = new HttpGet(uri);
 
        // Acts as the client which executes HTTP requests
        HttpClient client = new DefaultHttpClient();
 
        // Receives the response from our HTTP request
        HttpResponse response;
 
        // Will hold the data received
        StringBuilder stringBuilder = new StringBuilder();
 
        try {
 
            // Get the response of our query
            response = client.execute(httpGet);
 
            // Receive the entity information sent with the HTTP message
            HttpEntity entity = response.getEntity();
 
            // Holds the sent bytes of data
            InputStream stream = entity.getContent();
            int byteData;
 
            // Continue reading data while available
            while ((byteData = stream.read()) != -1) {
                stringBuilder.append((char) byteData);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        double lat = 0.0, lng = 0.0;
 
        // Holds key value mappings
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
 
            // Get the returned latitude and longitude
            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");
 
            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");
 
            // Change the latitude and longitude in the address 
            addressLatLng = new LatLng(lat, lng);
 
        } catch (JSONException e) {
            e.printStackTrace();
        }
 
    }
    
    /**
     * When user clicks save on action bar, current location/ default location will be saved.
     * */
    public void onSaveUserLocation() {
    	Intent returnIntent = new Intent();
    	if (marker != null) {
            /**Save the latitude and longitude from here into geoLocation object
             * which will then be saved in the user.
             * 
             * TODO: Need to make sure GeoLocation is saved properly.
            */
    		double latitude = marker.getPosition().latitude;
    		double longitude = marker.getPosition().longitude;
    		returnIntent.putExtra("latitude", latitude);
    		returnIntent.putExtra("longitude", longitude);
            setResult(RESULT_OK, returnIntent);
    	} else {
    		setResult(RESULT_CANCELED, returnIntent);
    	}
    	finish();
    }
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.google_map, menu);
		return true;
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.save_user_geolocation) {
			onSaveUserLocation();
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onBackPressed() {
		onSaveUserLocation();
	}
	
	

}
