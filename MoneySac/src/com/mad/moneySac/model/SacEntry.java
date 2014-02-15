package com.mad.moneySac.model;

import java.io.Serializable;
import java.util.Comparator;

import com.j256.ormlite.field.DatabaseField;

public class SacEntry implements Serializable, Comparable<SacEntry> {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String description;
	@DatabaseField
	private double amount;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Category category;
	@DatabaseField
	private long dateTime;
	@DatabaseField
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SacEntry [id=" + id + ", description=" + description + ", amount=" + amount + ", category=" + category + ", dateTime=" + dateTime + ", type="
				+ type + "]";
	}

	@Override
	public int compareTo(SacEntry another) {
		return getId().compareTo(another.getId());
	}

	public static Comparator<SacEntry> getDateComparator() {
		return new SacEntryDateComparator();
	}

	private static class SacEntryDateComparator implements Comparator<SacEntry> {

		@Override
		public int compare(SacEntry lhs, SacEntry rhs) {
			return lhs.getDateTime().compareTo(rhs.getDateTime());
		}

	}
}
