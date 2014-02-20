package com.mad.moneySac.helpers.reccurring;

public class TwoWeeklyBatchCreator extends RecurringBatchCreator {

	@Override
	long getNextCreationTime(long startCreationTime, int entryCounter) {
		return startCreationTime + (entryCounter * WeeklyBatchCreator.WEEK_IN_MILLIS * 2);
	}

}
