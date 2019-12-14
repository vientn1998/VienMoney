package com.tnv.moneymanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "menu")
public class Menu implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "menuId")
    public int menuId;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "image")
    public int image;


    public Menu() {
    }

    public Menu(int menuId, String name, int image) {
        this.menuId = menuId;
        this.name = name;
        this.image = image;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return this.getMenuId() + " - " + this.getName() + " - "+this.getImage();
    }
}
