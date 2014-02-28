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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mad.moneySac.R;
import com.mad.moneySac.helpers.reccurring.RecurringBatchCreator;
import com.mad.moneySac.helpers.reccurring.RecurringBatchCreatorFactory;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.CategoryDBHelper;
import com.mad.moneySac.model.RecurringEntry;
import com.mad.moneySac.model.SacEntryDBHelper;

/**
 * Activity zum Erfassen wiederkehrender Eintraege. Dabei dient die Activity nur dem Anlegen solcher Eintraege. Ein Aendern ist nicht moeglich und daher auch
 * in der Activity nicht abgedeckt.
 *
 */
public class RecurringEntryActivity extends Activity {

	private static final String TO_DATE_TAG = "toDatePicker";
	private static final String FROM_DATE_TAG = "fromDatePicker";
	private Spinner categorySpinner;
	private Spinner recurringIntervalSpinner;
	private AutoCompleteTextView descriptionAutoComplete;
	private String type;
	private long fromDateTime;
	private long toDateTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recurring_entry);
		type = getIntent().getStringExtra(MoneySac.TYPE_EXTRA);

		categorySpinner = (Spinner) findViewById(R.id.spinnerRecurringEntryCategory);
		recurringIntervalSpinner = (Spinner) findViewById(R.id.spinnerRecurringEntryInterval);
		descriptionAutoComplete = (AutoCompleteTextView) findViewById(R.id.editTextRecurringEntryDesc);

		loadCategories();
		loadRecurringIntervals();
		initAutoCompleteWithAlreadyUsedDescriptions();
		setFromDateButtonText(Calendar.getInstance());
		setToDateButtonText(Calendar.getInstance());
	}

	/**
	 * Befüllen des AutoComplete-Feldes, typspezifisch, sortiert nach Häufigkeit
	 */
	private void initAutoCompleteWithAlreadyUsedDescriptions() {
		SacEntryDBHelper dbHelper = new SacEntryDBHelper();
		List<String> descriptions = dbHelper.getUsedDescriptionsOrderByUsageDescending(this, type, true);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, descriptions);
		descriptionAutoComplete.setAdapter(adapter);
	}

	private void loadRecurringIntervals() {
		String[] recurringIntervals = getResources().getStringArray(R.array.recurring_intervals);
		recurringIntervalSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, recurringIntervals));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recurring_entry, menu);
		return false;
	}

	private void loadCategories() {
		CategoryDBHelper categoryHelper = new CategoryDBHelper();
		try {
			List<Category> categories = categoryHelper.where(this, "type", type);
			categorySpinner.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item,
					categories));
		} catch (SQLException e) {
			Log.e("RECURRING", "Konnte die Kategorien nicht laden! ",e);
		}
	}

	public void changeFromDateClicked(View v) {
		DialogFragment newFragment = new FromDatePickerFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		newFragment.show(getFragmentManager(), FROM_DATE_TAG);
	}

	public void changeToDateClicked(View v) {
		DialogFragment newFragment = new ToDatePickerFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		newFragment.show(getFragmentManager(), TO_DATE_TAG);
	}

	private void setFromDateButtonText(Calendar c) {
		Button dateButton = (Button) findViewById(R.id.buttonRecurringEntryStartDate);
		fromDateTime = c.getTimeInMillis();
		String date = c.get(Calendar.DAY_OF_MONTH) + "." + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
		dateButton.setText(date);
	}

	private void setFromDateButtonText(String date) {
		Button dateButton = (Button) findViewById(R.id.buttonRecurringEntryStartDate);
		dateButton.setText(date);
	}

	public void setFromDateTime(long currentDateInMillis) {
		this.fromDateTime = currentDateInMillis;
	}

	private void setToDateButtonText(Calendar c) {
		Button dateButton = (Button) findViewById(R.id.buttonRecurringEntryFinishDate);
		toDateTime = c.getTimeInMillis();
		String date = c.get(Calendar.DAY_OF_MONTH) + "." + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
		dateButton.setText(date);
	}

	private void setToDateButtonText(String date) {
		Button dateButton = (Button) findViewById(R.id.buttonRecurringEntryFinishDate);
		dateButton.setText(date);
	}

	public void setToDateTime(long currentDateInMillis) {
		this.toDateTime = currentDateInMillis;
	}

	public void persistClicked(View v) {
		String description = ((EditText) findViewById(R.id.editTextRecurringEntryDesc)).getText().toString();
		String amount = ((EditText) findViewById(R.id.editTextRecurringEntryAmount)).getText().toString();
		Category category = (Category) categorySpinner.getSelectedItem();
		String interval = (String) recurringIntervalSpinner.getSelectedItem();

		if (checkSacEntryValues(description, category, amount)) {
			RecurringEntry entry = new RecurringEntry(description, Double.parseDouble(amount), category, fromDateTime, toDateTime, type);
			RecurringBatchCreator creator = RecurringBatchCreatorFactory.getCreatorForInterval(interval, getResources());
			try {
				creator.createSacEntries(this, entry);
			} catch (SQLException e) {
				Log.e("RECURRING", getString(R.string.saving_recurring_entries_not_possible), e);
				Toast.makeText(this, R.string.saving_recurring_entries_not_possible, Toast.LENGTH_LONG).show();;
			}
			this.finish();
		}
	}

	private boolean checkSacEntryValues(String desc, Category category, String amount) {
		if (desc == null || desc.trim().isEmpty()) {
			Toast.makeText(this, R.string.desc_is_required, Toast.LENGTH_LONG).show();
			return false;
		}
		if (category == null) {
			Toast.makeText(this, R.string.no_valid_category_selected, Toast.LENGTH_LONG).show();
			return false;
		}
		if (amount == null || amount.trim().isEmpty()) {
			Toast.makeText(this, R.string.amount_is_required, Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	// =================== DATE PICKER =======================

	public static class FromDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {

			((RecurringEntryActivity) getActivity()).setFromDateButtonText(day + "." + (month + 1) + "." + year);
			final Calendar c = Calendar.getInstance();
			c.set(year, month, day);
			((RecurringEntryActivity) getActivity()).setFromDateTime(c.getTimeInMillis());
		}
	}

	public static class ToDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {

			((RecurringEntryActivity) getActivity()).setToDateButtonText(day + "." + (month + 1) + "." + year);
			final Calendar c = Calendar.getInstance();
			c.set(year, month, day);
			((RecurringEntryActivity) getActivity()).setToDateTime(c.getTimeInMillis());
		}
	}
}
