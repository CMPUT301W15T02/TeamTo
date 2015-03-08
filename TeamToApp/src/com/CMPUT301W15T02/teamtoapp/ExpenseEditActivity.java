package com.CMPUT301W15T02.teamtoapp;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ExpenseEditActivity extends Activity implements Observer {
	
	private ExpenseController controller;
	private String expenseID;
	private Expense currentExpense;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#0.00");
	
	private TextView dateTextView;
	private EditText amountEditText;
	private Spinner currencySpinner;
	private Spinner categorySpinner;
	private EditText descriptionEditText;
	private ImageView receiptImageView;
	private RadioButton completedRadioButton;
	
	private ArrayAdapter<CharSequence> currencyAdapter;
	private ArrayAdapter<CharSequence> categoriesAdapter;
	
	private DatePickerDialog datePickerDialog;
	

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_expense, menu);
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
	
	private void getModelObjects() {
		Intent intent = getIntent();
		expenseID = (String) intent.getSerializableExtra("expenseID");
		controller = new ExpenseController(expenseID);
		currentExpense = controller.getExpense();
		currentExpense.addObserver(this);
		
	}
	
	private void findViewsByIds() {
		dateTextView = (TextView) findViewById(R.id.ExpenseDateTextView);
		amountEditText = (EditText) findViewById(R.id.ExpenseAmountEditText);
		currencySpinner = (Spinner) findViewById(R.id.CurrencyExpenseSpinner);
		categorySpinner  = (Spinner) findViewById(R.id.CategoryExpenseSpinner);
		descriptionEditText = (EditText) findViewById(R.id.ExpenseDescriptionEditText);
		receiptImageView = (ImageView) findViewById(R.id.ExpenseImageView);
		completedRadioButton = (RadioButton) findViewById(R.id.ExpenseCompletedRadioButton);
	}
	
	private void setUpAdapters() {
		currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currency_string,
				android.R.layout.simple_spinner_dropdown_item);
		currencySpinner.setAdapter(currencyAdapter);
		categoriesAdapter = ArrayAdapter.createFromResource(this, R.array.categories_string,
				android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(categoriesAdapter);
	}
	
	private void setFieldValues() {
		dateTextView.setText(formatter.format(currentExpense.getDate().getTime()));
		amountEditText.setText(df.format(currentExpense.getAmount()));
		currencySpinner.setSelection(currencyAdapter.getPosition(currentExpense.getCurrency().toString()));
		categorySpinner.setSelection(categoriesAdapter.getPosition(currentExpense.getCategory()));
		
		if (currentExpense.getDescription().equals("")) {
			descriptionEditText.setHint("Enter a description");
		} else {
			descriptionEditText.setText(currentExpense.getDescription());
		}
		
		if (currentExpense.isComplete()) {
			completedRadioButton.setChecked(true);
		} else {
			completedRadioButton.setChecked(false);
		}
	}
	
	private void setListeners() {
		dateTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datePickerDialog.show();
				
			}
		});
		
		Calendar date = currentExpense.getDate();
		
		datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				controller.setDate(calendar);
			}
			
		}, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void update(Observable observable, Object data) {
		setFieldValues();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		currentExpense.deleteObserver(this);
	}
	
	
}
