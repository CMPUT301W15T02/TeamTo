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

package com.CMPUT301W15T02.teamtoapp.Activities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Controllers.ExpenseController;

/**
 * 
 * Non-editable activity for viewing the expense, will have the ability to preview picture when photos are added
 *
 * @author Kyle Carlstrom 
 */

public class ExpenseViewActivity extends Activity {
	
	private ExpenseController expenseController;
	private String expenseID;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#0.00");
	private TextView dateTextView;
	private EditText amountEditText;
	private Spinner currencySpinner;
	private Spinner categorySpinner;
	private EditText descriptionEditText;
	private Button receiptImageButton;
	
	private ArrayAdapter<CharSequence> currencyAdapter;
	private ArrayAdapter<CharSequence> categoriesAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense_view);
		/* Set up all model objects, widgets, field values, 
		 * and listeners
		 */
		getModelObjects();
		findViewsByIds();
		setUpAdapters();
		setFieldValues();
		setListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.expense_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Gets the associated expense and sets the expense controller
	 * Don't have to worry about observers because no edits can be made
	 */
	private void getModelObjects() {
		Intent intent = getIntent();
		// Obtain expenseID from intent and save in expense controller
		expenseID = (String) intent.getSerializableExtra("expenseID");
		expenseController = new ExpenseController(expenseID);
		
	}
	
	/**
	 * Finds all of the associated views
	 */
	private void findViewsByIds() {
		dateTextView = (TextView) findViewById(R.id.ExpenseDateTextView);
		amountEditText = (EditText) findViewById(R.id.ExpenseAmountEditText);
		currencySpinner = (Spinner) findViewById(R.id.CurrencyExpenseSpinner);
		categorySpinner  = (Spinner) findViewById(R.id.CategoryExpenseSpinner);
		descriptionEditText = (EditText) findViewById(R.id.ExpenseDescriptionEditText);
		receiptImageButton = (Button) findViewById(R.id.ExpenseImageButton);
	}
	
	/**
	 * Sets up the adapters
	 */
	private void setUpAdapters() {
		// Set up currency and category adapters
		currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currency_string,
				android.R.layout.simple_spinner_dropdown_item);
		currencySpinner.setAdapter(currencyAdapter);
		categoriesAdapter = ArrayAdapter.createFromResource(this, R.array.categories_string,
				android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(categoriesAdapter);
	}
	
	
	/**
	 * Sets the value of the different fields according to the values of the expense
	 */
	private void setFieldValues() {
		dateTextView.setText(formatter.format(expenseController.getDate().getTime()));
		currencySpinner.setSelection(currencyAdapter.getPosition(expenseController.getCurrency().toString()));
		categorySpinner.setSelection(categoriesAdapter.getPosition(expenseController.getCategory()));
		amountEditText.setText(df.format(expenseController.getAmount()));
		descriptionEditText.setText(expenseController.getDescription());
		if (expenseController.getPhoto() == null) {
			receiptImageButton.setVisibility(View.GONE);
		}
	}
	
	/**
	 * If reciept image button clicked, go to showImage() method
	 * to show avaibale image to user
	 * 
	 * @see // http://stackoverflow.com/questions/7693633/android-image-dialog-popup
	 */
	private void setListeners() {
		receiptImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (expenseController.getPhoto() != null) {
					showImage();
				}
			}
				
			
		});
	}
	
	/**
	 * This method will show an existing image of a photo receipt saved
	 * by the user.
	 */
	public void showImage() {
	    Dialog builder = new Dialog(this);
	    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    builder.getWindow().setBackgroundDrawable(
	        new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	        @Override
	        public void onDismiss(DialogInterface dialogInterface) {
	            //nothing;
	        }
	    });
	    ImageView imageView = new ImageView(this);
	    if (expenseController.getPhoto() != null) {
	    	// If photo exists, decode its string format
			byte[] decodedString = Base64.decode(expenseController.getPhoto(), Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			// Create and set image drawable for image bitmap
			Drawable drawable = new BitmapDrawable(getResources(),bitmap);
			imageView.setImageDrawable(drawable);
			
			// add the content view to builder and show image
			builder.addContentView(imageView, new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 
					ViewGroup.LayoutParams.MATCH_PARENT));
			builder.show();
	    }
	}
	
	
}
