package com.CMPUT301W15T02.teamtoapp;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class DestinationGeoLocationActivity extends Activity {

	private GoogleMap googleMap;
	private Location location = null;
	private Marker marker = null;
	private Context context = this;
	
	private EditText destinationEditText;
	private EditText reasonEditText;
	private LatLng addressLatLng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination_geo_location);
		
		
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
	
	
	private void saveDestination() {
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.destination_geo_location, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save_destination_geolocation) {
			saveDestination();
		}
		return super.onOptionsItemSelected(item);
	}
}
