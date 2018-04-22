package com.conceptcore.patientintake.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.patientintake.R;

import java.util.List;

/**
 * Created by SVF 15213 on 18-04-2018.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder> implements View.OnClickListener {

    private Context context;
    private List<PatientBean> patientList;
    OnPatientClickListner onPatientClickListner;

    public PatientAdapter(Context context, List<PatientBean> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @Override
    public PatientAdapter.PatientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_patient_list, parent, false);
        return new PatientHolder(v);
    }

    @Override
    public void onBindViewHolder(PatientAdapter.PatientHolder holder, int position) {

        PatientBean patient = patientList.get(position);
        holder.txtPatientName.setText(patient.getPatientName());
        holder.txtPatientAge.setText(patient.getAge() + "");

        if(patient.getPatientType() == 1){
            holder.txtPatientType.setText("Doctor");
        } else if(patient.getPatientType() == 2){
            holder.txtPatientType.setText("Dentist");
        } else if(patient.getPatientType() == 3){
            holder.txtPatientType.setText("Optometrist");
        }

        holder.cardPatientList.setOnClickListener(this);
        holder.cardPatientList.setTag(R.string.app_name,position);

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    @Override
    public void onClick(View view) {
        LinearLayout ll = (LinearLayout) view;
        int position = (int) ll.getTag(R.string.app_name);
        onPatientClickListner.OnPatientClicked(view, position, patientList);
    }

    public class PatientHolder extends RecyclerView.ViewHolder {

        LinearLayout cardPatientList;
        TextView txtPatientName,txtPatientAge,txtPatientType;

        public PatientHolder(View itemView) {
            super(itemView);

            cardPatientList = itemView.findViewById(R.id.cardPatientList);
            txtPatientName = itemView.findViewById(R.id.txtPatientName);
            txtPatientAge = itemView.findViewById(R.id.txtPatientAge);
            txtPatientType = itemView.findViewById(R.id.txtPatientType);
        }
    }

    public void setOnPatientClickListner(OnPatientClickListner onPatientClickListner) {
        this.onPatientClickListner = onPatientClickListner;
    }

    public interface OnPatientClickListner {
        void OnPatientClicked(View view, int position, List<PatientBean> list);
    }
}
