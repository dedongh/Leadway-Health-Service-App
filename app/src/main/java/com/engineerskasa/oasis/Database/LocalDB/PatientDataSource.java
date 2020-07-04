package com.engineerskasa.oasis.Database.LocalDB;

import com.engineerskasa.oasis.Database.DataSource.IPatientDataSource;
import com.engineerskasa.oasis.Model.Patient;

import java.util.List;

import io.reactivex.Flowable;

public class PatientDataSource implements IPatientDataSource {
    private PatientDAO patientDAO;
    private static PatientDataSource instance;

    public PatientDataSource(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public static PatientDataSource getInstance(PatientDAO patientDAO) {
        if (instance == null) {
            instance = new PatientDataSource(patientDAO);
        }
        return instance;
    }

    @Override
    public Flowable<List<Patient>> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    @Override
    public void registerPatient(Patient... patients) {
        patientDAO.registerPatient();
    }

    @Override
    public void deletePatients(Patient... patients) {
        patientDAO.deletePatients();
    }
}
