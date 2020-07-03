package com.engineerskasa.oasis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.engineerskasa.oasis.R;
import com.engineerskasa.oasis.Utility.Tools;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout open_lab_request_form,
    open_register_patient, open_manage_patients, open_locate_us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open_lab_request_form = (LinearLayout)findViewById(R.id.open_lab_request);
        open_register_patient = (LinearLayout)findViewById(R.id.open_patient_reg);
        open_manage_patients = (LinearLayout)findViewById(R.id.open_manage_pat);
        open_locate_us = (LinearLayout)findViewById(R.id.open_location);

        open_lab_request_form.setOnClickListener(this);
        open_register_patient.setOnClickListener(this);
        open_manage_patients.setOnClickListener(this);
        open_locate_us.setOnClickListener(this);

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.h1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Leadway HS");

    }

    @Override
    public void onClick(View v) {
        if (v == open_lab_request_form) {
            startActivity(new Intent(MainActivity.this, LabFormRequestActivity.class));
        }
        if (v == open_register_patient) {
            startActivity(new Intent(MainActivity.this, PatientRegistrationActivity.class));
        }
        if (v == open_manage_patients) {
            startActivity(new Intent(MainActivity.this, ManagePatientActivity.class));
        }
        if (v == open_locate_us) {
            startActivity(new Intent(MainActivity.this, LocateUsActivity.class));
        }
    }
}