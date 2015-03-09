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

package com.CMPUT301W15T02.teamtoapp;

import java.util.UUID;

public class Tag {

	private String tagText;
	private String tagId;
	
	public Tag(String tag) {
		this.tagText = tag;
		this.tagId = UUID.randomUUID().toString();
	}

	public String getTagText() {
		return tagText;
	}

	public void setTagText(String tag) {
		this.tagText = tag;
	}

	public String getTagId() {
		return tagId;
	}
	
}
