package com.engineerskasa.oasis.Database.DataSource;

import com.engineerskasa.oasis.Model.Patient;

import java.util.List;

import io.reactivex.Flowable;

public interface IPatientDataSource {

    Flowable<List<Patient>> getAllPatients();
    void registerPatient(Patient... patients);
    void deletePatients(Patient... patients);
}
