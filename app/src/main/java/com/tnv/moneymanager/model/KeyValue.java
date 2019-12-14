package com.tnv.moneymanager.model;



import java.io.Serializable;


public class KeyValue implements Serializable {
    public int id;
    public String name;
    public long money;

    public KeyValue() {
    }

    public KeyValue(String name, long money) {
        this.name = name;
        this.money = money;
    }

    public KeyValue(int id, String name, long money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
