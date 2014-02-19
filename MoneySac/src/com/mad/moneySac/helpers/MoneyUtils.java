package com.mad.moneySac.helpers;

public class MoneyUtils {

	public static String getFormattedNumber(double amount){
		return String.format("%.2f", amount)+" €";
	}
}
