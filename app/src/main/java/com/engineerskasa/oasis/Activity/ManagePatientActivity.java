package com.engineerskasa.oasis.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.engineerskasa.oasis.Adapter.PatientAdapter;
import com.engineerskasa.oasis.Common.Common;
import com.engineerskasa.oasis.Database.DataSource.PatientRepo;
import com.engineerskasa.oasis.Database.LocalDB.LeadWayDatabase;
import com.engineerskasa.oasis.Database.LocalDB.PatientDataSource;
import com.engineerskasa.oasis.Model.Patient;
import com.engineerskasa.oasis.R;
import com.engineerskasa.oasis.Utility.GridSpacingItemDecoration;
import com.engineerskasa.oasis.Utility.Tools;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ManagePatientActivity extends AppCompatActivity {

    private GridLayoutManager gridLayoutManager;
    private PatientAdapter patientAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Patient> patientArrayList = new ArrayList<>();

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_patient);

        Tools.setSystemBarColor(this, R.color.green_500);
        Tools.setSystemBarLight(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Patients");

        initDB();

        initializeView();

    }

    private void initializeView() {
        gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);

        recyclerView = (RecyclerView) findViewById(R.id.patient_recycler);
        recyclerView.setLayoutManager(gridLayoutManager);

        int spanCount = 3; // 3 columns
        int spacing = 15; // 50px
        boolean includeEdge = true;

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        patientAdapter = new PatientAdapter(this, patientArrayList) {
            @Override
            public void select(int position) {
                Patient patient = patientArrayList.get(position);
                //Toast.makeText(ManagePatientActivity.this, "ID: "+ patient.getId(), Toast.LENGTH_SHORT).show();
            }
        };

        recyclerView.setAdapter(patientAdapter);

        fetchAllPatientInfo();
    }

    private void fetchAllPatientInfo() {
        compositeDisposable.add(
                Common.patientRepo.getAllPatients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(patients -> {
                    Log.e("GJDS", "fetchAllPatientInfo: "+ patients.size() );
                    if (patients.size() > 0) {
                        patientArrayList.addAll(patients);
                    }
                    patientAdapter.notifyDataSetChanged();
                }, throwable -> {
                    Toast.makeText(this, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    patientAdapter.notifyDataSetChanged();
                })
        );
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
}