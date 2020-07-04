package com.engineerskasa.oasis.Database.LocalDB;

import com.engineerskasa.oasis.Database.DataSource.ILabReqDataSource;
import com.engineerskasa.oasis.Model.Lab;

import java.util.List;

import io.reactivex.Flowable;

public class LabDataSource implements ILabReqDataSource {

    private LabDAO labDAO;
    private static LabDataSource instance;

    public LabDataSource(LabDAO labDAO) {
        this.labDAO = labDAO;
    }

    public static LabDataSource getInstance(LabDAO labDAO) {
        if (instance == null) {
            instance = new LabDataSource(labDAO);
        }
        return instance;
    }

    @Override
    public Flowable<List<Lab>> getAllLabRequests() {
        return labDAO.getAllLabRequests();
    }

    @Override
    public void requestLabForm(Lab... labs) {
        labDAO.requestLabForm(labs);
    }
}
