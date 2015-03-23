package com.CMPUT301W15T02.teamtoapp.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.ElasticSearchManager;
import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.R.integer;
import android.content.Context;

public class Cache {

	private static Cache instance = null;
	private Context context;
	
	private static String UPDATE_FILE_NAME = "update_claims_cache_file.txt";
	private static String REMOVE_FILE_NAME = "remove_claims_cache_file.txt";
	
	private ArrayList<Claim> claimsToUpdate;
	private ArrayList<Claim> claimsToRemove; 
	
	
	private Cache() {
		claimsToUpdate = new ArrayList<Claim>();
		claimsToRemove = new ArrayList<Claim>(); 
	}
	
	public static Cache getInstance() {
		if (instance == null) {
			instance = new Cache();
		}
		return instance;
	}
	
	public void updateClaimsFromCache(final Context context) {

		ElasticSearchManager.initializeContext(context.getApplicationContext());
		for (int i = 0; i < claimsToUpdate.size(); i ++) {
			Claim claim = new Claim();
			claim = claimsToUpdate.get(i);
			ElasticSearchManager.addClaim(claim);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < claimsToRemove.size(); i ++) {
			Claim claim = new Claim();
			claim = claimsToRemove.get(i);
			ElasticSearchManager.deleteClaim(claim.getClaimId());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean isCacheEmpty() {
		return ((claimsToRemove.size() + claimsToUpdate.size()) == 0);
	}
	
	public void addRemovalToCache(Claim claim, Context context) {
		claimsToRemove.add(claim);
		saveCacheToDisk(context.getApplicationContext());
	}
	
	
	public void addUpdateToCache(Claim claim, Context context) {
		claimsToUpdate.add(claim);
		saveCacheToDisk(context.getApplicationContext());
	}
	
	
	public void saveCacheToDisk(Context context) {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = context.openFileOutput(UPDATE_FILE_NAME,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claimsToUpdate, osw);
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream fos = context.openFileOutput(REMOVE_FILE_NAME,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claimsToRemove, osw);
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
