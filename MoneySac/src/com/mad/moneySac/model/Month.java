package com.mad.moneySac.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Kev1n on 06.10.13.
 */
public class Month implements Serializable {

    private LinkedList<Entry> entries;
    private Date month;

    public Month(LinkedList<Entry> entries, Date month) {
        this.entries = entries;
        this.month = month;
    }

    public Month(Date month){
        this.month = month;
        this.entries = new LinkedList<Entry>();
    }

    public LinkedList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(LinkedList<Entry> entries) {
        this.entries = entries;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
