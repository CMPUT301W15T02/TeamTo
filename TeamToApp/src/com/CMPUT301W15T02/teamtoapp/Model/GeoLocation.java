/* 
 * 
 * Copyright 2015 Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
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

package com.CMPUT301W15T02.teamtoapp.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;

/**
 * 
 * GeoLocation.java is an object with latitude and longitude.
 * It can also grab a city name based on the latitude and longitude values.
 * City name can also be set manually by the user.
 * 
 * Sources: http://developer.android.com/reference/android/location/Geocoder.html      2015-03-25
 * 			http://stackoverflow.com/questions/2296377/how-to-get-city-name-from-latitude-and-longitude
 * 			-coordinates-in-google-maps      2015-03-25
 * 
 */

public class GeoLocation {
	
	private double latitude = 0.0;
	private double longitude = 0.0;
	private String locationName = "None";
	protected transient ArrayList<Listener> listeners = null;

	public GeoLocation() {
		listeners = new ArrayList<Listener>();
	}

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		listeners = new ArrayList<Listener>();
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
		notifyListeners();
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
		notifyListeners();
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
		notifyListeners();
	}
	
	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
	
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	
	public void removeListener(Listener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * Grab city name from longitude and latitude values.
	 * @param Context context
	 * @return  String cityName
	 * 
	 * TODO: Needs testing. Context is definitely required, don't know if we need other parameters later...
	 */
	
	
	// Need this for gps! Going to add gps feature in GoogleMapActivity.java
	public String getCityNameFromLocation(Context context) {
		String cityName = null;
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = geocoder.getFromLocation(getLatitude(), getLongitude(), 1);
			if (addresses.size() > 0)
				cityName = addresses.get(0).getLocality();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cityName;
	}
}
