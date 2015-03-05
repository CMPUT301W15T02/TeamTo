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
