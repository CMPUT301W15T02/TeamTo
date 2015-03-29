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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Files;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.CMPUT301W15T02.teamtoapp.MainManager;
import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Controllers.ClaimController;
import com.CMPUT301W15T02.teamtoapp.Controllers.ExpenseController;
import com.CMPUT301W15T02.teamtoapp.Interfaces.Listener;

/**
 * 
 * Activity for editing an expense
 *
 */
public class ExpenseEditActivity extends Activity implements Listener {
	
	private ExpenseController expenseController;
	private String expenseID;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#0.00");
	private TextView dateTextView;
	private EditText amountEditText;
	private Spinner currencySpinner;
	private Spinner categorySpinner;
	private EditText descriptionEditText;
	private ImageButton receiptImageButton;
	private CheckBox completedCheckBox;
	Uri imageFileUri;
	
	private ArrayAdapter<CharSequence> currencyAdapter;
	private ArrayAdapter<CharSequence> categoriesAdapter;
	
	private DatePickerDialog datePickerDialog;
	private String claimID;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_edit_activity);
		getModelObjects();
		findViewsByIds();
		setUpAdapters();
		setFieldValues();
		setListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.save_new_expense) {
			onSaveExpenseButtonClick();
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Gets the model objects that are needed (expense) and any controllers, also binds the observers to the model
	 */
	private void getModelObjects() {
		Intent intent = getIntent();
		expenseID = (String) intent.getSerializableExtra("expenseID");
		claimID = (String) intent.getSerializableExtra("claimID");
		expenseController = new ExpenseController(expenseID);
		expenseController.addListenerToExpense(this);
		
	}
	
	/**
	 * Finds all of the views that are needed in this activity
	 */
	private void findViewsByIds() {
		dateTextView = (TextView) findViewById(R.id.ExpenseDateTextView);
		amountEditText = (EditText) findViewById(R.id.ExpenseAmountEditText);
		currencySpinner = (Spinner) findViewById(R.id.CurrencyExpenseSpinner);
		categorySpinner  = (Spinner) findViewById(R.id.CategoryExpenseSpinner);
		descriptionEditText = (EditText) findViewById(R.id.ExpenseDescriptionEditText);
		receiptImageButton = (ImageButton) findViewById(R.id.ExpenseImageButton);
		completedCheckBox = (CheckBox) findViewById(R.id.ExpenseCompletedCheckBox);
	}
	
	/**
	 * Sets up both the currency spinner and the category spinner
	 */
	private void setUpAdapters() {
		currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currency_string,
				android.R.layout.simple_spinner_dropdown_item);
		currencySpinner.setAdapter(currencyAdapter);
		categoriesAdapter = ArrayAdapter.createFromResource(this, R.array.categories_string,
				android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(categoriesAdapter);
	}
	
	/**
	 * Sets the default values of the fields, also calls updateValues on fields that will be updated through mvc
	 */
	private void setFieldValues() {
		updateValues();
		// If there is no amount then set a hint, otherwise set the amount
		if (expenseController.getAmount().equals(0.0)) {
			amountEditText.setHint(df.format(expenseController.getAmount()));
		} else {
			amountEditText.setText(df.format(expenseController.getAmount()));
		}
		// If there is no description then set a hint, otherwise set the description
		if (expenseController.getDescription().equals("")) {
			descriptionEditText.setHint("Enter a description");
		} else {
			descriptionEditText.setText(expenseController.getDescription());
		}
	}
	
	
	/**
	 * Updates values that will be updated through the model changing (mvc)
	 */
	private void updateValues() {
		dateTextView.setText(formatter.format(expenseController.getDate().getTime()));
		currencySpinner.setSelection(currencyAdapter.getPosition(expenseController.getCurrency().toString()));
		categorySpinner.setSelection(categoriesAdapter.getPosition(expenseController.getCategory()));
		if (expenseController.isComplete()) {
			completedCheckBox.setChecked(true);
		} else {
			completedCheckBox.setChecked(false);
		}
	}
	
	/**
	 * Saves the current claim, duplicates functionality of onBackPressed
	 */
	private void onSaveExpenseButtonClick() {
		onBackPressed();
	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO move this to controller
		// Should only update if something has changed
		ClaimController claimController = new ClaimController(claimID);
		claimController.addExpense(expenseController.getExpense());
		MainManager.updateClaim(claimController.getCurrentClaim());
		super.onBackPressed();
	}

	/**
	 * Sets up listeners for the different fields
	 */
	private void setListeners() {
		// Show a datepicker dialog when clicked
		dateTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				datePickerDialog.show();
				
			}
		});
		// Get the default date
		Calendar date = expenseController.getDate();
		datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// When the date is set then set the date of the expense
				Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				expenseController.setDate(calendar);
			}
		// Set the expenses current date as a default
		}, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
		
		// Set the correct currency when selected
		currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				expenseController.setCurrency(Currency.getInstance(parent.getItemAtPosition(position).toString()));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing
				
			}
		});
		
		// Set the correct category when selected
		categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				expenseController.setCategory(parent.getItemAtPosition(position).toString());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing
				
			}
		});
		
		// Get the total amount for the expense
		amountEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				double amount;
				try {
                	amount = Double.parseDouble(s.toString());
                } catch (NumberFormatException nfe) {
                	amount = 0.0;
                }
				expenseController.setAmount(amount);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Intentionally blank
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// Intentionally blank
				
			}
		});
		
		// Get the description
		descriptionEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Intentionally blank
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Intentionally blank
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				expenseController.setDescription(s.toString());
			}
		});
		
		// Check box that is automatically set if the user enters a description and amount
		// But can also be manually changed
		completedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				expenseController.setComplete(isChecked);
			}
		});
		
		receiptImageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				takeAPhoto();
			}
		});
	}
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	public void takeAPhoto() {

		// Create a folder to store pictures
		String folder = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/tmp";
		File folderF = new File(folder);
		if (!folderF.exists()) {
			folderF.mkdir();
		}

		// Create an URI for the picture file
		String imageFilePath = folder + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg";
		File imageFile = new File(imageFilePath);
		imageFileUri = Uri.fromFile(imageFile);
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
	    	Drawable photo = Drawable.createFromPath(imageFileUri.getPath());
	    	Bitmap bitmap = ((BitmapDrawable) photo).getBitmap();
	    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
	    	bitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
	    	byte[] byteArray = byteArrayOutputStream.toByteArray();
	    	if (byteArray.length > 100000) {
	    		Toast.makeText(getApplicationContext(), "Image too large", Toast.LENGTH_SHORT).show();
	    	} else {
	    		expenseController.addPhoto(Base64.encodeToString(byteArray, Base64.DEFAULT));
	    	}
	    	// Scale it to 50 x 50
	    	Drawable d = new BitmapDrawable(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
	    	//Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 250, 250, true));
	    	receiptImageButton.setImageDrawable(d);
	    } else {
	    	Toast.makeText(getApplicationContext(), "Something is not working", Toast.LENGTH_SHORT).show();
	    }
	}

	
	/**
	 * Called when the activity is destroyed, it then removes this activity as an observer on the expense
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		expenseController.removeListenerFromExpense(this);
	}

	
	/**
	 * Called when something in the model changes, it then calls update values
	 * @param observable	the expense that changes
	 * @param data
	 */
	@Override
	public void update() {
		updateValues();
		
	}
	
	
}
