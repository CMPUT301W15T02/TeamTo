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

/**
 * GoogleMaps for user - saves default latitude longitude/ manually enter an address.
 * 
 * Sources:
 * @see http://www.newthinktank.com/2015/01/make-android-apps-23/ 2015-03-27
// @see http://www.newthinktank.com/2015/01/make-android-apps-23/ 2015-03-27
 * */
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

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.R.id;
import com.CMPUT301W15T02.teamtoapp.R.layout;
import com.CMPUT301W15T02.teamtoapp.R.menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class GoogleMapActivity extends Activity {

	// Define default latitude and longitude for user (University of Alberta, Edmonton, CA)
	static final LatLng defaultLatLng = new LatLng(53.523218900000, -113.526318599999970);
	
	// Store lat and long for address entered by user
	LatLng addressLatLng;
	private GoogleMap googleMap;
	private EditText addressEditText;
	
	// Marker used when user chooses own address
	private Marker marker;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);
		
		// Initialize addressEditText
		addressEditText = (EditText) findViewById(R.id.addressEditText);
		
		// Initialize googleMap
		try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            // Place dot on current location, add visible features
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            
            // Show zoom buttons
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            
            // Add marker to default location
            marker = googleMap.addMarker(new MarkerOptions().
                    position(defaultLatLng).title("University of Alberta"));
            
            // Move camera to edmonton location and zoom in
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 15));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            
		} catch (Exception e) {
                e.printStackTrace();
        }
		
	}
	
    // Called when getAddressButton is clicked
    public void showAddressMarker(View view) {
 
        // Get the street address entered
        String newAddress = addressEditText.getText().toString();
 
        if(newAddress != null){
 
            // Call for the AsyncTask to place a marker (PlaceMarker class made below)
            new PlaceAMarker().execute(newAddress);
 
        }
 
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/** 
	 * PlaceMarker class: 
	 * 
	 * Grabs new address along with 
	 * latitude and longitude (via getLatLong method).
	 * Then, post the marker on the screen of the new address.
	*/
    class PlaceAMarker extends AsyncTask<String, String, String> {
    	 
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
 
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
 
            // Change marker location on the screen
            marker.setPosition(addressLatLng);
            marker.setTitle(addressEditText.getText().toString());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addressLatLng, 15));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            
            // TODO: Need to save the latitude and longitude from here into geolocation object
            // which will then be saved in the user.
        }
 
    }
    
    protected void getLatLong(String address){
    	 
    	Log.i("CHECKING.","EVERYTHING OK HERE...");
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
 
            // Change the lat and long in the address 
            addressLatLng = new LatLng(lat, lng);
 
        } catch (JSONException e) {
            e.printStackTrace();
        }
 
    }
}
