package com.conceptcore.patientintake.Helpers;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;
    private DatePickerDialog datepic;
    private int year, month, day;
    private Long minDate, maxDate;

    public DatePickerFragment() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    @SuppressLint("NewApi")
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
//        minDate = args.getLong("minDate");
        maxDate = args.getLong("maxDate");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        datepic = new DatePickerDialog(getActivity(), ondateSet, year, month, day);
//        datepic.getDatePicker().setMinDate(minDate);
        datepic.getDatePicker().setMaxDate(maxDate);
        return datepic;
    }

}

