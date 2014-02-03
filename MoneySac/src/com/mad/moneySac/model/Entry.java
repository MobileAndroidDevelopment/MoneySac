package com.mad.moneySac.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kev1n on 05.10.13.
 */
public class Entry implements Serializable{
    private String desc;
    private boolean isIncome;
    private double amount;
    private Date date;

    public Entry(Date date, String desc, double amount, boolean isIncome) {
        this.date = date;
        this.isIncome = isIncome;
        this.desc = desc;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public boolean getIsIncome() {
        return isIncome;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }
}
