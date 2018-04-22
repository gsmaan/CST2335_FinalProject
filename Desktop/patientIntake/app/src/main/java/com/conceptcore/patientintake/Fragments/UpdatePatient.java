package com.conceptcore.patientintake.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.patientintake.Helpers.Constants;
import com.conceptcore.patientintake.Helpers.DAL;
import com.conceptcore.patientintake.Helpers.DatePickerFragment;
import com.conceptcore.patientintake.Helpers.PatientBean;
import com.conceptcore.patientintake.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdatePatient extends Fragment implements View.OnClickListener {

    private EditText etxtName,etxtHealthCardNo,etxtAddr,etxtDescription,etxtPhoneNo;
    private Button btnUpdatePatient,btnDeletePatient;

    private Spinner spDocType;
    private ArrayAdapter<String> docTypeAdapter;
    private List<String> docTypes;

    private TextView txtBirthDate,txtBoughtDate;

    private LinearLayout llDoctor,llDentist,llOptometrist;
    private EditText etxtsurgeries,etxtstoreName;

    private RadioGroup rGroupHaveBraces,rGroupMedicalBenifits;
    private RadioButton rbtnBracesTrue,rbtnBracesFalse,rbtnMedicalYes,rbtnMedicalNo;

    private PatientBean patient;

    int day = 0,boughtDay = 0;
    int month = 0,boughtMonth = 0;
    int year = 0, boughtYear = 0;

    private String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private Calendar cal,calendarNow,calBought;

    public UpdatePatient() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_patient, container, false);
        etxtName = view.findViewById(R.id.etxtName);
        etxtHealthCardNo = view.findViewById(R.id.etxtHealthCardNo);
        etxtAddr = view.findViewById(R.id.etxtAddr);
        etxtDescription = view.findViewById(R.id.etxtDescription);
        etxtPhoneNo = view.findViewById(R.id.etxtPhoneNo);
        spDocType = view.findViewById(R.id.spDocType);

        txtBirthDate = view.findViewById(R.id.txtBirthDate);
        txtBoughtDate = view.findViewById(R.id.txtBoughtDate);

        llDoctor = view.findViewById(R.id.llDoctor);
        llDentist = view.findViewById(R.id.llDentist);
        llOptometrist = view.findViewById(R.id.llOptometrist);

        etxtsurgeries = view.findViewById(R.id.etxtsurgeries);
        etxtstoreName = view.findViewById(R.id.etxtstoreName);

        rGroupHaveBraces = view.findViewById(R.id.rGroupHaveBraces);
        rGroupMedicalBenifits = view.findViewById(R.id.rGroupMedicalBenifits);

        rbtnBracesTrue = view.findViewById(R.id.rbtnBracesTrue);
        rbtnBracesFalse = view.findViewById(R.id.rbtnBracesFalse);
        rbtnMedicalYes = view.findViewById(R.id.rbtnMedicalYes);
        rbtnMedicalNo = view.findViewById(R.id.rbtnMedicalNo);

        btnUpdatePatient = view.findViewById(R.id.btnUpdatePatient);
        btnDeletePatient = view.findViewById(R.id.btnDeletePatient);

        cal = Calendar.getInstance();
        calBought = Calendar.getInstance();

        Bundle bundle = getArguments();
        patient = (PatientBean) bundle.getSerializable("patientinfo");

        setDocTypes(patient);

        setDataInViews(patient);

        btnUpdatePatient.setOnClickListener(this);
        btnDeletePatient.setOnClickListener(this);

        spDocType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        llDoctor.setVisibility(View.VISIBLE);
                        llDentist.setVisibility(View.GONE);
                        llOptometrist.setVisibility(View.GONE);

//                        resetData();
                        break;
                    case 2:
                        llDoctor.setVisibility(View.GONE);
                        llDentist.setVisibility(View.VISIBLE);
                        llOptometrist.setVisibility(View.GONE);

//                        resetData();
                        break;
                    case 3:
                        llDoctor.setVisibility(View.GONE);
                        llDentist.setVisibility(View.GONE);
                        llOptometrist.setVisibility(View.VISIBLE);

//                        resetData();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setDatePicker();
        setDatePickerForGlasses();

        return view;
    }

    private void setDataInViews(PatientBean patient){
        etxtName.setText(patient.getPatientName());
        etxtHealthCardNo.setText(patient.getPatientHealthcardNo());
        etxtAddr.setText(patient.getPatientAddress());
        etxtDescription.setText(patient.getPatientDes());
        etxtPhoneNo.setText(patient.getPatientPhoneNo());
        try {
            Date birthDate = Constants.sdfDateMobile.parse(patient.getPatientBirthDate());
            txtBirthDate.setText(Constants.sdfParseDateMobile.format(birthDate));
            cal.setTime(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        etxtsurgeries.setText(patient.getSurOrAller());
        if(patient.getIsBraces() == 1){
            rbtnBracesTrue.setChecked(true);
        } else{
            rbtnBracesFalse.setChecked(true);
        }
        if(patient.getIsMedicalBenifits() == 1){
            rbtnMedicalYes.setChecked(true);
        } else {
            rbtnMedicalNo.setChecked(true);
        }
        etxtstoreName.setText(patient.getStoreName());
        if(patient.getGlassesBoughtDate() != null){
            try {
                Date boughtDate = Constants.sdfDateMobile.parse(patient.getGlassesBoughtDate());
                txtBoughtDate.setText(Constants.sdfParseDateMobile.format(boughtDate));
                calBought.setTime(boughtDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDocTypes(PatientBean patient){
        docTypes = new ArrayList<>();
        docTypes.add(0, "--Select Doctor Type--");
        docTypes.add(1, "Doctor");
        docTypes.add(2, "Dentist");
        docTypes.add(3, "Optometrist");

        docTypeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, docTypes);
        spDocType.setAdapter(docTypeAdapter);

        spDocType.setSelection(patient.getPatientType());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setDatePicker() {

        txtBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year1, int monthOfYear,
                                      int dayOfMonth) {
                    cal.set(Calendar.YEAR, year1);
                    cal.set(Calendar.MONTH, monthOfYear);
                    cal.set(Calendar.DATE, dayOfMonth);
                    try {
                        day = dayOfMonth;
                        month = monthOfYear;
                        year = year1;
                        txtBirthDate.setText(String.valueOf(dayOfMonth) + " " + months[monthOfYear]
                                + " " + String.valueOf(year1));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    valid();
                }
            };

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    closeKeyboard(v);
                    DatePickerFragment date = new DatePickerFragment();
                    Calendar calender = Calendar.getInstance();
                    Bundle args = new Bundle();

                    Date myDate = null;
                    try {
                        myDate = Constants.sdfParseDateMobile.parse(txtBirthDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar selectedCalender = Calendar.getInstance();
                    selectedCalender.setTime(myDate);

                    if (day == 0)
                        day = selectedCalender.get(Calendar.DAY_OF_MONTH);
                    if (year == 0)
                        year = selectedCalender.get(Calendar.YEAR);
                    if (month == 0)
                        month = selectedCalender.get(Calendar.MONTH);
                    args.putInt("year", year);
                    args.putInt("month",month);
                    args.putInt("day", day);
//                    calender.add(Calendar.DAY_OF_MONTH, 1);
//                    args.putLong("minDate", calender.getTimeInMillis());
//                        calender.add(Calendar.DAY_OF_MONTH, 30);
                    args.putLong("maxDate", calender.getTimeInMillis());
                    date.setArguments(args);

                    date.setCallBack(ondate);
                    date.show(getFragmentManager(), "Date Picker");
                }
            }
        });

        txtBirthDate.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year1, int monthOfYear,
                                      int dayOfMonth) {
                    cal.set(Calendar.YEAR, year1);
                    cal.set(Calendar.MONTH, monthOfYear);
                    cal.set(Calendar.DATE, dayOfMonth);
                    try {
                        day = dayOfMonth;
                        month = monthOfYear;
                        year = year1;
                        txtBirthDate.setText(String.valueOf(dayOfMonth) + " " + months[monthOfYear]
                                + " " + String.valueOf(year1));

                        /*dateTxt = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                                + "-" + String.valueOf(year1);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    valid();
                }
            };

            @Override
            public void onClick(View v) {
                closeKeyboard(v);
                DatePickerFragment date = new DatePickerFragment();
                Calendar calender = Calendar.getInstance();
                Bundle args = new Bundle();

                Date myDate = null;
                try {
                    myDate = Constants.sdfParseDateMobile.parse(txtBirthDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar selectedCalender = Calendar.getInstance();
                selectedCalender.setTime(myDate);

                if (day == 0)
                    day = selectedCalender.get(Calendar.DAY_OF_MONTH);
                if (year == 0)
                    year = selectedCalender.get(Calendar.YEAR);
                if (month == 0)
                    month = selectedCalender.get(Calendar.MONTH);
                args.putInt("year", year);
                args.putInt("month",month);
                args.putInt("day", day);

//                calender.add(Calendar.DAY_OF_MONTH, 1);
//                args.putLong("minDate", calender.getTimeInMillis());
//                        calender.add(Calendar.DAY_OF_MONTH, 30);
                args.putLong("maxDate", calender.getTimeInMillis());
                date.setArguments(args);

                date.setCallBack(ondate);
                date.show(getFragmentManager(), "Date Picker");
            }
        });
    }

    private void setDatePickerForGlasses() {

        txtBoughtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year1, int monthOfYear,
                                      int dayOfMonth) {
                    calBought.set(Calendar.YEAR, year1);
                    calBought.set(Calendar.MONTH, monthOfYear);
                    calBought.set(Calendar.DATE, dayOfMonth);
                    try {
                        boughtDay = dayOfMonth;
                        boughtMonth = monthOfYear;
                        boughtYear = year1;
                        txtBoughtDate.setText(String.valueOf(dayOfMonth) + " " + months[monthOfYear]
                                + " " + String.valueOf(year1));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    closeKeyboard(v);
                    DatePickerFragment date = new DatePickerFragment();
                    Calendar calender = Calendar.getInstance();
                    Bundle args = new Bundle();
                    int thisDay = boughtDay;
                    int thisYear = boughtYear;
                    int thisMonth = boughtMonth;
                    if (thisDay == 0)
                        thisDay = calender.get(Calendar.DAY_OF_MONTH);
                    if (thisYear == 0)
                        thisYear = calender.get(Calendar.YEAR);
                    if (thisMonth == 0)
                        thisMonth = calender.get(Calendar.MONTH);
                    args.putInt("year", thisYear);
                    args.putInt("month", thisMonth);
                    args.putInt("day", thisDay);
//                    calender.add(Calendar.DAY_OF_MONTH, 1);
//                    args.putLong("minDate", calender.getTimeInMillis());
//                        calender.add(Calendar.DAY_OF_MONTH, 30);
                    args.putLong("maxDate", calender.getTimeInMillis());
                    date.setArguments(args);

                    date.setCallBack(ondate);
                    date.show(getFragmentManager(), "Date Picker");
                }
            }
        });

        txtBoughtDate.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year1, int monthOfYear,
                                      int dayOfMonth) {
                    calBought.set(Calendar.YEAR, year1);
                    calBought.set(Calendar.MONTH, monthOfYear);
                    calBought.set(Calendar.DATE, dayOfMonth);
                    try {
                        boughtDay = dayOfMonth;
                        boughtMonth = monthOfYear;
                        boughtYear = year1;
                        txtBoughtDate.setText(String.valueOf(dayOfMonth) + " " + months[monthOfYear]
                                + " " + String.valueOf(year1));

                        /*dateTxt = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                                + "-" + String.valueOf(year1);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    valid();
                }
            };

            @Override
            public void onClick(View v) {
                closeKeyboard(v);
                DatePickerFragment date = new DatePickerFragment();
                Calendar calender = Calendar.getInstance();
                Bundle args = new Bundle();
                int thisDay = boughtDay;
                int thisYear = boughtYear;
                int thisMonth = boughtMonth;
                if (thisDay == 0)
                    thisDay = calender.get(Calendar.DAY_OF_MONTH);
                if (thisYear == 0)
                    thisYear = calender.get(Calendar.YEAR);
                if (thisMonth == 0)
                    thisMonth = calender.get(Calendar.MONTH);
                args.putInt("year", thisYear);
                args.putInt("month", thisMonth);
                args.putInt("day", thisDay);
                args.putLong("maxDate", calender.getTimeInMillis());
                date.setArguments(args);

                date.setCallBack(ondate);
                date.show(getFragmentManager(), "Date Picker");
            }
        });
    }

    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUpdatePatient:
                if (!validate()) {
                    return;
                }
                updateData();
                break;

            case R.id.btnDeletePatient:
                DAL data = new DAL(getActivity());
                data.openDB();
                boolean suc = data.deleteData(patient.getId());
                if(suc){
                    Toast.makeText(getActivity(), "Patient is deleted successfully", Toast.LENGTH_SHORT).show();
                    openPatientList();
                } else {
                    Toast.makeText(getActivity(), "Error while deleting patient", Toast.LENGTH_SHORT).show();
                }
                data.closeDB();
                break;
        }
    }

    private void updateData(){
        DAL d = new DAL(getActivity());
        d.openDB();

        PatientBean patientBean = new PatientBean();
        patientBean.setPatientName(etxtName.getText().toString());
        patientBean.setPatientHealthcardNo(etxtHealthCardNo.getText().toString());
        patientBean.setPatientAddress(etxtAddr.getText().toString());
        patientBean.setPatientDes(etxtDescription.getText().toString());
        patientBean.setPatientPhoneNo(etxtPhoneNo.getText().toString());
        if(spDocType.getSelectedItem().toString().equals("Doctor")){
            patientBean.setPatientType(1);
        } else if(spDocType.getSelectedItem().toString().equals("Dentist")){
            patientBean.setPatientType(2);
        } else if(spDocType.getSelectedItem().toString().equals("Optometrist")){
            patientBean.setPatientType(3);
        }
        patientBean.setAge(getAge(year,month,day));
        patientBean.setPatientBirthDate(getBirthDate());
        patientBean.setId(patient.getId());

        //doctor
        patientBean.setSurOrAller(etxtsurgeries.getText().toString());

        //dentist
        if (rbtnBracesTrue.isChecked()) {
            patientBean.setIsBraces(1);
        } else {
            patientBean.setIsBraces(0);
        }
        if(rbtnMedicalYes.isChecked()){
            patientBean.setIsMedicalBenifits(1);
        } else {
            patientBean.setIsMedicalBenifits(0);
        }

        //optometrist
        patientBean.setStoreName(etxtstoreName.getText().toString());
        if(txtBoughtDate.getText().toString() != null || txtBoughtDate.getText().toString()!= "") {
            patientBean.setGlassesBoughtDate(getBoughtDate());
        } else{
            patientBean.setGlassesBoughtDate(null);
        }

        boolean success = d.updateData(patientBean);
        if(success){
            Toast.makeText(getActivity(), "Patient is updated successfully", Toast.LENGTH_SHORT).show();
            openPatientList();
        } else {
            Toast.makeText(getActivity(), "Error while updating patient", Toast.LENGTH_SHORT).show();
        }
        d.closeDB();
    }

    @Nullable
    private String getBirthDate() {
        try {
            Date date = Constants.sdfParseDateMobile.parse(txtBirthDate.getText().toString());
            return Constants.sdfDateMobile.format(date);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }

    @Nullable
    private String getBoughtDate() {
        try {
            Date date = Constants.sdfParseDateMobile.parse(txtBoughtDate.getText().toString());
            return Constants.sdfDateMobile.format(date);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }

    private void openPatientList() {
        getFragmentManager().beginTransaction().replace(R.id.frame, new PatientList()).commit();
    }

    public boolean validate() {
        boolean valid = true;
        calendarNow = Calendar.getInstance();
        calendarNow.add(Calendar.HOUR_OF_DAY, 24);

        String name = etxtName.getText().toString();
        String healthCardNo = etxtHealthCardNo.getText().toString();
        String description = etxtDescription.getText().toString();
        String addr = etxtAddr.getText().toString();
        String phone = etxtPhoneNo.getText().toString();
        String eventdate = txtBirthDate.getText().toString();
        String boughtdate = txtBoughtDate.getText().toString();
        String storeName = etxtstoreName.getText().toString();

        String category = spDocType.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter patient name", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (category == docTypes.get(0)) {
            Toast.makeText(getActivity(), "Please select Type", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (healthCardNo.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter Health Card No", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (description.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter description", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (addr.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter address", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (phone.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter phone no", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (eventdate.isEmpty()) {
            Toast.makeText(getActivity(), "Please select birth date", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(llOptometrist.getVisibility() == View.VISIBLE && storeName.isEmpty()){
            Toast.makeText(getActivity(), "Please enter storename from you bough glasses ", Toast.LENGTH_SHORT).show();
            valid = false;
        }  else if (llOptometrist.getVisibility() == View.VISIBLE && boughtdate.isEmpty()) {
            Toast.makeText(getActivity(), "Please select glasses bought date", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    private Integer getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);

        return ageInt;
    }

//    private void resetData(){
//        etxtsurgeries.setText("");
//        etxtstoreName.setText("");
//        rbtnBracesTrue.setChecked(true);
//        rbtnMedicalYes.setChecked(true);
//        txtBoughtDate.setText("");
//    }
}
