package com.tnv.moneymanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Menu.class,
        parentColumns = "menuId",
        childColumns = "menuId",
        onDelete = CASCADE))
public class MenuItem implements Serializable {
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "itemId")
    public int itemId;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "image")
    public int image;
    @ColumnInfo(name = "menuId")
    public int menuId;

    public MenuItem() {
    }

    public MenuItem(String name, int image, int menuId) {
        this.name = name;
        this.image = image;
        this.menuId = menuId;
    }

    public MenuItem(int itemId, String name, int image, int menuId) {
        this.itemId = itemId;
        this.name = name;
        this.image = image;
        this.menuId = menuId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
        return this.getItemId() + " - " + this.getName() + " - " + this.getImage() + " - " + this.getMenuId();
    }
}
