package com.mad.moneySac.activities;

import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mad.moneySac.R;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.CategoryDBHelper;
import com.mad.moneySac.model.SacEntryType;

public class CategoryDetailActivity extends Activity {

	private Button btSave;
	private Spinner typeSpinner;
	private EditText edName;
	private Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_detail);

		initViews();
		setSpinnerValues();
		setListeners();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			category = (Category) extras
					.getSerializable(CategoryListView.CATEGORY_EXTRA_ID);
			updateTextBoxes();
		}

	}

	private void updateTextBoxes() {
		edName.setText(category.getName());
		if (!((String) typeSpinner.getSelectedItem()).equals(category.getType())){
			typeSpinner.setSelection(1);
		}
	}

	private void setSpinnerValues() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item, SacEntryType.getTypes());
		typeSpinner.setAdapter(adapter);
	}

	private void initViews() {
		btSave = (Button) findViewById(R.id.moneysac_detail_category_bt_save);
		typeSpinner = (Spinner) findViewById(R.id.moneysac_detail_category_typ_spinner);
		edName = (EditText) findViewById(R.id.moneysac_detail_category_edit_title);
	}

	private void setListeners() {

		btSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				save();
			}
		});

	}

	private void save() {
		CategoryDBHelper catDBHelper = new CategoryDBHelper();
		if (category == null)
			category = new Category();

		category.setType((String) typeSpinner.getSelectedItem());
		if (edName.getText().toString().trim().isEmpty())
			Toast.makeText(this, R.string.category_details_name_empty,
					Toast.LENGTH_SHORT).show();
		else {
			category.setName(edName.getText().toString().trim());
			try {
				catDBHelper.createOrUpdate(this, category);
				Toast.makeText(this, R.string.saved_succesfull,
						Toast.LENGTH_SHORT).show();
				finish();
			} catch (SQLException e) {
				Log.e("CategoryDetailsSave", e.toString());
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Reacts if a menu item is selected
		switch (item.getItemId()) {
		case R.id.menu_category_delete:
			if (category != null) {
				CategoryDBHelper catDBHelper = new CategoryDBHelper();
				try {
					catDBHelper.delete(this, category);
					Toast.makeText(this, R.string.deleted_category, Toast.LENGTH_SHORT).show();
					finish();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Log.e("CategoryDetailsDelete", e.toString());
				}
			}else{
				finish();
			}
			break;

		}
		return true;
	}

}
