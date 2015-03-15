/* Copyright 2015 Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
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

package com.CMPUT301W15T02.teamtoapp.Utilities;

import java.util.Comparator;

import com.CMPUT301W15T02.teamtoapp.Model.Claim;


/**
 * 
 * Comparator class used to sort claims in order of start date with the oldest claims first
 *
 */


public class ClaimComparatorOldestFirst implements Comparator<Claim> {

	@Override
	public int compare(Claim lhs, Claim rhs) {
		return lhs.getStartDate().compareTo(rhs.getStartDate());
	}

}
