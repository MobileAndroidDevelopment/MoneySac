package com.mad.moneySac.activities;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.mad.moneySac.R;
import com.mad.moneySac.adapters.DatePickerFragment;
import com.mad.moneySac.adapters.ListViewAdapter;
import com.mad.moneySac.helpers.SacEntrySelection;
import com.mad.moneySac.helpers.SegmentedRadioGroup;
import com.mad.moneySac.model.SacEntry;
import com.mad.moneySac.model.SacEntryDBHelper;
import com.mad.moneySac.model.SacEntryType;

public class MoneySac extends Activity {

	public static final String TYPE_EXTRA = "TYPE";
	private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.GERMANY);
	private long currentDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moneysac);
		setTitle("MoneySac");
		load();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setSelection(loadSegmentedRadioGroup().getCheckedRadioButtonId());
	}

	private void load() {
		loadCurrentMonthButton();
	}

	private Button loadCurrentMonthButton() {
		Button button = (Button)findViewById(R.id.monthButton);
		Calendar c=Calendar.getInstance();
		button.setText(sdf.format(c.getTime()));
		currentDate = c.getTimeInMillis();
		return button;
	}
	
	public void changeMonth(int year, int month, int day){
		Button button = (Button)findViewById(R.id.monthButton);
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		currentDate = c.getTimeInMillis();
		button.setText(sdf.format(c.getTime()));
		setSelection(loadSegmentedRadioGroup().getCheckedRadioButtonId());
	}
	

	private SegmentedRadioGroup loadSegmentedRadioGroup() {
		return (SegmentedRadioGroup) findViewById(R.id.segmentedRadioGroup);
	}


	public void segmentedButtonClicked(View v) {
		setSelection(v.getId());
	}
	
	public void setSelection(int id){
		SacEntrySelection selection = null;
		if (id == R.id.segmentedRadioButtonIn) {
			// if income selected
			selection = new SacEntrySelection().setSelectedMonth(currentDate).setType(SacEntryType.INCOME);
		} else if (id == R.id.segmentedRadioButtonOut) {
			// if expenses selected
			selection = new SacEntrySelection().setSelectedMonth(currentDate).setType(SacEntryType.EXPENSE);
		} else {
			// represents all other states, showing both.
			selection = new SacEntrySelection().setSelectedMonth(currentDate);
		}
		refreshListView(selection);
	}

	private ListView refreshListView(SacEntrySelection selection) {
		ListView listView = (ListView) findViewById(R.id.listViewEntries);
		
		SacEntryDBHelper helper = new SacEntryDBHelper();
		List<SacEntry> list = null;
		try {
			list = helper.where(this, selection);
		} catch (SQLException e) {
			// TODO Exception Handling
			e.printStackTrace();
		}
		
		ListViewAdapter listAdapter = new ListViewAdapter(this, list);
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
