package com.engineerskasa.oasis.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.engineerskasa.oasis.Common.Common;
import com.engineerskasa.oasis.Database.DataSource.LabRequestRepo;
import com.engineerskasa.oasis.Database.LocalDB.LabDataSource;
import com.engineerskasa.oasis.Database.LocalDB.LeadWayDatabase;
import com.engineerskasa.oasis.Model.Lab;
import com.engineerskasa.oasis.R;
import com.engineerskasa.oasis.Utility.Tools;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class LabFormRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txt_name, txt_age, txt_phone, txt_gender, txt_doctor, txt_desc;

    private String name, age, phone, gender, description, doctor;

    private Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_form_request);

        Tools.setSystemBarColor(this, R.color.green_500);
        Tools.setSystemBarLight(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lab Request Form");

        initDB();

        initializeView();


    }

    private void initializeView() {
        btn_save = (Button) findViewById(R.id.btn_send_req);
        btn_save.setOnClickListener(this);

        txt_name = (EditText) findViewById(R.id.req_name);
        txt_age = (EditText) findViewById(R.id.req_age);
        txt_phone = (EditText) findViewById(R.id.req_phone);
        txt_gender = (EditText) findViewById(R.id.req_gender);
        txt_doctor = (EditText) findViewById(R.id.req_doc);
        txt_desc = (EditText) findViewById(R.id.req_desc);
    }

    private void initDB() {
        Common.leadWayDatabase = LeadWayDatabase.getInstance(this);
        Common.labRequestRepo = LabRequestRepo.getInstance(LabDataSource.getInstance(Common.leadWayDatabase.labDAO()));
    }

    @Override
    public void onClick(View v) {
        if (v == btn_save) {
            sendRequest();
        }
    }

    private void sendRequest() {
        age = txt_age.getText().toString();
        gender = txt_gender.getText().toString();
        name = txt_name.getText().toString();
        phone = txt_phone.getText().toString();
        description = txt_desc.getText().toString();
        doctor = txt_doctor.getText().toString();

        if (TextUtils.isEmpty(age)) {
            txt_age.setError("Please enter age");
            txt_age.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            txt_gender.setError("Please enter gender");
            txt_gender.requestFocus();
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
        if (TextUtils.isEmpty(doctor)) {
            txt_doctor.setError("Please enter doctor's name");
            txt_doctor.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            txt_desc.setError("Please enter description");
            txt_desc.requestFocus();
            return;
        }

        Lab lab = new Lab();
        lab.setAge(age);
        lab.setDescription(description);
        lab.setDoctor(doctor);
        lab.setGender(gender);
        lab.setPhone(phone);
        lab.setName(name);

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Common.labRequestRepo.requestLabForm(lab);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(LabFormRequestActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(LabFormRequestActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}