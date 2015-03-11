package com.CMPUT301W15T02.teamtoapp;

import java.net.InetAddress;


public class SessionController {
	
	private User user;
	private ClaimList claims;
	
	public SessionController() {
	}

	//Checks connection to elastic search server
	//Source: http://stackoverflow.com/a/9570292 2015-03-10
	public static boolean isOnline() {
        try {
            InetAddress ip = InetAddress.getByName("cmput301.softwareprocess.es:8080/cmput301w15t02/");

            if (ip.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }
}