package com.mad.moneySac.helpers.reccurring;

public class TwoWeeklyBatchCreator extends RecurringBatchCreator{

	private static final long WEEK_IN_MILLIS = 1000*60*60*24*7;

	@Override
	long getNextCreationTime(long startCreationTime, int entryCounter) {
		return startCreationTime + (entryCounter*WEEK_IN_MILLIS * 2);
	}

}
