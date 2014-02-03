package com.mad.moneySac.model;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class CategoryDBHelper extends AbstractDBHelper<Category> {

	public List<Category> getAll(Context context) throws SQLException {
		Log.i(Category.class.getName(), "Show list again");
		Dao<Category, Integer> dao = getHelper(context).getCategoryDao();
		QueryBuilder<Category, Integer> builder = dao.queryBuilder();
		List<Category> list = dao.query(builder.prepare());
		Log.d("CategoryDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	public void createOrUpdate(Context context, Category category) throws SQLException {
		Dao<Category, Integer> dao = getHelper(context).getCategoryDao();
		dao.createOrUpdate(category);
	}

	public void delete(Context context, Category category) throws SQLException {
		Dao<Category, Integer> dao = getHelper(context).getCategoryDao();
		dao.delete(category);
	}

	public List<Category> where(Context context, String column, Object value) throws SQLException{
		Dao<Category, Integer> dao = getHelper(context).getCategoryDao();
		List<Category> categories =dao.queryBuilder().where().eq(column, value).query();
		return categories;
	}
}
