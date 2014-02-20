package com.mad.moneySac.model;

/**
 * M�gliche Intervalle, die bei wiederkehrenden Eintr�gen verwendet werden k�nnen. Sie besitzen zusaetzlich eine Text-Repr�sentation
 */
public enum RecurringInterval {

	weekly("w�chentlich"), monthly("monatlich"), twoWeekly("alle zwei Wochen"), twoMonthly("alle zwei Monate");

	private final String text;

	RecurringInterval(String name) {
		this.text = name;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
