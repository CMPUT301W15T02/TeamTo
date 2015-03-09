package com.CMPUT301W15T02.teamtoapp;

import java.net.InetAddress;


public class SessionController {
	
	private User user;
	private ClaimList claims;
	
	public SessionController() {
	}

	public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("cmput301.softwareprocess.es:8080/cmput301w15t02/");

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }
}
