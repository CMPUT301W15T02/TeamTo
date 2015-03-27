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
import com.CMPUT301W15T02.teamtoapp.Commands.Command;
import com.CMPUT301W15T02.teamtoapp.Commands.addCommand;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.R.integer;
import android.content.Context;

public class Cache {

	
	private static final String CLAIMFILE = "claims.sav";
	private static Cache instance = null;
	private ArrayList<Command> commands;

	
	
	private Cache() {
		commands = new ArrayList<Command>();
	}
	
	public static Cache getInstance() {
		if (instance == null) {
			instance = new Cache();
		}
		return instance;
	}
	
	public void addCommandToCache(Command command) {
		commands.add(command);
	}
	
	public void removeCommandFromCache(Command command) {
		commands.remove(command);
	}
	
	/*public void saveCache(Context context) {
		context = context.getApplicationContext();
		Gson gson = new Gson();
		try {
			FileOutputStream fos = context.openFileOutput(CLAIMFILE,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(ClaimList.getInstance().getClaims(), osw);
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	
}
