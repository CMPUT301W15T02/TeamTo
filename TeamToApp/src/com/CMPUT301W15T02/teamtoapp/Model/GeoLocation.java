package com.CMPUT301W15T02.teamtoapp.Model;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

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
	
	private double latitude;
	private double longitude;
	private String locationName = "None";

	public GeoLocation() {
	}

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	


	/**
	 * Grab city name from longitude and latitude values.
	 * @param Context context
	 * @return  String cityName
	 * 
	 * TODO: Needs testing. Context is definitely required, don't know if we need other parameters later...
	 */
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
