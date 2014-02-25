package com.mad.moneySac.model;

/**
 * Definition eines wiederkehrenden Eintrages, egal ob Einkommen oder Ausgabe. Es handelt sich dabei nur um eine Hilfsklasse, die zur Generierung der SacEntries
 * verwendet wird. Hierbei existiert keine Persistenz der Definition. Sobald der Vorgang abgeschlossen ist, ist auch der RecurringEntry nicht mehr vorhanden.
 */
public class RecurringEntry {

	private String description;
	private double amount;
	private Category category;
	private long startDateTime;
	private long endDateTime;
	private String type;

	public RecurringEntry(String description, double amount, Category category, long startDateTime, long endDateTime, String type) {
		this.description = description;
		this.amount = amount;
		this.category = category;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public long getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(long startDateTime) {
		this.startDateTime = startDateTime;
	}

	public long getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(long endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RecurringEntry [description=" + description + ", amount=" + amount + ", category=" + category + ", startDateTime=" + startDateTime
				+ ", endDateTime=" + endDateTime + ", type=" + type + "]";
	}

}
