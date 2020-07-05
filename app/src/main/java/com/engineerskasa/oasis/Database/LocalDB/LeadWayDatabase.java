package com.engineerskasa.oasis.Database.LocalDB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.engineerskasa.oasis.Model.Lab;
import com.engineerskasa.oasis.Model.Patient;
import com.engineerskasa.oasis.Model.User;

@Database(entities = {User.class, Patient.class, Lab.class}, version = 1/*, exportSchema = false*/)
public abstract class LeadWayDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract PatientDAO patientDAO();
    public abstract LabDAO labDAO();

    private static LeadWayDatabase instance;

    public static LeadWayDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, LeadWayDatabase.class, "lead_way")
            .build();
        }
        return instance;
    }
}
