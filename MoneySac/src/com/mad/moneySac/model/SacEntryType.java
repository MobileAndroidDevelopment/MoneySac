package com.mad.moneySac.model;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import com.mad.moneySac.R;

public class SacEntryType {

	private String name;
	private int categoryIcon;
	private int listIcon;
	private String buttonText;

	private SacEntryType(String name, int categoryIcon, int listIcon, String buttonText) {
		this.name = name;
		this.categoryIcon = categoryIcon;
		this.listIcon = listIcon;
		this.buttonText = buttonText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategoryIcon() {
		return categoryIcon;
	}

	public void setCategoryIcon(int categoryIcon) {
		this.categoryIcon = categoryIcon;
	}

	public int getListIcon() {
		return listIcon;
	}

	public void setListIcon(int listIcon) {
		this.listIcon = listIcon;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	@Override
	public String toString() {
		return "SacEntryType [name=" + name + ", buttonIcon=" + categoryIcon + ", listIcon=" + listIcon + ", buttonText=" + buttonText + "]";
	}

	public static final String INCOME = "Einnahme";
	public static final String EXPENSE = "Ausgabe";

	private static final TreeMap<String, SacEntryType> entryTypes = new TreeMap<String, SacEntryType>();

	static {
		entryTypes.put(INCOME, new SacEntryType(INCOME, R.drawable.money_bag_up, R.drawable.arrow_green, "+"));
		entryTypes.put(EXPENSE, new SacEntryType(EXPENSE, R.drawable.money_bag_down, R.drawable.arrow_red, "-"));
	}

	public static SacEntryType getType(String typeName) {
		return entryTypes.get(typeName);
	}

	public static final List<String> getTypesAsStringList() {
		List<String> allTypes = new LinkedList<String>();
		allTypes.add(EXPENSE);
		allTypes.add(INCOME);
		return allTypes;
	}
	
	public static final List<SacEntryType> getTypesAsList(){
		List<SacEntryType> types = new LinkedList<SacEntryType>();
		types.add(entryTypes.get(INCOME));
		types.add(entryTypes.get(EXPENSE));
		return types;
	}
}
