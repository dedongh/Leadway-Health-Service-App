package com.engineerskasa.oasis.Database.LocalDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.engineerskasa.oasis.Model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface UserDAO {

    @Query("select * from users where email = :email and password = :password")
    Single<User> getSingleUserInfo(String email, String password);

    @Query("select * from users")
    Flowable<List<User>> getAllUsers();

    @Insert
    void registerUser(User... users);
}
