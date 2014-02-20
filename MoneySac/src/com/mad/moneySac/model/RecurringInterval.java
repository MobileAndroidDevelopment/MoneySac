package com.mad.moneySac.model;

/**
 * Mögliche Intervalle, die bei wiederkehrenden Einträgen verwendet werden können. Sie besitzen zusaetzlich eine Text-Repräsentation
 */
public enum RecurringInterval {

	weekly("wöchentlich"), monthly("monatlich"), twoWeekly("alle zwei Wochen"), twoMonthly("alle zwei Monate");

	private final String text;

	RecurringInterval(String name) {
		this.text = name;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
