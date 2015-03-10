package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TagManagerActivity extends Activity {

	private UserController userController;
	private ArrayList<Tag> tags;
	private ArrayAdapter<Tag> adapter;
	
	private ListView tagsListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_manager);
		getModelObjects();
		findViewsByIds();
		setUpAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.managerAddTagButton) {
			// TODO Add dialog to add tag
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void getModelObjects() {
		userController = new UserController();
		tags = userController.getTags();
	}
	
	private void findViewsByIds() {
		tagsListView = (ListView) findViewById(R.id.tagManagerListView);
	}
	
	private void setUpAdapter() {
		adapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, tags);
		tagsListView.setAdapter(adapter);
	}
	
	private void setListeners() {
		// TODO Add onclick listener to edit tag
		// TODO Add long click listener to delete tag
	}
}
