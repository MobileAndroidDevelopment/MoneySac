package com.mad.moneySac.activities;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.mad.moneySac.R;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.CategoryDBHelper;
import com.mad.moneySac.model.SacEntryType;

public class RecurringEntryActivity extends Activity {

	private Spinner categorySpinner;
	private Spinner recurringTypeSpinner;
	private String type;
	private long fromDateTime;
	private long toDateTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recurring_entry);
		type = getIntent().getStringExtra(MoneySac.TYPE_EXTRA);
		if (type.equals(SacEntryType.INCOME)) {
			setTitle("Neue wiederkehrende Einnahme");
		} else {
			setTitle("Neue wiederkehrende Ausgabe");
		}

		loadCategories();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recurring_entry, menu);
		return true;
	}

	private void loadCategories() {
		CategoryDBHelper categoryHelper = new CategoryDBHelper();
		try {
			List<Category> categories = categoryHelper.where(this, "type", type);
			categorySpinner.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categories));
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			((RecurringEntryActivity) getActivity()).setDateButtonText(day + "." + (month + 1) + "." + year);
			final Calendar c = Calendar.getInstance();
			c.set(year, month, day);
			((RecurringEntryActivity) getActivity()).setFromDateTime(c.getTimeInMillis());
		}
	}

	private void setFromDateButtonText(Calendar c) {
		Button dateButton = (Button) findViewById(R.id.buttonRecurringEntryStartDate);
		fromDateTime = c.getTimeInMillis();
		dateButton.setText(c.get(Calendar.DAY_OF_MONTH) + "." + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR));
	}
	
	private void setDateButtonText(String date) {
		Button dateButton = (Button) findViewById(R.id.buttonEntryDate);
		dateButton.setText(date);
	}

	public void setFromDateTime(long currentDateInMillis) {
		this.fromDateTime = currentDateInMillis;
	}
}
