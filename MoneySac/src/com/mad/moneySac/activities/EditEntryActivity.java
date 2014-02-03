package com.mad.moneySac.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.mad.moneySac.R;

public class EditEntryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_entry);
		setTitle("Neue Ausgabe");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_entry, menu);
		return true;
	}

}
