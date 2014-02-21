package com.mad.moneySac.model;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.mad.moneySac.helpers.SacEntrySelection;

public class SacEntryDBHelper extends AbstractDBHelper<SacEntry> {

	public List<SacEntry> getAll(Context context) throws SQLException {
		Log.i(SacEntry.class.getName(), "Show list again");
		Dao<SacEntry, Integer> dao = getHelper(context).getSacEntryDao();
		QueryBuilder<SacEntry, Integer> builder = dao.queryBuilder();
		List<SacEntry> list = dao.query(builder.prepare());
		Log.d("CategoryDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	public void createOrUpdate(Context context, SacEntry category) throws SQLException {
		Dao<SacEntry, Integer> dao = getHelper(context).getSacEntryDao();
		dao.createOrUpdate(category);
	}

	public void delete(Context context, SacEntry category) throws SQLException {
		Dao<SacEntry, Integer> dao = getHelper(context).getSacEntryDao();
		dao.delete(category);
	}

	public List<SacEntry> where(Context context, SacEntrySelection selection) throws SQLException {
		Dao<SacEntry, Integer> dao = getHelper(context).getSacEntryDao();
		Where<SacEntry, Integer> where = dao.queryBuilder().where().isNotNull("id");
		if (selection.hasTimeSelection()) {
			where.and().between("dateTime", selection.fromDate(), selection.toDate());
		}
		if (selection.hasTypeSelection()) {
			where.and().eq("type", selection.getType());
		}

		return where.query();
	}

	/**
	 * @param context
	 * @param type
	 * @param recurring
	 * @return Alle existierenden Beschreibungen sortiert nach Häufigkeit, häufigste dabei an erster Stelle
	 */
	public List<String> getUsedDescriptionsOrderByUsageDescending(Context context, String type, boolean recurring) {
		// da SQLite nicht direkt boolean kennt muss diese veränderte Abfrage auf true (kein NOT davor) oder false (NOT davor) benutzt werden
		String recurringSelection = (recurring?" ":"NOT ")+SacEntryTable.COLUMN_RECURRING;
		String queryString =
				"SELECT DISTINCT " + SacEntryTable.COLUMN_DESCRIPTION + ", COUNT(" + SacEntryTable.COLUMN_DESCRIPTION + ") AS count " +
						"FROM  " + SacEntryTable.TABLE + " " +
						"WHERE " + SacEntryTable.COLUMN_TYPE + " = ? AND "+recurringSelection+" GROUP BY " + SacEntryTable.COLUMN_DESCRIPTION + " ORDER BY count DESC";
		SQLiteDatabase db = getHelper(context).getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, new String[] { type });
		List<String> descriptions = new LinkedList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			descriptions.add(cursor.getString(0));
			cursor.moveToNext();
		}
		Log.d("DESCRIPTIONS", descriptions.toString());

		return descriptions;
	}
}
