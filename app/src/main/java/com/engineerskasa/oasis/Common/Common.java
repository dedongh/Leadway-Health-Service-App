package com.engineerskasa.oasis.Common;

import android.content.Context;

import com.engineerskasa.oasis.Database.DataSource.LabRequestRepo;
import com.engineerskasa.oasis.Database.DataSource.PatientRepo;
import com.engineerskasa.oasis.Database.DataSource.UserRepo;
import com.engineerskasa.oasis.Database.LocalDB.LeadWayDatabase;

public class Common {

    private Context context;

    public Common(Context context) {
        this.context = context;
    }

    public static LeadWayDatabase leadWayDatabase;
    public static UserRepo userRepo;
    public static PatientRepo patientRepo;
    public static LabRequestRepo labRequestRepo;
}
