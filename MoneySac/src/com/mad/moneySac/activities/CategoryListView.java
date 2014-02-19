package com.mad.moneySac.activities;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mad.moneySac.R;
import com.mad.moneySac.adapters.CategoryListViewAdapter;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.CategoryDBHelper;

public class CategoryListView extends Activity {

	public static final String CATEGORY_EXTRA_ID = "CATEGORY_ID";
	private ListView catList;
	private CategoryDBHelper catDBHelper;
	private List<Category> values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moneysac_category_list_view);
		catDBHelper = new CategoryDBHelper();
		initViewElements();
	}

	@Override
	protected void onResume() {
		super.onResume();
		showList();
	}
	
	/**
	 * Startet die Activity zum Anlegen einer neuen Kategorie
	 * @param view
	 */
	public void startAddCategory(View view){
		startActivity(new Intent(getApplicationContext(), CategoryDetailActivity.class));
	}

	private void showList() {
		values = new LinkedList<Category>();

		try {
			values = catDBHelper.getAll(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		CategoryListViewAdapter adapter = new CategoryListViewAdapter(this, values);
		catList.setAdapter(adapter);
		catList.setOnItemClickListener(new CategoryItemClickListener(adapter));
		registerForContextMenu(catList);
	}

	private void initViewElements() {
		catList = (ListView) findViewById(R.id.moneysac_cetegory_listview);
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.moneysac_cetegory_listview) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(values.get(info.position).getName());
			String[] menuItems = { getString(R.string.edit),
					getString(R.string.delete) };
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case 0:
			Intent categoryDetailIntend = new Intent(CategoryListView.this, CategoryDetailActivity.class);
			categoryDetailIntend.putExtra(CATEGORY_EXTRA_ID, values.get(info.position));
			startActivity(categoryDetailIntend);
			break;

		case 1:
			CategoryDBHelper db = new CategoryDBHelper();
			try {
				db.delete(this, values.get(info.position));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			showList();
			break;
		}
		return true;
	}

	private class CategoryItemClickListener implements OnItemClickListener {

		private ArrayAdapter<Category> adapter;

		public CategoryItemClickListener(ArrayAdapter<Category> adapter) {
			this.adapter = adapter;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Intent categoryDetailIntend = new Intent(CategoryListView.this, CategoryDetailActivity.class);
			categoryDetailIntend.putExtra(CATEGORY_EXTRA_ID, adapter.getItem(position));
			startActivity(categoryDetailIntend);
		}
	}
}
