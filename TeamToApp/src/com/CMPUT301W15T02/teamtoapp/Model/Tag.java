/* Tags class that gives each user the ability to add their personal tags and use other user 
 * made tags, as well as edit, and delete their tag own tags.
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

/**
 * 
 * Tag is a simple object that contains only a string for now
 *
 */

public class Tag {

	private String tagName;
	
	public Tag(String name) {
		this.tagName = name;
	}

	public String getTagName() {
		return tagName;
	}
	
	public void setTagName(String name) {
		this.tagName = name;
	}

	@Override
	public String toString() {
		return tagName;
	}
	
}
