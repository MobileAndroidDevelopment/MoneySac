package com.mad.moneySac.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.mad.moneySac.R;
import com.mad.moneySac.adapters.DatePickerFragment;
import com.mad.moneySac.adapters.ListViewAdapter;
import com.mad.moneySac.helpers.SegmentedRadioGroup;
import com.mad.moneySac.model.Entry;
import com.mad.moneySac.model.SacEntryType;

public class MoneySac extends Activity {

	public static final String TYPE_EXTRA = "TYPE";
	private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.GERMANY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moneysac);
		setTitle("MoneySac");
		load();
	}

	private void load() {
		loadListView();
		loadSegmentedRadioGroup();
		loadCurrentMonthButton();
	}

	private Button loadCurrentMonthButton() {
		Button button = (Button)findViewById(R.id.monthButton);
		Calendar c=Calendar.getInstance();
		button.setText(sdf.format(c.getTime()));
		return button;
	}
	
	public void changeMonth(int year, int month){
		Button button = (Button)findViewById(R.id.monthButton);
		Calendar c = Calendar.getInstance();
		c.set(year, month, 0);
		button.setText(sdf.format(c.getTime()));
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

		LinkedList<Entry> accountList = new LinkedList<Entry>();
		accountList.add(new Entry(today, "Schuhe", 100, false));
		accountList.add(new Entry(today, "Gehalt", 2000, true));
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
		ListViewAdapter listAdapter = new ListViewAdapter(this, accountList);
		listView.setAdapter(listAdapter);
		return listView;
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

	public void addIncomeClicked(View v) {
		// TODO add extras in intent bundle
		Intent intent = new Intent(this, EditEntryActivity.class);
		intent.putExtra(TYPE_EXTRA, SacEntryType.INCOME);
		startActivity(intent);
	}

	public void addExpenseClicked(View v) {
		// TODO add extras in intent bundle
		Intent intent = new Intent(this, EditEntryActivity.class);
		intent.putExtra(TYPE_EXTRA, SacEntryType.EXPENSE);
		startActivity(intent);
	}

}
