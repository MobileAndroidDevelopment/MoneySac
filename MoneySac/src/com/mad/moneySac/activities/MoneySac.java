package com.mad.moneySac.activities;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.moneySac.R;
import com.mad.moneySac.adapters.DatePickerFragment;
import com.mad.moneySac.adapters.ListViewAdapter;
import com.mad.moneySac.helpers.FileUtils;
import com.mad.moneySac.helpers.MoneyUtils;
import com.mad.moneySac.helpers.SacEntrySelection;
import com.mad.moneySac.helpers.SegmentedRadioGroup;
import com.mad.moneySac.model.SacEntry;
import com.mad.moneySac.model.SacEntryDBHelper;
import com.mad.moneySac.model.SacEntryType;

public class MoneySac extends Activity {

	public static final String TYPE_EXTRA = "TYPE";
	public static final String ENTRY_EXTRA = "ENTRY";
	public static final String INCOME = "income";
	public static final String EXPENSE = "expense";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.GERMANY);
	private long currentDate;
	private File[] files;
	double totalIncome = 0.0;
	double totalExpense = 0.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moneysac);
		setTitle(R.string.moneysac_title);
		loadCurrentMonthButton();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setSelection(loadSegmentedRadioGroup().getCheckedRadioButtonId());
	}

	private Button loadCurrentMonthButton() {
		Button button = (Button) findViewById(R.id.monthButton);
		Calendar c = Calendar.getInstance();
		button.setText(sdf.format(c.getTime()));
		currentDate = c.getTimeInMillis();
		return button;
	}

	public void changeMonth(int year, int month, int day) {
		Button button = (Button) findViewById(R.id.monthButton);
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

	public void setSelection(int id) {
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

	private void refreshListView(SacEntrySelection selection) {
		ListView listView = (ListView) findViewById(R.id.listViewEntries);

		SacEntryDBHelper helper = new SacEntryDBHelper();
		List<SacEntry> list = null;
		try {
			list = helper.where(this, selection);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ListViewAdapter listAdapter = new ListViewAdapter(this, list);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(listAdapter.getCategoryItemClickListener());
		registerForContextMenu(listView);
		calculateSum();
	}

	private void calculateSum() {
		TextView textViewIncome = (TextView) findViewById(R.id.textViewInValue);
		TextView textViewExpense = (TextView) findViewById(R.id.textViewOutValue);
		totalIncome = 0.0;
		totalExpense = 0.0;
		ListView listView = (ListView) findViewById(R.id.listViewEntries);
		ListViewAdapter adapter = (ListViewAdapter) listView.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).getType().equals(SacEntryType.INCOME)) {
				totalIncome += adapter.getItem(i).getAmount();
			} else {
				totalExpense += adapter.getItem(i).getAmount();
			}
		}

		textViewIncome.setText(MoneyUtils.getFormattedNumber(totalIncome));
		textViewExpense.setText(MoneyUtils.getFormattedNumber(totalExpense));
	}

	public void showMoneySacDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment(this, currentDate);
		newFragment.show(getFragmentManager(), "datePicker");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_moneysac_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
			case R.id.menu_main_category:
				// starts the activity "CategoryListView"
				intent = new Intent(this, CategoryListView.class);
				startActivity(intent);
				break;
			case R.id.menu_main_import:
				// imports the database
				showListOfBackups();
				break;
			case R.id.menu_main_export:
				// exports the database
				String fileName = FileUtils.exportDB(this);
				Toast.makeText(this, getString(R.string.database_exported) + fileName, Toast.LENGTH_LONG).show();
				break;
			case R.id.menu_main_stats:
				// starts the activity "StatsActivity"
				intent = new Intent(this, StatsActivity.class);
				intent.putExtra(INCOME, totalIncome);
				intent.putExtra(EXPENSE, totalExpense);
				startActivity(intent);
				break;
			case R.id.add_recurring_expense:
				// starts the activity "StatsActivity"
				intent = new Intent(this, RecurringEntryActivity.class);
				intent.putExtra(TYPE_EXTRA, SacEntryType.EXPENSE);
				startActivity(intent);
				break;
			case R.id.add_recurring_income:
				// starts the activity "StatsActivity"
				intent = new Intent(this, RecurringEntryActivity.class);
				intent.putExtra(TYPE_EXTRA, SacEntryType.INCOME);
				startActivity(intent);
				break;
		}
		return true;
	}

	private void showListOfBackups(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.select_import_file));

		ListView list = new ListView(this);
		File folder = new File(Environment.getExternalStorageDirectory().toString() + "/MoneySacExport");
		folder.mkdirs();
		
		files = folder.listFiles();
		String[] fileNames = new String[files.length];
		
		for(int i = 0; i < files.length; i++){
			fileNames[i] = files[i].getName().substring(8, 10) +"."+ files[i].getName().substring(5, 8) + files[i].getName().substring(0, 4)+" - " +files[i].getName().substring(11,16) +" Uhr";
		}
		
		ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, fileNames);
		list.setAdapter(modeAdapter);
		builder.setView(list);
		final Dialog dialog = builder.create();
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
					showConfirmationDialog(pos,dialog);
			}			
		});

		dialog.show();
	}

	private void showConfirmationDialog(final int pos,final Dialog pDialog) {
		// TODO: Text in String.xml
		new AlertDialog.Builder(this)
				.setMessage("Dies wird ihren aktuellen Datenbestand überschreiben! Möchten sie dennoch fortfahren?")
				.setTitle(R.string.attention)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(getString(R.string.strContinue), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try {
							FileUtils.importDatabase(getApplicationContext(),files[pos]);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Log.e("IMPORT_DATABASE", e.toString());
						}
						Toast.makeText(getApplicationContext(), R.string.database_imported, Toast.LENGTH_LONG).show();
						pDialog.dismiss();
						onResume();
					}
				}).setNegativeButton(getString(R.string.cancel), null).show();
	}

	public void addIncomeClicked(View v) {
		Intent intent = new Intent(this, EditEntryActivity.class);
		intent.putExtra(TYPE_EXTRA, SacEntryType.INCOME);
		startActivity(intent);
	}

	public void addExpenseClicked(View v) {
		Intent intent = new Intent(this, EditEntryActivity.class);
		intent.putExtra(TYPE_EXTRA, SacEntryType.EXPENSE);
		startActivity(intent);
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listViewEntries) {

			menu.setHeaderTitle(R.string.main_pls_select);
			String[] menuItems = { getString(R.string.edit), getString(R.string.delete) };
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		ListView listView = (ListView) findViewById(R.id.listViewEntries);
		ListViewAdapter adapter = (ListViewAdapter) listView.getAdapter();
		switch (item.getItemId()) {
		case 0:
			Intent editEntryIntent = new Intent(this, EditEntryActivity.class);
			editEntryIntent.putExtra(ENTRY_EXTRA, adapter.getItem(info.position));
			Log.d("longclicked item", adapter.getItem(info.position).toString());
			Log.d("longclicked item position", info.position + "");
			startActivity(editEntryIntent);
			break;

		case 1:
			SacEntryDBHelper helper = new SacEntryDBHelper();
			try {
				helper.delete(this, adapter.getItem(info.position));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			onResume();
			break;
		}
		return true;
	}

}
