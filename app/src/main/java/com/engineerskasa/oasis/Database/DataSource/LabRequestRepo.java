package com.engineerskasa.oasis.Database.DataSource;

import com.engineerskasa.oasis.Model.Lab;

import java.util.List;

import io.reactivex.Flowable;

public class LabRequestRepo implements ILabReqDataSource{

    private ILabReqDataSource iLabReqDataSource;

    public LabRequestRepo(ILabReqDataSource iLabReqDataSource) {
        this.iLabReqDataSource = iLabReqDataSource;
    }

    private static LabRequestRepo instance;

    public static LabRequestRepo getInstance(ILabReqDataSource iLabReqDataSource) {

        if (instance == null) {
            instance = new LabRequestRepo(iLabReqDataSource);
        }
        return instance;
    }

    @Override
    public Flowable<List<Lab>> getAllLabRequests() {
        return iLabReqDataSource.getAllLabRequests();
    }

    @Override
    public void requestLabForm(Lab... labs) {
        iLabReqDataSource.requestLabForm(labs);
    }
}
