package com.mad.moneySac.helpers.reccurring;

import java.util.Calendar;

/**
 * Zeitberechnung fuer monatliche Eintraege
 */
public class MonthlyBatchCreator extends RecurringBatchCreator {

	/**
	 * Berechnung: Einfaches Addieren von x-Monaten auf das Startdatum. Dies hat den Vorteil, dass auch Datums-Additionen funktioneren, die bei Berechnung
	 * mit Millisekunden in den nächsten Monat rutschen würden.<br/>
	 * Beispiel: 31.01.2014 + 1 Monat = 28.02.2014
	 */
	@Override
	long getNextCreationTime(long startCreationTime, int entryNumber) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startCreationTime);
		calendar.add(Calendar.MONTH, entryNumber);
		return calendar.getTimeInMillis();
	}

}
