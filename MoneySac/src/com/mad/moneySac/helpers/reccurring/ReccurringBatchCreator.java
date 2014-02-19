package com.mad.moneySac.helpers.reccurring;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.mad.moneySac.model.ReccurringEntry;
import com.mad.moneySac.model.SacEntry;
import com.mad.moneySac.model.SacEntryDBHelper;

public abstract class ReccurringBatchCreator {

	void createSacEntries(Context context, ReccurringEntry entry) throws SQLException {
		SacEntryDBHelper helper = new SacEntryDBHelper();

		long nextCreationTime = entry.getStartDateTime();
		int counter = 0;
		while (nextCreationTime <= entry.getEndDateTime()) {
			SacEntry newEntry = SacEntry.recurringEntry(entry, nextCreationTime);
			helper.createOrUpdate(context, newEntry);
			nextCreationTime = getNextCreationTime(entry.getStartDateTime(), counter);
			counter++;
		}

		Log.d("RECCURRING_ENTRY", counter + " Eintraege angelegt");
	}

	abstract long getNextCreationTime(long startCreationTime, int entryNumber);
}
