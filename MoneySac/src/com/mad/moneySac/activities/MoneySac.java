package com.mad.moneySac.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.mad.moneySac.R;
import com.mad.moneySac.adapters.DatePickerFragment;
import com.mad.moneySac.helpers.SegmentedRadioGroup;
import com.mad.moneySac.model.Entry;

public class MoneySac extends Activity {

	public static final String IS_INCOME = "IS_INCOME";
	SimpleDateFormat sdfOut = new SimpleDateFormat("MMMM yyyy", Locale.GERMAN);
	SimpleDateFormat sdfIn = new SimpleDateFormat("yyyyMM", Locale.GERMAN);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moneysac);

		load();
	}

	private void load() {
		ListView listView = loadListView();
		Spinner monthSpinner = loadSpinner();
		SegmentedRadioGroup segmentedButton = loadSegmentedRadioGroup();
	}

	private SegmentedRadioGroup loadSegmentedRadioGroup() {
		return (SegmentedRadioGroup) findViewById(R.id.segmentedRadioGroup);
	}

	public void segmentedButtonClicked(View v) {

		if (v.getId() == R.id.segmentedRadioButtonIn) {

		} else if (v.getId() == R.id.segmentedRadioButtonOut) {
			// if Out clicked

		} else {
			// represents all other states, showing both.

		}
	}

	private ListView loadListView() {
		ListView listView = (ListView) findViewById(R.id.listViewEntries);
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		Date yesterday = calendar.getTime();

		LinkedList<Entry> accountList = new LinkedList<Entry>();
		accountList.add(new Entry(yesterday, "Schuhe", 100, false));
		accountList.add(new Entry(yesterday, "Gehalt", 2000, true));
		accountList.add(new Entry(today, "Laptop", 1000, false));
		accountList.add(new Entry(today, "Taschengeld", 100, true));
		accountList.add(new Entry(today, "Essen", 200, false));

		Date thisMonth = calendar.getTime();

		// save a month just for example
		// BankAccountMonth bankAccountMonth = new BankAccountMonth(accountList,
		// thisMonth);
		// BankAccountList.addMonthToStorage(this, bankAccountMonth);
		//
		// //and load it again
		// BankAccountMonth currentMonth =
		// BankAccountList.getMonthFromStorage(this, thisMonth);
		// ListViewAdapter listAdapter = new ListViewAdapter(this,
		// currentMonth.getEntries());
		// listView.setAdapter(listAdapter);
		return listView;
	}

	private Spinner loadSpinner() {
		Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerMonths);
		ArrayAdapter<String> monthSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		// TODO monthSpinnerAdapter.addAll(getMonths());
		LinkedList<String> tempList = new LinkedList<String>();
		tempList.add("Monat 1");
		tempList.add("Monat 2");
		tempList.add("Monat 3");
		monthSpinnerAdapter.addAll(tempList);
		monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		monthSpinner.setAdapter(monthSpinnerAdapter);
		return monthSpinner;
	}

	public void showMoneySacDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment(this);
		newFragment.show(getFragmentManager(), "datePicker");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_moneysac_main, menu); 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Reacts if a menu item is selected
		Intent intent;
		switch (item.getItemId()) {
		case R.id.menu_main_category:
			//starts the activity "CategoryListView"
			intent = new Intent(this, CategoryListView.class);
			startActivity(intent);
			break;
			
		}
		return true;
	}

	public void addMonthToSpinner(String date) {

		Spinner spinner = (Spinner) findViewById(R.id.spinnerMonths);
		ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
		Log.d("DATE STRING", date);
		try {
			boolean found = false;
			for (int i = 0; i < adapter.getCount(); i++) {
				if (adapter.getItem(i).equals(sdfOut.format(sdfIn.parse(date)))) {
					found = true;
				}
			}
			if (!found) {
				adapter.add(sdfOut.format(sdfIn.parse(date)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void addIncomeClicked(View v) {
		// TODO add extras in intent bundle
		Intent intent = new Intent(this, EditEntryActivity.class);
		intent.putExtra(IS_INCOME, true);
		startActivity(intent);
	}

	public void addExpenseClicked(View v) {
		// TODO add extras in intent bundle
		Intent intent = new Intent(this, EditEntryActivity.class);
		intent.putExtra(IS_INCOME, false);
		startActivity(intent);
	}

}
