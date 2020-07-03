package com.engineerskasa.oasis.Database.DataSource;

import com.engineerskasa.oasis.Model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IUserDataSource {

    Single<User> getSingleUserInfo(String email, String password);
    Flowable<List<User>> getAllUsers();
    void registerUser(User... users);
}
