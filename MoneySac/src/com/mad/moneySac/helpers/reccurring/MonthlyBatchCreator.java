package com.mad.moneySac.helpers.reccurring;

import java.util.Calendar;

/**
 * Zeitberechnung fuer monatliche Eintraege
 */
public class MonthlyBatchCreator extends RecurringBatchCreator {

	@Override
	long getNextCreationTime(long startCreationTime, int entryNumber) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startCreationTime);
		calendar.add(Calendar.MONTH, entryNumber);
		return calendar.getTimeInMillis();
	}

}
