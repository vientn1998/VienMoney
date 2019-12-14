package com.tnv.moneymanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MenuItemDao {
    @Query("SELECT * FROM menuitem")
    List<MenuItem> getAll();

    @Query("SELECT * FROM menuitem where itemId = :itemId")
    MenuItem getMenuItem(int itemId);

    @Query("SELECT * FROM menuitem where menuId = :menuId")
    List<MenuItem> getById(int menuId);

//    @Query("SELECT COUNT(*) FROM user where name LIKE  :name")
//    long checkExistsName(String name);
//
//    @Query("SELECT COUNT(*) FROM user where email LIKE  :email")
//    long checkExistsEmail(String email);
//
//    @Query("SELECT COUNT(*) from user")
//    int countUsers();

    @Insert
    void insertAll(ArrayList<MenuItem> menuItem);

//    @Insert
//    long insert(User user);
//
//    @Delete
//    void delete(User user);
}
