package com.CMPUT301W15T02.teamtoapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TagManagerActivity extends Activity implements Observer {

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
		setListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tag_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.managerAddTagButton) {
			// TODO Add dialog to add tag
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void getModelObjects() {
		userController = new UserController();
		userController.addObserverToUser(this);
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
		tagsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				LayoutInflater inflater = LayoutInflater.from(getBaseContext());
				View editDeleteTagView = inflater.inflate(R.layout.edit_delete_tag_dialog, null);
				final EditText tagNameEditText = (EditText) editDeleteTagView.findViewById(R.id.tagNameEditText);
				tagNameEditText.setText(userController.getTagText(position));
				
				AlertDialog.Builder builder = new AlertDialog.Builder(TagManagerActivity.this);
				builder.setView(editDeleteTagView);
				builder.setPositiveButton("Save", new DialogInterface.OnClickListener () {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						String tagName = tagNameEditText.getText().toString().trim();
						String tagID = userController.getTagId(position);
						userController.renameTag(tagID, tagName);
					}
				})
				.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						userController.removeTag(position);
						
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// Do nothing
					}
				});
				
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				
			}
		});
	}

	@Override
	public void update(Observable observable, Object data) {
		adapter.notifyDataSetChanged();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		userController.removeObserverFromUser(this);
	}
	
	
}
