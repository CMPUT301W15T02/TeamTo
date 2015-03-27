package com.CMPUT301W15T02.teamtoapp.Activities;

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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

// Source: http://www.newthinktank.com/2015/01/make-android-apps-23/ 2015-03-27
public class GoogleMapActivity extends Activity {

	// Define default latitude and longitude for user (University of Alberta, Edmonton, CA)
	static final LatLng edmontonLatLng = new LatLng(53.523218900000, -113.526318599999970);
	private GoogleMap googleMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);
		
		// initialze googleMap
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
            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(edmontonLatLng).title("University of Alberta"));
            
            // Move camera to edmonton location and zoom in
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edmontonLatLng, 15));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            
		} catch (Exception e) {
                e.printStackTrace();
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
}
