package com.mad.moneySac.model;

import java.io.Serializable;
import java.util.Comparator;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SacEntryType implements Serializable, Comparable<SacEntryType> {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(unique = true)
	private String name;
	@DatabaseField(canBeNull = false)
	private int icon;

	public SacEntryType() {
	}

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

	@Override
	public String toString() {
		//Auskommentiert, da diese Methode vom Spinner Adapter aufgerufen wird.
		//return "SacEntryType [id=" + id + ", name=" + name + "]";
		return getName();
	}

	@Override
	public int compareTo(SacEntryType another) {
		return getId().compareTo(another.getId());
	}

	public static Comparator<SacEntryType> getNameComparator() {
		return new SacEntryTypeNameComparator();
	}

	/**
	 * @return the icon
	 */
	public int getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}

	private static class SacEntryTypeNameComparator implements Comparator<SacEntryType> {

		@Override
		public int compare(SacEntryType arg0, SacEntryType arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}

	}

}
