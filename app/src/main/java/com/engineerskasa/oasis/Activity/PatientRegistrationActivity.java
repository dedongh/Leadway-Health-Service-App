package com.engineerskasa.oasis.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.engineerskasa.oasis.Common.Common;
import com.engineerskasa.oasis.Database.DataSource.PatientRepo;
import com.engineerskasa.oasis.Database.DataSource.UserRepo;
import com.engineerskasa.oasis.Database.LocalDB.LeadWayDatabase;
import com.engineerskasa.oasis.Database.LocalDB.PatientDataSource;
import com.engineerskasa.oasis.Database.LocalDB.UserDataSource;
import com.engineerskasa.oasis.Model.Patient;
import com.engineerskasa.oasis.R;
import com.engineerskasa.oasis.Utility.Tools;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class PatientRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txt_name, txt_email, txt_phone, txt_occupation, txt_address;

    private String name, email, phone, occupation, address;

    private Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        Tools.setSystemBarColor(this, R.color.green_500);
        Tools.setSystemBarLight(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register Patient");

        initDB();

        initializeView();
    }

    private void initializeView() {
        txt_name = (EditText) findViewById(R.id.reg_name);
        txt_email = (EditText) findViewById(R.id.reg_email);
        txt_phone = (EditText) findViewById(R.id.reg_phone);
        txt_occupation = (EditText) findViewById(R.id.reg_occ);
        txt_address = (EditText) findViewById(R.id.reg_addr);

        btn_save = (Button) findViewById(R.id.btn_reg);
        btn_save.setOnClickListener(this);
    }

    private void initDB() {
        Common.leadWayDatabase = LeadWayDatabase.getInstance(this);
        Common.patientRepo = PatientRepo.getInstance(PatientDataSource.getInstance(Common.leadWayDatabase.patientDAO()));
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_save) {
            registerPatient();
        }
    }

    private void registerPatient() {
        email = txt_email.getText().toString();
        occupation = txt_occupation.getText().toString();
        name = txt_name.getText().toString();
        phone = txt_phone.getText().toString();
        address = txt_address.getText().toString();

        if (TextUtils.isEmpty(email)) {
            txt_email.setError("Please enter email");
            txt_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            txt_address.setError("Please enter address");
            txt_address.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            txt_name.setError("Please enter your name");
            txt_name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            txt_phone.setError("Please enter phone number");
            txt_phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(occupation)) {
            txt_occupation.setError("Please enter employment status");
            txt_occupation.requestFocus();
            return;
        }

        Patient patient = new Patient();
        patient.setName(name);
        patient.setAddress(address);
        patient.setOccupation(occupation);
        patient.setEmail(email);
        patient.setPhone(phone);

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Common.patientRepo.registerPatient(patient);
            }
        }) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(PatientRegistrationActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(PatientRegistrationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}