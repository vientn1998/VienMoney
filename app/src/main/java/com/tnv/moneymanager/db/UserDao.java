package com.tnv.moneymanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tnv.moneymanager.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user where name LIKE  :name AND password LIKE :password")
    User findByName(String name, String password);

    @Query("SELECT COUNT(*) FROM user where name LIKE  :name")
    long checkExistsName(String name);

    @Query("SELECT COUNT(*) FROM user where email LIKE  :email")
    long checkExistsEmail(String email);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Insert
    long insert(User user);

    @Delete
    void delete(User user);
}
