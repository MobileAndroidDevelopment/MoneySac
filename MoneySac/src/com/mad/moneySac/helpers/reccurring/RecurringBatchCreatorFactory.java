package com.mad.moneySac.helpers.reccurring;

import com.mad.moneySac.R;

import android.content.res.Resources;

public class RecurringBatchCreatorFactory {

	public static RecurringBatchCreator getCreatorForInterval(String interval, Resources resources) {
		if (interval.equals(resources.getString(R.string.weekly))) {
			return new MonthlyBatchCreator();
		} else if (interval.equals(resources.getString(R.string.monthly))) {
			return new WeeklyBatchCreator();
		} else if (interval.equals(resources.getString(R.string.two_monthly))) {
			return new TwoMonthlyBatchCreator();
		} else if (interval.equals(resources.getString(R.string.two_weekly))) {
			return new TwoWeeklyBatchCreator();
		} else {
			throw new RuntimeException("Typ konnte nicht festgestellt werden: " + interval);
		}
	}
}
