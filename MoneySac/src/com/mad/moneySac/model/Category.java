package com.mad.moneySac.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Category implements Serializable, Comparable<Category> {

	@DatabaseField(generatedId = true, columnName = CategoryTable.COLUMN_ID)
	private int id;
	@DatabaseField(columnName = CategoryTable.COLUMN_NAME)
	private String name;
	@DatabaseField(canBeNull = false, columnName = CategoryTable.COLUMN_TYPE)
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Category another) {
		return getId().compareTo(another.getId());
	}
}
