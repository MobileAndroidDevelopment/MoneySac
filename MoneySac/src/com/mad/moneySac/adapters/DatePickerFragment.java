package com.mad.moneySac.adapters;

import java.lang.reflect.Field;
import java.util.Calendar;

import com.mad.moneySac.R;
import com.mad.moneySac.R.color;
import com.mad.moneySac.activities.MoneySac;

import android.annotation.SuppressLint;
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
@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static final int DATE_DIALOG_ID = 1;
    private long date;
    private Context context;

    public DatePickerFragment(){
    }
    
    public DatePickerFragment(Context context, long date){
        this.context = context;
        this.date = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog = this.customDatePicker();
        return datePickerDialog;
    }

	public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        ((MoneySac)getActivity()).changeMonth(year, month, day);
    }
    
    private DatePickerDialog customDatePicker() {
    	Calendar c = Calendar.getInstance();
    	c.setTimeInMillis(date);
        DatePickerDialog dpd = new DatePickerDialog(context, this, c.get(Calendar.YEAR), (c.get(Calendar.MONTH)+1), 0);
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
		// TODO: Text in String.xml
        dpd.setTitle(getActivity().getString(R.string.choose_month));
        return dpd;
    }
}
