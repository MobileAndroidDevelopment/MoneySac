package com.mad.moneySac.activities;

import java.sql.SQLException;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mad.moneySac.R;
import com.mad.moneySac.adapters.CategoryListViewAdapter;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.CategoryDBHelper;
import com.mad.moneySac.model.SacEntryType;

public class CategoryListView extends Activity {

	public static final String CATEGORY_EXTRA_ID = "CATEGORY_ID";
	private ListView catList;
	private Button btAddCat;
	private CategoryDBHelper catDBHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moneysac_category_list_view);
		catDBHelper = new CategoryDBHelper();
		initViewElements();
		setListener();
		showList();
	}

	private void setListener() {
		btAddCat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), CategoryDetailActivity.class));

			}
		});
	}

	private void showList() {

		//fills a list with some test data

		LinkedList<Category> values = new LinkedList<Category>();

		
		try {
			values = new LinkedList<Category>(catDBHelper.getAll(this));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CategoryListViewAdapter adapter = new CategoryListViewAdapter(this, values);
		catList.setAdapter(adapter);
		catList.setOnItemClickListener(new CategoryItemClickListener(adapter));
	}

	private void initViewElements() {
		catList = (ListView) findViewById(R.id.moneysac_cetegory_listview);
		btAddCat = (Button) findViewById(R.id.addCategoryButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	private class CategoryItemClickListener implements OnItemClickListener {

		private ArrayAdapter<Category> adapter;

		public CategoryItemClickListener(ArrayAdapter<Category> adapter) {
			this.adapter = adapter;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Intent categoryDetailIntend = new Intent(CategoryListView.this, CategoryDetailActivity.class);
			categoryDetailIntend.putExtra(CATEGORY_EXTRA_ID, adapter.getItem(position));
			startActivity(categoryDetailIntend);
		}
	}
}
