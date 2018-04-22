package com.conceptcore.patientintake.Helpers;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by SVF 15213 on 19-04-2018.
 */

public class XmlParser {
    private String currTag = null;
    private boolean firstTag = true;
    StringBuilder sb;
    private InputStream is;

    public XmlParser(InputStream is) {
        this.is = is;
    }

    public ArrayList<PatientBean> parseXML(Context ctx) {
        ArrayList<PatientBean> list = null;
        try {

            XmlPullParserFactory xppFactory = XmlPullParserFactory.newInstance();
            xppFactory.setNamespaceAware(true);

            XmlPullParser xpp = xppFactory.newPullParser();
            xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

//            URL yourXmlPath = new URL();
//            InputStream is = yourXmlPath.openConnection().getInputStream();

//            InputStream is = ctx.getAssets().open("patients.xml");

            xpp.setInput(is,null);

            list = parseXML(xpp);





//            Log.e("patient 1: "," " + list.get(0).getPatientName());
//            Log.e("patient 2: "," " + list.get(1).getPatientName());

        }   catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return list;
    }

    private ArrayList<PatientBean> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<PatientBean> patients = null;
        int eventType = parser.getEventType();
        PatientBean patient = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    patients = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("patient")){
                        patient = new PatientBean();
                    } else if (patient != null){

                        switch (name){
                            case "name":
                                patient.setPatientName(parser.nextText());
                                break;
                            case "healthcardno":
                                patient.setPatientHealthcardNo(parser.nextText());
                                break;
                            case "address":
                                patient.setPatientAddress(parser.nextText());
                                break;
                            case "description":
                                patient.setPatientDes(parser.nextText());
                                break;
                            case "phone":
                                patient.setPatientPhoneNo(parser.nextText());
                                break;
                            case "patienttype":
                                patient.setPatientType(Integer.parseInt(parser.nextText()));
                                break;
                            case "patientage":
                                patient.setAge(Integer.parseInt(parser.nextText()));
                                break;
                            case "birthdate":
                                patient.setPatientBirthDate(parser.nextText());
                                break;
                            case "surOrAller":
                                patient.setSurOrAller(parser.nextText());
                                break;
                            case "isBraces":
                                patient.setIsBraces(Integer.parseInt(parser.nextText()));
                                break;
                            case "isMedicalBenifits":
                                patient.setIsMedicalBenifits(Integer.parseInt(parser.nextText()));
                                break;
                            case "storename":
                                patient.setStoreName(parser.nextText());
                                break;
                            case "glassesBoughtDate":
                                patient.setGlassesBoughtDate(parser.nextText());
                                break;
                        }

//                        if (name.equals("name")){
//                            patient.setPatientName(parser.nextText());
//                        } else if (name.equals("healthcardno")){
//                            patient.setPatientHealthcardNo(parser.nextText());
//                        } else if(name.equals("address")){
//                            patient.setPatientAddress(parser.nextText());
//                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("patient") && patient != null){
                        patients.add(patient);
                    }
            }
            eventType = parser.next();
        }

//        Log.e("list of size read:"," " + patients.size());

        return patients;

    }
}

