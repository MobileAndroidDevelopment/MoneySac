package com.mad.moneySac.model;

public class RecurringEntry {

	private String description;
	private double amount;
	private Category category;
	private long startDateTime;
	private long endDateTime;
	private String type;
	private RecurringInterval interval;

	public RecurringEntry(String description, double amount, Category category, long startDateTime, long endDateTime, String type, RecurringInterval interval) {
		this.description = description;
		this.amount = amount;
		this.category = category;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.type = type;
		this.interval = interval;
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

	public RecurringInterval getInterval() {
		return interval;
	}

	public void setInterval(RecurringInterval interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "RecurringEntry [description=" + description + ", amount=" + amount + ", category=" + category + ", startDateTime=" + startDateTime
				+ ", endDateTime=" + endDateTime + ", type=" + type + ", interval=" + interval + "]";
	}

}
