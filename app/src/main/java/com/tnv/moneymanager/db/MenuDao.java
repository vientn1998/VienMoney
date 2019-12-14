package com.tnv.moneymanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.model.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MenuDao {
    @Query("SELECT * FROM menu")
    List<Menu> getAll();

    @Query("SELECT * FROM menu where menuId = :menuId")
    Menu findById(int menuId);

//    @Query("SELECT COUNT(*) FROM user where name LIKE  :name")
//    long checkExistsName(String name);
//
//    @Query("SELECT COUNT(*) FROM user where email LIKE  :email")
//    long checkExistsEmail(String email);
//
//    @Query("SELECT COUNT(*) from user")
//    int countUsers();

    @Insert
    void insertAll(ArrayList<Menu> menus);

//    @Insert
//    long insert(User user);
//
//    @Delete
//    void delete(User user);
}
