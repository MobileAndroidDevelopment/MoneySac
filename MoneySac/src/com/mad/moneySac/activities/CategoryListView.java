package com.mad.moneySac.activities;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mad.moneySac.R;
import com.mad.moneySac.adapters.CategoryListViewAdapter;
import com.mad.moneySac.helpers.SacEntrySelection;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.CategoryDBHelper;
import com.mad.moneySac.model.SacEntry;
import com.mad.moneySac.model.SacEntryDBHelper;

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
			Log.e("CATEGORY_LIST", "Konnte die aktuellen Kategorien nicht laden!", e);
			Toast.makeText(this, R.string.load_all_categories_not_possible, Toast.LENGTH_LONG).show();
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
				Category category = values.get(info.position);

				SacEntryDBHelper sacEntryDBHelper = new SacEntryDBHelper();
				CategoryDBHelper categoryDbHelper = new CategoryDBHelper();
				try {
					List<SacEntry> elementsWithCategory = sacEntryDBHelper.where(this, new SacEntrySelection().setCategoryId(category.getId()));
					if (elementsWithCategory.size() > 0) {
						Toast.makeText(this, R.string.category_used, Toast.LENGTH_LONG).show();
					} else {
						categoryDbHelper.delete(this, values.get(info.position));
					}
				} catch (SQLException e) {
					Log.e("CATEGORY_LIST", "Konnte die Kategorie nicht l�schen!", e);
					Toast.makeText(this, R.string.could_not_delete_entry, Toast.LENGTH_LONG).show();
				} finally {
					sacEntryDBHelper.close();
					categoryDbHelper.close();
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
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Intent categoryDetailIntend = new Intent(CategoryListView.this, CategoryDetailActivity.class);
			categoryDetailIntend.putExtra(CATEGORY_EXTRA_ID, adapter.getItem(position));
			startActivity(categoryDetailIntend);
		}
	}
}
