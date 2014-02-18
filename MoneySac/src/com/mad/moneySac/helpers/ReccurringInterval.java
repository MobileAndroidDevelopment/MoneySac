package com.mad.moneySac.helpers;

public enum ReccurringInterval {

	weekly("wöchentlich"), monthly("monatlich");

	private final String text;

	ReccurringInterval(String name) {
		this.text = name;
	}

	public String getText() {
		return text;
	}

	public ReccurringInterval withText(String text) {
		for (ReccurringInterval nextInterval : ReccurringInterval.values()) {
			if (nextInterval.getText().equals(text)) {
				return nextInterval;
			}
		}
		throw new RuntimeException("Kein Interval mit dem Text gefunden: " + text);
	}
}
