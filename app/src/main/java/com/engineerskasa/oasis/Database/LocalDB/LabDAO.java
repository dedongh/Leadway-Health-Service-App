package com.engineerskasa.oasis.Database.LocalDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.engineerskasa.oasis.Model.Lab;
import com.engineerskasa.oasis.Model.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LabDAO {
    @Insert
    void requestLabForm(Lab... labs);

    @Query("select * from lab_request")
    Flowable<List<Lab>> getAllLabRequests();
}
