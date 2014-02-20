package com.mad.moneySac.helpers;

import java.util.Calendar;

public class SacEntrySelection {

	private String type;
	private Long selectedMonth;

	public SacEntrySelection() {
	}

	public String getType() {
		return type;
	}

	public SacEntrySelection setType(String type) {
		this.type = type;
		return this;
	}

	public long getSelectedMonth() {
		return selectedMonth;
	}

	public SacEntrySelection setSelectedMonth(long selectedMonth) {
		this.selectedMonth = selectedMonth;
		return this;
	}

	/**
	 * @return Millisekunden des ersten Tages des gewaehlten Monats um 00:00:00
	 */
	public long fromDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(selectedMonth);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * @return Millisekunden des letzten Tages des gewaehlten Monats um 23:59:59
	 */
	public long toDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(selectedMonth);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTimeInMillis();
	}

	public boolean hasTimeSelection() {
		return selectedMonth != null;
	}

	public boolean hasTypeSelection() {
		return type != null;
	}
}
