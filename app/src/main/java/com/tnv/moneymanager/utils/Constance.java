package com.tnv.moneymanager.utils;

public class Constance {
    public static long PREF_USER_ID = 0;
    public static String PREF_USER = "PREF_USER";
    public static String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    public static String BROADCAST_ADD_BILL = "BROADCAST_ADD_BILL";
    public static String EXTRA_BILL = "EXTRA_BILL";
    public static String EXTRA_TYPE_MONTH = "EXTRA_TYPE_MONTH";
    public static final int REQUEST_CODE = 1100;
    public enum TYPE_TOAST {
        Error, Success, Info, Warning
    }
    public enum TYPE_MONTH {
        Current, First, Last
    }
}
