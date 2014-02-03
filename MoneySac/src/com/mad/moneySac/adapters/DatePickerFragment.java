package com.mad.moneySac.adapters;

import java.lang.reflect.Field;
import java.util.Calendar;

import com.mad.moneySac.activities.MoneySac;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

/**
 * Created by Kev1n on 06.10.13.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Context context;

    public DatePickerFragment(){
    }
    
    public DatePickerFragment(Context context){
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;

        DatePickerDialog datePickerDialog = this.customDatePicker();
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        mYear = year;
        mMonth = month;
        mDay = day;
        updateDate();
    }

    protected void updateDate() {
        int localMonth = (mMonth+1);
        String monthString = localMonth < 10 ? "0" + localMonth : Integer.toString(localMonth);
        String localYear = Integer.toString(mYear).substring(2);

        ((MoneySac)getActivity()).addMonthToSpinner("20" + localYear + monthString);
    }

    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(context, this, mYear, mMonth, mDay);
        try {

            Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    Field datePickerFields[] = datePickerDialogField.getType().getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName()) || "mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }

            }
        } catch (Exception ex) {
        }
        return dpd;
    }
}
