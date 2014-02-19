package com.mad.moneySac.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.mad.moneySac.R;

public class RecurringEntryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recurring_entry);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recurring_entry, menu);
		return true;
	}

}
