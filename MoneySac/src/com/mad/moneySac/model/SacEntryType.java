package com.mad.moneySac.model;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import com.mad.moneySac.R;

public class SacEntryType {

	public static final String INCOME = "Einnahme";
	public static final String EXPENSE = "Ausgabe";
	
	private static final TreeMap<String, Integer> TYPE_ICONS = new TreeMap<String, Integer>();
	
	static {
		TYPE_ICONS.put(EXPENSE, R.drawable.money_bag_down);
		TYPE_ICONS.put(INCOME, R.drawable.money_bag_up);
	}
	
	public static int getIconForEntryType(String type){
		return TYPE_ICONS.get(type);
	}
	
	public static final List<String> getTypes(){
		List<String> allTypes = new LinkedList<String>();
		allTypes.add(EXPENSE);
		allTypes.add(INCOME);
		return allTypes;
	}
}
