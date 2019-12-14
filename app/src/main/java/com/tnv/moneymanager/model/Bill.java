package com.tnv.moneymanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.tnv.moneymanager.utils.Converters;
import com.tnv.moneymanager.utils.TimestampConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "bill",foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "uid",
        childColumns = "uid",
        onDelete = CASCADE))
public class Bill implements Serializable {
    @PrimaryKey()
    @ColumnInfo(name = "bid")
    public long bid;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "date")
    @TypeConverters({Converters.class})
    public Date date;
    @ColumnInfo(name = "money")
    public long money;
    @ColumnInfo(name = "menuId")
    public int menuId;
    @ColumnInfo(name = "itemId")
    public int itemId;
    @ColumnInfo(name = "uid")
    public long uid;
    @ColumnInfo(name = "eid")
    public int eid;

    @Ignore
    public List<Bill> list;
    public Bill() {
    }

    public Bill(Date date, List<Bill> list, long money) {
        this.date = date;
        this.list = list;
        this.money = money;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public List<Bill> getList() {
        return list;
    }

    public void setList(List<Bill> list) {
        this.list = list;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public long getuId() {
        return uid;
    }

    public void setuId(long uId) {
        this.uid = uId;
    }

    @Override
    public String toString() {
        return this.getBid() + " - " + this.getDate() + " - "  +
                " - " + this.getDescription() + " - " + this.getMenuId() +
                " - " + this.getItemId()+ " - " + this.getuId();
    }
}
