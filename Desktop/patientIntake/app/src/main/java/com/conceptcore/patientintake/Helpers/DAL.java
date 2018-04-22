package com.conceptcore.patientintake.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SVF 15213 on 18-04-2018.
 */

public class DAL extends SQLiteOpenHelper {

    private final static String DB_NAME = "patientsDB";
    private Context context = null;
    private SQLiteDatabase sqlDB;

    public DAL(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table patients ( _id integer primary key autoincrement, name text, healthcardno text, address text,description text,phone text, patienttype integer,patientage integer, birthdate date, surOrAller text, isBraces integer, isMedicalBenifits integer, storename text, glassesBoughtDate date )";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDB() {
        sqlDB = getWritableDatabase();
    }

    public void closeDB() {
        if (sqlDB != null && sqlDB.isOpen()) {
            sqlDB.close();
        }
    }

    public boolean insertData(PatientBean patient){

        ContentValues cv = new ContentValues();
        cv.put("name",patient.getPatientName());
        cv.put("healthcardno",patient.getPatientHealthcardNo());
        cv.put("address",patient.getPatientAddress());
        cv.put("description",patient.getPatientDes());
        cv.put("phone",patient.getPatientPhoneNo());
        cv.put("patienttype",patient.getPatientType());
        cv.put("patientage",patient.getAge());
        cv.put("birthdate",patient.getPatientBirthDate());

        cv.put("surOrAller",patient.getSurOrAller());
        cv.put("isBraces",patient.getIsBraces());
        cv.put("isMedicalBenifits",patient.getIsMedicalBenifits());
        cv.put("storename",patient.getStoreName());
        cv.put("glassesBoughtDate",patient.getGlassesBoughtDate());

        long l = sqlDB.insert("patients",null,cv);
        if(l != -1){
            return true;
        } else {
            return false;
        }
    }

    public List<PatientBean> selectData() {
        Cursor cursor = sqlDB.query("patients", null, null, null, null, null, null, null);
        List<PatientBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            PatientBean patientBean = new PatientBean();
            patientBean.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            patientBean.setPatientName(cursor.getString(cursor.getColumnIndex("name")));
            patientBean.setPatientHealthcardNo(cursor.getString(cursor.getColumnIndex("healthcardno")));
            patientBean.setPatientAddress(cursor.getString(cursor.getColumnIndex("address")));
            patientBean.setPatientDes(cursor.getString(cursor.getColumnIndex("description")));
            patientBean.setPatientPhoneNo(cursor.getString(cursor.getColumnIndex("phone")));
            patientBean.setPatientType(cursor.getInt(cursor.getColumnIndex("patienttype")));
            patientBean.setAge(cursor.getInt(cursor.getColumnIndex("patientage")));
            patientBean.setPatientBirthDate(cursor.getString(cursor.getColumnIndex("birthdate")));

            patientBean.setSurOrAller(cursor.getString(cursor.getColumnIndex("surOrAller")));
            patientBean.setIsBraces(cursor.getInt(cursor.getColumnIndex("isBraces")));
            patientBean.setIsMedicalBenifits(cursor.getInt(cursor.getColumnIndex("isMedicalBenifits")));
            patientBean.setStoreName(cursor.getString(cursor.getColumnIndex("storename")));
            patientBean.setGlassesBoughtDate(cursor.getString(cursor.getColumnIndex("glassesBoughtDate")));
            list.add(patientBean);
        }
        cursor.close();
        return list;
    }

    public boolean updateData(PatientBean patient){
        ContentValues cv = new ContentValues();
        cv.put("name",patient.getPatientName());
        cv.put("healthcardno",patient.getPatientHealthcardNo());
        cv.put("address",patient.getPatientAddress());
        cv.put("description",patient.getPatientDes());
        cv.put("phone",patient.getPatientPhoneNo());
        cv.put("patienttype",patient.getPatientType());
        cv.put("patientage",patient.getAge());
        cv.put("birthdate",patient.getPatientBirthDate());

        cv.put("surOrAller",patient.getSurOrAller());
        cv.put("isBraces",patient.getIsBraces());
        cv.put("isMedicalBenifits",patient.getIsMedicalBenifits());
        cv.put("storename",patient.getStoreName());
        cv.put("glassesBoughtDate",patient.getGlassesBoughtDate());

        int count = sqlDB.update("patients",cv,"_id = " + patient.getId() ,null);

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteData(int id){
        int count = sqlDB.delete("patients","_id = "+ id,null);

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    public Integer getMinAge(){
        String query = "";
        final Cursor cursor = sqlDB.rawQuery("select min(patientage) from patients;", null);
        int min = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    min = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        return min;
    }

    public Integer getMaxAge(){
        String query = "";
        final Cursor cursor = sqlDB.rawQuery("select max(patientage) from patients;", null);
        int max = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    max = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        return max;
    }

    public Integer getAvgAge(){
        String query = "";
        final Cursor cursor = sqlDB.rawQuery("select avg(patientage) from patients;", null);
        int avg = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    avg = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        return avg;
    }
}
