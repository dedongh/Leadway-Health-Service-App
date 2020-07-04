package com.engineerskasa.oasis.Database.LocalDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.engineerskasa.oasis.Model.Patient;
import com.engineerskasa.oasis.Model.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PatientDAO {

    @Query("select * from patients")
    Flowable<List<Patient>> getAllPatients();

    @Insert
    void registerPatient(Patient... patients);

    @Delete
    void deletePatients(Patient... patients);
}
