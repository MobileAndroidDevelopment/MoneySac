package com.mad.moneySac.model;

import java.io.Serializable;
import java.util.Comparator;

import com.j256.ormlite.field.DatabaseField;

public class SacEntry implements Serializable, Comparable<SacEntry> {

	@DatabaseField(generatedId = true, columnName=SacEntryTable.COLUMN_ID)
	private int id;
	@DatabaseField(columnName=SacEntryTable.COLUMN_DESCRIPTION)
	private String description;
	@DatabaseField(columnName=SacEntryTable.COLUMN_AMOUNT)
	private double amount;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName=SacEntryTable.COLUMN_CATEGORY)
	private Category category;
	@DatabaseField(columnName=SacEntryTable.COLUMN_DATE_TIME)
	private long dateTime;
	@DatabaseField(columnName=SacEntryTable.COLUMN_TYPE)
	private String type;
	@DatabaseField(columnName=SacEntryTable.COLUMN_PICTURE_PATH)
	private String picturePath;
	@DatabaseField(columnName=SacEntryTable.COLUMN_RECURRING)
	private boolean recurring;

	public SacEntry() {
	}

	private SacEntry(boolean recurring) {
		this.recurring = recurring;
	}

	public static SacEntry normalEntry() {
		return new SacEntry(false);
	}

	public static SacEntry recurringEntry(RecurringEntry entryBase, long dateTime) {
		SacEntry newEntry =  new SacEntry(true);
		newEntry.setAmount(entryBase.getAmount());
		newEntry.setCategory(entryBase.getCategory());
		newEntry.setDateTime(dateTime);
		newEntry.setDescription(entryBase.getDescription());
		newEntry.setType(entryBase.getType());
		return newEntry;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

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

	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	@Override
	public String toString() {
		return "SacEntry [id=" + id + ", description=" + description + ", amount=" + amount + ", category=" + category + ", dateTime=" + dateTime + ", type="
				+ type + ", picturePath=" + picturePath + ", recurring=" + recurring + "]";
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
