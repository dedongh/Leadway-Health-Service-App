package com.engineerskasa.oasis.Database.DataSource;

import com.engineerskasa.oasis.Model.Patient;

import java.util.List;

import io.reactivex.Flowable;

public class PatientRepo implements IPatientDataSource{
    private IPatientDataSource iPatientDataSource;

    public PatientRepo(IPatientDataSource iPatientDataSource) {
        this.iPatientDataSource = iPatientDataSource;
    }

    private static PatientRepo instance;

    public static PatientRepo getInstance(IPatientDataSource iPatientDataSource) {
        if (instance == null) {
            instance = new PatientRepo(iPatientDataSource);
        }
        return instance;
    }

    @Override
    public Flowable<List<Patient>> getAllPatients() {
        return iPatientDataSource.getAllPatients();
    }

    @Override
    public void registerPatient(Patient... patients) {
        iPatientDataSource.registerPatient(patients);
    }

    @Override
    public void deletePatients(Patient... patients) {
        iPatientDataSource.deletePatients(patients);
    }
}
