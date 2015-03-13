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

package com.CMPUT301W15T02.teamtoapp.Controllers;

import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;
import com.CMPUT301W15T02.teamtoapp.Model.ClaimList;
import com.CMPUT301W15T02.teamtoapp.Model.Session;
import com.CMPUT301W15T02.teamtoapp.Model.User;

import android.R.integer;

public class ClaimListController {

	private ClaimList claims;
	private User user;
	
	public ClaimListController() {
		claims = Session.getInstance().getCurrentClaims();
		user = Session.getInstance().getCurrentUser();
	}
	
	public void addClaim(Claim claim) {
		claim.setUser(user.getName());
		claims.addClaim(claim);
	}
	
	public void removeClaim(Claim claim) {
		claims.removeClaim(claim);
	}
	
	public Claim getClaim(int position) {
		return claims.getClaims().get(position);
	}

	public ArrayList<Claim> getClaims() {
		return claims.getClaims();
	}
	
}
