package com.mad.moneySac.model;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class SacEntryTypeDBHelper extends AbstractDBHelper<SacEntryType> {

	public List<SacEntryType> getAll(Context context) throws SQLException {
		Log.i(SacEntryType.class.getName(), "Show list again");
		Dao<SacEntryType, Integer> dao = getHelper(context).getSacEntryTypeDao();
		QueryBuilder<SacEntryType, Integer> builder = dao.queryBuilder();
		List<SacEntryType> list = dao.query(builder.prepare());
		Log.d("CategoryDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	public void createOrUpdate(Context context, SacEntryType category) throws SQLException {
		Dao<SacEntryType, Integer> dao = getHelper(context).getSacEntryTypeDao();
		dao.createOrUpdate(category);
	}

	public void delete(Context context, SacEntryType category) throws SQLException {
		Dao<SacEntryType, Integer> dao = getHelper(context).getSacEntryTypeDao();
		dao.delete(category);
	}

	public List<SacEntryType> where(Context context, String column, Object value) throws SQLException {
		Dao<SacEntryType, Integer> dao = getHelper(context).getSacEntryTypeDao();
		List<SacEntryType> categories = dao.queryBuilder().where().eq(column, value).query();
		return categories;
	}
}
