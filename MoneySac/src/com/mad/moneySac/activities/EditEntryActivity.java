package com.mad.moneySac.activities;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.mad.moneySac.R;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.CategoryDBHelper;
import com.mad.moneySac.model.SacEntry;
import com.mad.moneySac.model.SacEntryDBHelper;
import com.mad.moneySac.model.SacEntryType;

public class EditEntryActivity extends Activity {
	
	private Spinner categorySpinner;
	private long currentDateInMillis;
	private String type;
	private SacEntry sacEntry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_entry);
		setTitle("Neue Ausgabe");

		getExtrasFromBundle();
		loadCategories();
	}

	private void loadCategories() {
		CategoryDBHelper categoryHelper = new CategoryDBHelper();
		try {
			List<Category> categories = categoryHelper.where(this, "type", type);
			categorySpinner = (Spinner) findViewById(R.id.spinnerEntryCategory);
			categorySpinner.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categories));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getExtrasFromBundle() {
		Calendar calendar = Calendar.getInstance();
		if(getIntent().getExtras().containsKey("")){
			sacEntry = (SacEntry)getIntent().getExtras().get("");
			type = sacEntry.getType();
			((EditText) findViewById(R.id.editTextEntryAmount)).setText(sacEntry.getAmount()+"");
			((EditText) findViewById(R.id.editTextEntryDesc)).setText(sacEntry.getDescription()+"");
			calendar.setTimeInMillis(sacEntry.getDateTime());

			@SuppressWarnings("unchecked")
			ArrayAdapter<Category> spinnerAdapter = (ArrayAdapter<Category>) categorySpinner.getAdapter();
			int position = spinnerAdapter.getPosition(sacEntry.getCategory());
			categorySpinner.setSelection(position);
		} else {
			type = getIntent().getStringExtra(MoneySac.TYPE_EXTRA);
		}
		
		setDateButtonText(calendar);
		((Button) findViewById(R.id.buttonAddEntry)).setText(SacEntryType.getButtonTextForEntryType(type));
	}

	private void setDateButtonText(Calendar c) {
		Button dateButton = (Button) findViewById(R.id.buttonEntryDate);
		currentDateInMillis = c.getTimeInMillis();
		dateButton.setText(c.get(Calendar.DAY_OF_MONTH) + "." + (c.get(Calendar.MONTH)+1) + "." + c.get(Calendar.YEAR));
	}
	
	private void setDateButtonText(String date) {
		Button dateButton = (Button) findViewById(R.id.buttonEntryDate);
		dateButton.setText(date);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_entry, menu);
		return true;
	}
	
	public void persistClicked(View v){
		String desc = ((EditText)findViewById(R.id.editTextEntryDesc)).getText().toString();
		double amount = Double.parseDouble(((EditText)findViewById(R.id.editTextEntryAmount)).getText().toString());
		Category category = (Category)((Spinner)findViewById(R.id.spinnerEntryCategory)).getSelectedItem();
		long date = currentDateInMillis;

		persist(desc, amount, category, date);
	}
	
	private void persist(String desc, double amount, Category category, long date) {
		if (sacEntry == null) {
			sacEntry = new SacEntry();
			sacEntry.setAmount(amount);
			sacEntry.setCategory(category);
			sacEntry.setDescription(desc);
			sacEntry.setDateTime(date);
			sacEntry.setType(type);
		}
		Log.d("zu speicherndes objekt", sacEntry.toString());
		SacEntryDBHelper helper = new SacEntryDBHelper();
		try {
			helper.createOrUpdate(this, sacEntry);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		helper.close();
		this.finish();
	}

	public void changeDateClicked(View v){
		DialogFragment newFragment = new DatePickerFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void setCurrentMillis(long currentDateInMillis){
		this.currentDateInMillis = currentDateInMillis;
	}
	
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			((EditEntryActivity)getActivity()).setDateButtonText(day+"."+(month+1)+"."+year);
			final Calendar c = Calendar.getInstance();
			c.set(year, month, day);
			((EditEntryActivity)getActivity()).setCurrentMillis(c.getTimeInMillis());
		}
	}

}
