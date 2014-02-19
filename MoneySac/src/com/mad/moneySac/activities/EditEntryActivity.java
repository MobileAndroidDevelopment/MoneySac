package com.mad.moneySac.activities;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mad.moneySac.R;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.CategoryDBHelper;
import com.mad.moneySac.model.SacEntry;
import com.mad.moneySac.model.SacEntryDBHelper;
import com.mad.moneySac.model.SacEntryType;

public class EditEntryActivity extends Activity {

	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final String IMAGE = "IMAGE";

	private Uri fileUri;
	private Spinner categorySpinner;
	private long currentDateInMillis;
	private String type;
	private SacEntry sacEntry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_entry);
		setTitle("Neue Ausgabe");
		categorySpinner = (Spinner) findViewById(R.id.spinnerEntryCategory);

		getExtrasFromBundle();
		loadCategories();
		initView();
	}

	private void initView() {
		Calendar calendar = Calendar.getInstance();
		if (sacEntry != null) {
			((EditText) findViewById(R.id.editTextEntryAmount)).setText(sacEntry.getAmount() + "");
			((EditText) findViewById(R.id.editTextEntryDesc)).setText(sacEntry.getDescription() + "");
			calendar.setTimeInMillis(sacEntry.getDateTime());

			@SuppressWarnings("unchecked")
			ArrayAdapter<Category> spinnerAdapter = (ArrayAdapter<Category>) categorySpinner.getAdapter();
			int position = spinnerAdapter.getPosition(sacEntry.getCategory());
			categorySpinner.setSelection(position);
		}

		setDateButtonText(calendar);
		((Button) findViewById(R.id.buttonAddEntry)).setText(SacEntryType.getType(type).getButtonText());

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

	private void getExtrasFromBundle() {
		if (getIntent().getExtras().containsKey(MoneySac.ENTRY_EXTRA)) {
			sacEntry = (SacEntry) getIntent().getExtras().get(MoneySac.ENTRY_EXTRA);
			type = sacEntry.getType();
		} else {
			type = getIntent().getStringExtra(MoneySac.TYPE_EXTRA);
		}
	}

	private void setDateButtonText(Calendar c) {
		Button dateButton = (Button) findViewById(R.id.buttonEntryDate);
		currentDateInMillis = c.getTimeInMillis();
		dateButton.setText(c.get(Calendar.DAY_OF_MONTH) + "." + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR));
	}

	private void setDateButtonText(String date) {
		Button dateButton = (Button) findViewById(R.id.buttonEntryDate);
		dateButton.setText(date);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_entry, menu);
		return true;
	}

	public void persistClicked(View v) {
		String desc = ((EditText) findViewById(R.id.editTextEntryDesc)).getText().toString();
		double amount = Double.parseDouble(((EditText) findViewById(R.id.editTextEntryAmount)).getText().toString());
		Category category = (Category) ((Spinner) findViewById(R.id.spinnerEntryCategory)).getSelectedItem();
		long date = currentDateInMillis;

		persist(desc, amount, category, date);
	}

	private void persist(String desc, double amount, Category category, long date) {
		if (sacEntry == null) {
			sacEntry = SacEntry.normalEntry();
		}
		sacEntry.setAmount(amount);
		sacEntry.setCategory(category);
		sacEntry.setDescription(desc);
		sacEntry.setDateTime(date);
		sacEntry.setType(type);
		if(fileUri != null){
			sacEntry.setPicturePath(fileUri+"");
		}
		Log.d("zu speicherndes objekt", sacEntry.toString());
		SacEntryDBHelper helper = new SacEntryDBHelper();
		try {
			helper.createOrUpdate(this, sacEntry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		helper.close();
		this.finish();
	}

	public void changeDateClicked(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void setCurrentMillis(long currentDateInMillis) {
		this.currentDateInMillis = currentDateInMillis;
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
			((EditEntryActivity) getActivity()).setDateButtonText(day + "." + (month + 1) + "." + year);
			final Calendar c = Calendar.getInstance();
			c.set(year, month, day);
			((EditEntryActivity) getActivity()).setCurrentMillis(c.getTimeInMillis());
		}
	}
	
	public void seePictureOfBill(View v){
		if(sacEntry != null){
			 if(sacEntry.getPicturePath() != null){
				 startActivity(sacEntry.getPicturePath());
			 } else {
				 Toast.makeText(this, "Kein Bild vorhanden", Toast.LENGTH_LONG).show();
			 }
		} else if(fileUri != null){
			startActivity(fileUri+"");
		} else {
			Toast.makeText(this, "Kein Bild vorhanden", Toast.LENGTH_LONG).show();
		}
	}
	
	private void startActivity(String path){
		 Intent intent = new Intent(this,ShowPictureActivity.class);
		 intent.putExtra(IMAGE, path);
		 startActivity(intent);
	}

	public void takePictureOfBill(View v) {
		// create Intent to take a picture and return control to the calling application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

		// start the image capture Intent
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Gespeichert!" /*+ fileUri*/, Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				Toast.makeText(this, "SRY U NO PIC", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/** Create a file Uri for saving an image */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MoneySac");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MoneySacCam", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMAN).format(new Date());
		File mediaFile;
		
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
			
		} else {
			return null;
		}

		return mediaFile;
	}
}
