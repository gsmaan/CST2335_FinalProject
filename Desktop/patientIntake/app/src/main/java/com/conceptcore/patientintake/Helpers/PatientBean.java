package com.conceptcore.patientintake.Helpers;

import java.io.Serializable;

/**
 * Created by SVF 15213 on 18-04-2018.
 */

public class PatientBean implements Serializable {

    private int id,patientType,age;
    private String patientName,patientAddress,patientBirthDate,patientPhoneNo,patientHealthcardNo,patientDes;
    private String surOrAller,storeName,glassesBoughtDate;
    private int isBraces,isMedicalBenifits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPatientType() {
        return patientType;
    }

    public void setPatientType(int patientType) {
        this.patientType = patientType;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getPatientPhoneNo() {
        return patientPhoneNo;
    }

    public void setPatientPhoneNo(String patientPhoneNo) {
        this.patientPhoneNo = patientPhoneNo;
    }

    public String getPatientHealthcardNo() {
        return patientHealthcardNo;
    }

    public void setPatientHealthcardNo(String patientHealthcardNo) {
        this.patientHealthcardNo = patientHealthcardNo;
    }

    public String getPatientDes() {
        return patientDes;
    }

    public void setPatientDes(String patientDes) {
        this.patientDes = patientDes;
    }

    public String getSurOrAller() {
        return surOrAller;
    }

    public void setSurOrAller(String surOrAller) {
        this.surOrAller = surOrAller;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getGlassesBoughtDate() {
        return glassesBoughtDate;
    }

    public void setGlassesBoughtDate(String glassesBoughtDate) {
        this.glassesBoughtDate = glassesBoughtDate;
    }

    public int getIsBraces() {
        return isBraces;
    }

    public void setIsBraces(int isBraces) {
        this.isBraces = isBraces;
    }

    public int getIsMedicalBenifits() {
        return isMedicalBenifits;
    }

    public void setIsMedicalBenifits(int isMedicalBenifits) {
        this.isMedicalBenifits = isMedicalBenifits;
    }
}
