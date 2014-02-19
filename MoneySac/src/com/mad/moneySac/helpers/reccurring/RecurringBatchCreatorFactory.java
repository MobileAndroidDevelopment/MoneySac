package com.mad.moneySac.helpers.reccurring;

import com.mad.moneySac.model.RecurringInterval;

public class RecurringBatchCreatorFactory {

	public static RecurringBatchCreator getCreatorForInterval(RecurringInterval interval) {
		switch (interval) {
			case monthly:
				return new MonthlyBatchCreator();
			case weekly:
				return new WeeklyBatchCreator();
			case twoMonthly:
				return new TwoMonthlyBatchCreator();
			case twoWeekly:
				return new TwoWeeklyBatchCreator();
			default:
				throw new RuntimeException("Typ konnte nicht festgestellt werden: " + interval);
		}
	}
}
