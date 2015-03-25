/*
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

import java.util.UUID;

/**
 * 
 * Tag is only composed of a string for now 
 *
 */

public class Tag {

	private String tagName;
	private String tagID;
	
	
	public Tag(String name) {
		this.tagName = name;
		this.tagID = UUID.randomUUID().toString();
	}
	
	

	public String getTagName() {
		return tagName;
	}
	
	
	
	public void setTagName(String name) {
		this.tagName = name;
	}
	
	
	
	

	public String getTagID() {
		return tagID;
	}



	public void setTagID(String tagID) {
		this.tagID = tagID;
	}



	// Converts the tag into a string representation to make user of simple list adapters
	@Override
	public String toString() {
		return tagName;
	}
	
	
	/**
	 * Compares two tags by checking if they have the same name
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Tag) {
			Tag tocompare = (Tag) o;
			if (tocompare.getTagID().equals(this.getTagID())) {
				return true;
			} else {
				return false;
			}
		}
		return super.equals(o);
	}
	
	
	
}
