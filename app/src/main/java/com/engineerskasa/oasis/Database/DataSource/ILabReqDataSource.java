package com.engineerskasa.oasis.Database.DataSource;

import com.engineerskasa.oasis.Model.Lab;

import java.util.List;

import io.reactivex.Flowable;

public interface ILabReqDataSource {

    Flowable<List<Lab>> getAllLabRequests();

    void requestLabForm(Lab... labs);
}
