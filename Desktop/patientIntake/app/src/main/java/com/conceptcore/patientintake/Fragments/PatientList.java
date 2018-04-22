package com.conceptcore.patientintake.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.conceptcore.patientintake.Helpers.DAL;
import com.conceptcore.patientintake.Helpers.PatientAdapter;
import com.conceptcore.patientintake.Helpers.PatientBean;
import com.conceptcore.patientintake.R;

import java.util.ArrayList;
import java.util.List;

public class PatientList extends Fragment implements PatientAdapter.OnPatientClickListner{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvPatientList;
    private LinearLayout dataView,errorView;
    private PatientAdapter patientAdapter;
    private FloatingActionButton addPatient;
    private List<PatientBean> patientList = new ArrayList<>();

    private ProgressDialog progress;

    public PatientList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_list, container, false);

        dataView = view.findViewById(R.id.dataView);
        errorView = view.findViewById(R.id.errorView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rvPatientList = view.findViewById(R.id.rvPatientList);
        addPatient = view.findViewById(R.id.addPatient);

        showProgress();
        patientList = getDataFromDb();

        if(patientList.size() == 0){
            dataView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        } else {
            dataView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
        }

        //Recycler View
        rvPatientList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvPatientList.setLayoutManager(llm);

        patientAdapter = new PatientAdapter(getActivity(),patientList);
        rvPatientList.setAdapter(patientAdapter);
        patientAdapter.setOnPatientClickListner(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refresh data
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

                patientList = getDataFromDb();
                if(patientList.size() == 0){
                    dataView.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                } else {
                    dataView.setVisibility(View.VISIBLE);
                    errorView.setVisibility(View.GONE);
                }
                patientAdapter.notifyDataSetChanged();
            }
        });

        //get data with async task
//        patientList = getDataFromDb();
//        patientAdapter.notifyDataSetChanged();

        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                getFragmentManager().beginTransaction().replace(R.id.frame, new AddPatient()).commit();

            }
        });

        hideProgress();

        return view;
    }

    private List<PatientBean> getDataFromDb(){
        DAL d = new DAL(getActivity());
        d.openDB();
        List<PatientBean> list = d.selectData();
        d.closeDB();

//        hideProgress();

        return list;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void hideProgress() {
        if ((progress != null) && progress.isShowing()) {
            progress.dismiss();
        }
    }


    private void showProgress() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public void OnPatientClicked(View view, int position, List<PatientBean> list) {
        PatientBean patient = list.get(position);

        UpdatePatient updatePatient = new UpdatePatient();
        Bundle bundle = new Bundle();
        bundle.putSerializable("patientinfo", patient);
        updatePatient.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.frame, updatePatient).commit();
    }
}
