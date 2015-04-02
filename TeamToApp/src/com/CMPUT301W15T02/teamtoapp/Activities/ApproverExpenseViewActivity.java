package com.CMPUT301W15T02.teamtoapp.Activities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.CMPUT301W15T02.teamtoapp.R;
import com.CMPUT301W15T02.teamtoapp.Controllers.ExpenseController;
import com.CMPUT301W15T02.teamtoapp.Model.ApproverClaims;
import com.CMPUT301W15T02.teamtoapp.Model.Expense;
import com.CMPUT301W15T02.teamtoapp.R.id;
import com.CMPUT301W15T02.teamtoapp.R.layout;
import com.CMPUT301W15T02.teamtoapp.R.menu;

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
		getModelObjects();
		findViewsByIds();
		setUpAdapters();
		setFieldValues();
		setListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_expense_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
		expenseID = (String) intent.getSerializableExtra("expenseID");
		expense = ApproverClaims.getInstance().findExpenseByID(expenseID);
		
	}
	
	/**
	 * Finds all of the associated views
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
	 * Sets up the adapters
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
		if (expense.getPhoto() == null) {
			receiptImageButton.setVisibility(View.GONE);
		}
	}
	
	private void setListeners() {
		receiptImageButton.setOnClickListener(new View.OnClickListener() {
			// http://stackoverflow.com/questions/7693633/android-image-dialog-popup
			@Override
			public void onClick(View v) {
				if (expense.getPhoto() != null) {
					showImage();
				}
			}
				
			
		});
	}
	
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
	    if (expense.getPhoto() != null) {
			byte[] decodedString = Base64.decode(expense.getPhoto(), Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			Drawable drawable = new BitmapDrawable(getResources(),bitmap);
			imageView.setImageDrawable(drawable);
		builder.addContentView(imageView, new RelativeLayout.LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT, 
	            ViewGroup.LayoutParams.MATCH_PARENT));
	    builder.show();
	    }
	}
}
