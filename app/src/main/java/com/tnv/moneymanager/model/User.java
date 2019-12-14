package com.tnv.moneymanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user")
public class User implements Serializable {
    @PrimaryKey()
    @ColumnInfo(name = "uid")
    public long uid;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "email")
    public String email;

    public User() {
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getUid() + " - " +this.getName() + " - "+this.getPassword() + " - "+this.getEmail();
    }
}
