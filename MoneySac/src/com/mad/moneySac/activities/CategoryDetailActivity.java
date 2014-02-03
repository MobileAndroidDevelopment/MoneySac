package com.mad.moneySac.activities;


import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mad.moneySac.R;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.SacEntryType;
import com.mad.moneySac.model.SacEntryTypeDBHelper;


public class CategoryDetailActivity extends Activity {

	
	private Button btSave;
	private Button btCancel;
	private Spinner typeSpinner;
	private EditText edName;
	private Category category;
	
	private List<SacEntryType> typeList;
	private SacEntryTypeDBHelper typeDBHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_detail);
		
		typeDBHelper = new SacEntryTypeDBHelper();
		

		category = (Category) getIntent().getExtras().getSerializable(CategoryListView.CATEGORY_EXTRA_ID);

		initViews();
		setSpinnerValues();
		setListeners();

	}
	
	private void setSpinnerValues(){
		
		try {
			typeList = typeDBHelper.getAll(this);
		} catch (SQLException e) {
			Log.e("Get Types:", e.toString());
		}
		
		ArrayAdapter<SacEntryType> adapter = new ArrayAdapter<SacEntryType>(this, android.R.layout.simple_spinner_item, typeList);
		typeSpinner.setAdapter(adapter);
	}
	
	private void initViews(){
		btSave = (Button) findViewById(R.id.moneysac_detail_categoryl_bt_save);
		btCancel = (Button) findViewById(R.id.moneysac_detail_category_bt_cancel);
		typeSpinner = (Spinner) findViewById(R.id.moneysac_detail_category_typ_spinner);
		edName = (EditText) findViewById(R.id.moneysac_detail_category_edit_title);
		edName.setText(category.getName());
	}
	
	private void setListeners(){
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category_detail, menu);
		return true;
	}

}
