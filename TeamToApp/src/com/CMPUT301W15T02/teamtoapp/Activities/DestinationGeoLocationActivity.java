package com.CMPUT301W15T02.teamtoapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.R.id;
import com.CMPUT301W15T02.teamtoapp.R.layout;
import com.CMPUT301W15T02.teamtoapp.R.menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * 
 * Records the geolocation, destination of the geolocation, and reason for travel
 * @author Kyle Carlstrom
 *
 */
public class DestinationGeoLocationActivity extends Activity {

	private GoogleMap googleMap;
	private Location location = null;
	private Marker marker = null; // marks location selected by user on googleMap 
	private Context context = this;
	
	private EditText destinationEditText;
	private EditText reasonEditText;
	private LatLng addressLatLng; // latitude and longitude saved from googleMap
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination_geo_location);
		
		// Initialize destination and reason EditTexts
		destinationEditText = (EditText) findViewById(R.id.destinationSelectEditText);
		reasonEditText = (EditText) findViewById(R.id.reasonDestinationEditText);
		
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
						findFragmentById(R.id.destinationMap)).getMap();
			}     

			// Place marker/dot on current location and add visible features
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.setMyLocationEnabled(true);
			googleMap.setBuildingsEnabled(true);

			// Show zoom buttons
			googleMap.getUiSettings().setZoomControlsEnabled(true);

			// Getting LocationManager object from System Service LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			// Getting Current Location using GPS_PROVIDER
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


		} catch (Exception e) {
			e.printStackTrace();
		}

		// Add/move existing marker to the position of the map long-clicked
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

		// Zoom into location if it exists 
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
	 * Save destination entered as long as a geolocation is specified
	 */
	private void saveDestination() {
		// Obtain destination and reason texts
		String destination = destinationEditText.getText().toString();
		String reason = reasonEditText.getText().toString();
		
		// If no destination entered, toast user to enter a destination
		if (destination.length() == 0) {
			Toast.makeText(context, "Please enter a destination.", Toast.LENGTH_SHORT).show();
		} else if (marker == null) {
			Toast.makeText(context, "Please select location by long-clicking on map.", Toast.LENGTH_SHORT).show();
		} else {
			if (reason == null) {
				reason = "";
			}
			// Obtain latitude and longitude from marker
			double latitude = marker.getPosition().latitude;
    		double longitude = marker.getPosition().longitude;
    		Intent returnIntent = new Intent();
    		
    		// Add latitude, longitude, reason, and destination to intent
    		returnIntent.putExtra("latitude", latitude);
        	returnIntent.putExtra("longitude", longitude);
        	returnIntent.putExtra("reason", reason);
        	returnIntent.putExtra("destination", destination);
        	
        	// Return intent with items attached
            setResult(RESULT_OK, returnIntent);
    		finish();
		}
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.destination_geo_location, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.save_destination_geolocation) {
			saveDestination();
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	
}
