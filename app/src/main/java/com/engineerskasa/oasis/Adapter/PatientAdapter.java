package com.engineerskasa.oasis.Adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.engineerskasa.oasis.Model.Patient;
import com.engineerskasa.oasis.R;

import java.util.ArrayList;
import java.util.Random;

public abstract class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private Context context;
    private ArrayList<Patient> patientArrayList = new ArrayList<>();

    public PatientAdapter(Context context, ArrayList<Patient> patientArrayList) {
        this.context = context;
        this.patientArrayList = patientArrayList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PatientViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_layout, parent, false);
        viewHolder = new PatientViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = (Patient) patientArrayList.get(position);

        int colors[] = {
                ContextCompat.getColor(context, android.R.color.holo_purple),
                ContextCompat.getColor(context, R.color.light_blue_800),
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, android.R.color.holo_orange_light),
                ContextCompat.getColor(context, android.R.color.holo_blue_dark),
                ContextCompat.getColor(context, android.R.color.holo_red_light),
                ContextCompat.getColor(context, android.R.color.holo_green_light)
        };

       GradientDrawable gradientDrawable = (GradientDrawable)holder.initialsTextView.getBackground();
       gradientDrawable.setColor(colors[new Random().nextInt(colors.length)]);
        holder.patient_number.setText(patientArrayList.get(position).getPhone());
        holder.patient_name.setText(patientArrayList.get(position).getName());

        holder.initialsTextView.setText(holder.patient_name.getText().toString().toUpperCase().substring(0,1));
        holder.patient_layout.setTag(position);
        holder.patient_layout.setOnClickListener(v -> {
            int pos = (int)holder.patient_layout.getTag();
            select(pos);
        });
    }

    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }

    public abstract void select(int position);

    public class PatientViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout patient_layout;
        private TextView patient_name, patient_number, initialsTextView;
        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_layout = (LinearLayout) itemView.findViewById(R.id.layout_patient);
            patient_name = (TextView) itemView.findViewById(R.id.txt_name);
            patient_number = (TextView) itemView.findViewById(R.id.txt_number);
            initialsTextView = (TextView) itemView.findViewById(R.id.initialsTextView);
        }
    }
}

