package com.mad.moneySac.helpers.reccurring;

import java.util.Calendar;

/**
 * Zeitberechnung fuer zwei-monatliche Eintraege
 */
public class TwoMonthlyBatchCreator extends ReccurringBatchCreator {

	@Override
	long getNextCreationTime(long startCreationTime, int entryNumber) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startCreationTime);
		calendar.add(Calendar.MONTH, entryNumber*2);
		return calendar.getTimeInMillis();
	}

}
