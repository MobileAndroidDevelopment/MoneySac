package com.mad.moneySac.model;


public enum RecurringInterval {

	weekly(""), 
	monthly(""), 
	twoWeekly(""),
	twoMonthly("");

	private final String text;

	RecurringInterval(String name) {
		this.text = name;
	}

	public String getText() {
		return text;
	}

	public RecurringInterval withText(String text) {
		for (RecurringInterval nextInterval : RecurringInterval.values()) {
			if (nextInterval.getText().equals(text)) {
				return nextInterval;
			}
		}
		throw new RuntimeException("Kein Interval mit dem Text gefunden: " + text);
	}
	
	@Override
	public String toString() {
		return text;
	}
}
