package com.mad.moneySac.helpers.reccurring;

import com.mad.moneySac.R;

import android.content.res.Resources;

/**
 * Factory-Klasse zur Erstellung der passenden Strategie f�r die wiederkehrenden Eintr�ge.
 */
public class RecurringBatchCreatorFactory {

	/**
	 * Erstellt basierend auf dem Intervall das Creator-Objekt mit der passenden Strategie zum Anlegen wiederkehrenden Eintr�ge.
	 * @param interval
	 * @param resources benoetigt, um sprachabhaengig auf das Intervall zu reagieren
	 * @return passender ReccuringBatchCreator
	 */
	public static RecurringBatchCreator getCreatorForInterval(String interval, Resources resources) {
		if (interval.equals(resources.getString(R.string.weekly))) {
			return new WeeklyBatchCreator();
		} else if (interval.equals(resources.getString(R.string.monthly))) {
			return new MonthlyBatchCreator();
		} else if (interval.equals(resources.getString(R.string.two_monthly))) {
			return new TwoMonthlyBatchCreator();
		} else if (interval.equals(resources.getString(R.string.two_weekly))) {
			return new TwoWeeklyBatchCreator();
		} else {
			throw new RuntimeException("Typ konnte nicht festgestellt werden: " + interval);
		}
	}
}
