package com.engineerskasa.oasis.Database.LocalDB;

import com.engineerskasa.oasis.Database.DataSource.IUserDataSource;
import com.engineerskasa.oasis.Model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


public class UserDataSource implements IUserDataSource {

    private UserDAO userDAO;
    private static UserDataSource instance;

    public UserDataSource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static UserDataSource getInstance(UserDAO userDAO) {
        if (instance == null) {
            instance = new UserDataSource(userDAO);
        }
        return instance;
    }
    @Override
    public Single<User> getSingleUserInfo(String email, String password) {
        return userDAO.getSingleUserInfo(email, password);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void registerUser(User... users) {
        userDAO.registerUser(users);
    }
}
