package com.mad.moneySac.helpers.reccurring;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.mad.moneySac.model.RecurringEntry;
import com.mad.moneySac.model.SacEntry;
import com.mad.moneySac.model.SacEntryDBHelper;

public abstract class RecurringBatchCreator {

	/**
	 * Erstellt SacEntries nach RecurringEntry-Definition.
	 * @param context
	 * @param entry
	 * @throws SQLException
	 */
	public void createSacEntries(Context context, RecurringEntry entry) throws SQLException {
		SacEntryDBHelper helper = new SacEntryDBHelper();

		long nextCreationTime = entry.getStartDateTime();
		int counter = 0;
		while (nextCreationTime <= entry.getEndDateTime()) {
			nextCreationTime = getNextCreationTime(entry.getStartDateTime(), counter);
			SacEntry newEntry = SacEntry.recurringEntry(entry, nextCreationTime);
			helper.createOrUpdate(context, newEntry);
			counter++;
		}
		helper.close();
		Log.d("RECCURRING_ENTRY", counter + " Eintraege angelegt");
	}

	/**
	 * @param startCreationTime
	 * @param entryNumber
	 * @return die Zeit des x-ten Entries ausgehend von der Startzeit
	 */
	abstract long getNextCreationTime(long startCreationTime, int entryNumber);
}
