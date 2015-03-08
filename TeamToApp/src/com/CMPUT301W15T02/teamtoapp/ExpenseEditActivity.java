package com.CMPUT301W15T02.teamtoapp;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
		controller.addObserverToExpense(this);
		
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
		updateValues();
		if (controller.getAmount().equals(0.0)) {
			amountEditText.setHint(df.format(controller.getAmount()));
		} else {
			amountEditText.setText(df.format(controller.getAmount()));
		}
		if (controller.getDescription().equals("")) {
			descriptionEditText.setHint("Enter a description");
		} else {
			descriptionEditText.setText(controller.getDescription());
		}
	}
	
	
	private void updateValues() {
		dateTextView.setText(formatter.format(controller.getDate().getTime()));
		currencySpinner.setSelection(currencyAdapter.getPosition(controller.getCurrency().toString()));
		categorySpinner.setSelection(categoriesAdapter.getPosition(controller.getCategory()));
		if (controller.isComplete()) {
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
		
		Calendar date = controller.getDate();
		
		datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				controller.setDate(calendar);
			}
			
		}, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
		
		currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				controller.setCurrency(Currency.getInstance(parent.getItemAtPosition(position).toString()));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				controller.setCategory(parent.getItemAtPosition(position).toString());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		amountEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				double amount;
				try {
                	amount = Double.parseDouble(s.toString());
                } catch (NumberFormatException nfe) {
                	amount = 0.0;
                }
				controller.setAmount(amount);
				
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
				controller.setDescription(s.toString());
			}
		});
	}

	@Override
	public void update(Observable observable, Object data) {
		updateValues();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		controller.removeObserverFromExpense(this);
	}
	
	
}
