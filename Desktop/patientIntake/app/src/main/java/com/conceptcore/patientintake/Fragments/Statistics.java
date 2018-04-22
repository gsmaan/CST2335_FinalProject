package com.conceptcore.patientintake.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conceptcore.patientintake.Helpers.DAL;
import com.conceptcore.patientintake.R;

public class Statistics extends Fragment {

    private TextView maxAge,minAge,avgAge;

    public Statistics() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        maxAge = view.findViewById(R.id.maxAge);
        minAge = view.findViewById(R.id.minAge);
        avgAge = view.findViewById(R.id.avgAge);

        DAL d = new DAL(getActivity());
        d.openDB();
        maxAge.setText(d.getMaxAge().toString());
        minAge.setText(d.getMinAge().toString());
        avgAge.setText(d.getAvgAge().toString());
        d.closeDB();


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
