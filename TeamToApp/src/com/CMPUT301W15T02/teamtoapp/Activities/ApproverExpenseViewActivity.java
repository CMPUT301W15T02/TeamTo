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
import com.CMPUT301W15T02.teamtoapp.Model.ApproverClaims;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;

/**
 * 
 * Activity to allow the approver to look at more information of a specific expense
 *
 * @author Kyle Carlstrom
 * 
 */

public class ApproverExpenseViewActivity extends Activity {

	private Expense expense;
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
		setContentView(R.layout.activity_approver_expense_view);
		
		/* Set up all IDs, model objects, listeners, 
		and approver expense list adapter */
		getModelObjects();
		findViewsByIds();
		setUpAdapters();
		setFieldValues();
		setListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.approver_expense_view, menu);
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
		// Obtain expense ID from intent
		expenseID = (String) intent.getSerializableExtra("expenseID");
		
		// Obtain expense based on expense ID from list of approved claims
		expense = ApproverClaims.getInstance().findExpenseByID(expenseID);
		
	}
	
	/**
	 * Finds all of the associated views and assign them
	 */
	private void findViewsByIds() {
		dateTextView = (TextView) findViewById(R.id.ApproverExpenseDateTextView);
		amountEditText = (EditText) findViewById(R.id.ApproverExpenseAmountEditText);
		currencySpinner = (Spinner) findViewById(R.id.ApproverCurrencyExpenseSpinner);
		categorySpinner  = (Spinner) findViewById(R.id.ApproverCategoryExpenseSpinner);
		descriptionEditText = (EditText) findViewById(R.id.ApproverExpenseDescriptionEditText);
		receiptImageButton = (Button) findViewById(R.id.ApproverExpenseImageButton);
	}
	
	/**
	 * Sets up the adapters for currency and category spinners
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
	 * Sets the value of the different fields according to the values of the expense
	 */
	private void setFieldValues() {
		dateTextView.setText(formatter.format(expense.getDate().getTime()));
		currencySpinner.setSelection(currencyAdapter.getPosition(expense.getCurrency().toString()));
		categorySpinner.setSelection(categoriesAdapter.getPosition(expense.getCategory()));
		amountEditText.setText(df.format(expense.getAmount()));
		descriptionEditText.setText(expense.getDescription());
		
		// If no photo exists, do not show receiptImageButton
		if (expense.getPhoto() == null) {
			receiptImageButton.setVisibility(View.GONE);
		}
	}
	
	/**
	 * Sets listener when receipt button pressed
	 * @see // http://stackoverflow.com/questions/7693633/android-image-dialog-popup
	 */
	private void setListeners() {
		receiptImageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (expense.getPhoto() != null) {
					showImage();
				}
			}
				
			
		});
	}
	
	/**
	 * This method is called when the receipt button is clicked and a photo exists.
	 * It allows a photo to be viewed by the user.
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
	    // Create ImageView
	    ImageView imageView = new ImageView(this);
	    
	    if (expense.getPhoto() != null) {
	    	// If photo exists, decode from string format
			byte[] decodedString = Base64.decode(expense.getPhoto(), Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			
			// Create and set image drawable for bitmap
			Drawable drawable = new BitmapDrawable(getResources(),bitmap);
			imageView.setImageDrawable(drawable);
			
			// Add image view to the dialog builder and show image
			builder.addContentView(imageView, new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 
					ViewGroup.LayoutParams.MATCH_PARENT));
			builder.show();
	    }
	}
}
