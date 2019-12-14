package com.tnv.moneymanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "event", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "uid",
        childColumns = "uid",
        onDelete = CASCADE))
public class Event implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "eid")
    public int eid;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "date")
    public long date;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "reminder_time")
    public String reminderTime;
    @ColumnInfo(name = "uid")
    public int uid;

    public Event() {
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int geteId() {
        return eid;
    }

    public void setMenuId(int eId) {
        this.eid = eId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    @Override
    public String toString() {
        return this.geteId() + " - " + this.getName() + " - " + this.getDate()
                + " - " + this.getDescription() + " - " + this.getReminderTime();
    }
}
