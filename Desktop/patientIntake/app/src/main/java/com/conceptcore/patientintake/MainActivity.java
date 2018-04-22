package com.conceptcore.patientintake;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.conceptcore.patientintake.Fragments.AddPatient;
import com.conceptcore.patientintake.Fragments.PatientList;
import com.conceptcore.patientintake.Fragments.Statistics;
import com.conceptcore.patientintake.Fragments.UpdatePatient;
import com.conceptcore.patientintake.Dialogs.UserHelp;
import com.conceptcore.patientintake.Helpers.DAL;
import com.conceptcore.patientintake.Helpers.PatientBean;
import com.conceptcore.patientintake.Helpers.XmlParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //import list into db

        new InserDataWithAsyncTask().execute();

    }

    private void openPatientList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PatientList()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            UserHelp dialog = new UserHelp();
            dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            //getSupportFragmentManager().beginTransaction().replace(R.id.frame, new UserHelp()).commit();
            return true;
        } else if(id == R.id.statics){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Statistics()).commit();
            return true;
        } else if(id == R.id.importxml){
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");

            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    XmlParser parser = new XmlParser(inputStream);
                    ArrayList<PatientBean> list = parser.parseXML(MainActivity.this);

                    Toast.makeText(this, "List size in selected file: " + list.size(), Toast.LENGTH_SHORT).show();

                    new InserDataWithAsyncTask().execute();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
//                Log.i(TAG, "Uri: " + uri.toString());
//                showImage(uri);
            }
        }
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (fragment instanceof AddPatient) {
            openPatientList();
        } else if (fragment instanceof UpdatePatient) {
            openPatientList();
        } else if (fragment instanceof Statistics) {
            openPatientList();
        } else if (fragment instanceof UserHelp) {
            openPatientList();
        } else if (fragment instanceof PatientList) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setTitle("Please Confirm!");
            builder1.setMessage("Do You Want To Exit From Application?");

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                            return;
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else {
            super.onBackPressed();
        }
    }

    private class InserDataWithAsyncTask extends AsyncTask<Void,Void,Boolean> {

        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected Boolean doInBackground(Void... voids) {
            XmlParser parser = null;
            try {
                parser = new XmlParser(MainActivity.this.getAssets().open("patients.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<PatientBean> list = parser.parseXML(MainActivity.this);

//            Log.e("--------","Hello from asynctask-------");
//            Log.e("data got in list","" + list.get(0).getPatientName());
//            Log.e("list size","" + list.size());

            DAL dal = new DAL(MainActivity.this);
            dal.openDB();
            for(int i = 0; i < list.size(); i++){
                dal.insertData(list.get(i));
            }
            dal.closeDB();

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("Parsing Xml Data...");
            pdLoading.show();

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            pdLoading.dismiss();

            openPatientList();
        }
    }
}
