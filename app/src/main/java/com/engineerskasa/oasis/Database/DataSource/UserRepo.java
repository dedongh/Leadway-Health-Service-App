package com.engineerskasa.oasis.Database.DataSource;

import com.engineerskasa.oasis.Model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


public class UserRepo implements IUserDataSource{

    private IUserDataSource iUserDataSource;

    public UserRepo(IUserDataSource iUserDataSource) {
        this.iUserDataSource = iUserDataSource;
    }

    private static UserRepo instance;

    public static UserRepo getInstance(IUserDataSource iUserDataSource) {
        if (instance == null) {
            instance = new UserRepo(iUserDataSource);
        }
        return instance;
    }

    @Override
    public Single<User> getSingleUserInfo(String email, String password) {
        return iUserDataSource.getSingleUserInfo(email, password);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return iUserDataSource.getAllUsers();
    }

    @Override
    public void registerUser(User... users) {
        iUserDataSource.registerUser(users);
    }
}
