package com.mad.moneySac.activities;

import java.util.Calendar;

import junit.framework.Test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.mad.moneySac.R;

public class EditEntryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_entry);
		setTitle("Neue Ausgabe");

		getExtrasFromBundle();
		setDateButtonText();
	}

	private void getExtrasFromBundle() {
		setButtonText(getIntent().getBooleanExtra(MoneySac.IS_INCOME, false));
	}

	private void setButtonText(boolean isIncome) {
		Button readyButton = (Button) findViewById(R.id.buttonAddEntry);
		if (isIncome) {
			readyButton.setText("+");
		} else {
			readyButton.setText("-");
		}
	}

	private void setDateButtonText() {
		Button dateButton = (Button) findViewById(R.id.buttonEntryDate);
		Calendar c = Calendar.getInstance();
		dateButton.setText(c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR));
	}
	
	private void setDateButtonText(String date) {
		Button dateButton = (Button) findViewById(R.id.buttonEntryDate);
		dateButton.setText(date);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_entry, menu);
		return true;
	}
	
	public void changeDateClicked(View v){
		DialogFragment newFragment = new DatePickerFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
	    newFragment.show(getFragmentManager(), "datePicker");
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
		}
	}

}
