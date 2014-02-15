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
		loadListView();
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
		currentDate = c.getTimeInMillis();
		return button;
	}
	
	public void changeMonth(int year, int month){
		Button button = (Button)findViewById(R.id.monthButton);
		Calendar c = Calendar.getInstance();
		c.set(year, month, 0);
		currentDate = c.getTimeInMillis();
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

		SacEntrySelection selection = new SacEntrySelection().setSelectedMonth(currentDate);
		SacEntryDBHelper helper = new SacEntryDBHelper();
		List<SacEntry> list = null;
		try {
//			list = helper.where(this, selection);
			list = helper.getAll(this);
			Log.d("getAll()", list.size()+"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
