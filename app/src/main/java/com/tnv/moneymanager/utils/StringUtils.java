package com.tnv.moneymanager.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
    public static String formatDateTime(long secondTime) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EE - dd/MM/yyyy");
        if (secondTime == 0) {
            return "Không có";
        }
        return mSimpleDateFormat.format(new Date(secondTime));
    }

    public static String formatHourTime(long secondTime) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("kk:mm");
        if (secondTime == 0) {
            return "Không có";
        }
        return mSimpleDateFormat.format(new Date(secondTime));
    }

    public static String formatFullDateTime(long secondTime) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EE - dd/MM/yyyy, kk:mm");
        if (secondTime == 0) {
            return "Không có";
        }
        return mSimpleDateFormat.format(new Date(secondTime));
    }

    public static String convertStringIntoDate(String date) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EE - dd/MM/yyyy, kk:mm");
        if (date.isEmpty())
            return "Không có";
        return mSimpleDateFormat.format(new Date(date));
    }

    public static String getDayNumberFromDate(String date) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd");
        if (date.isEmpty())
            return "Không có";
        return mSimpleDateFormat.format(new Date(date));
    }

    public static String getDayFromDate(String date) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EE");
        if (date.isEmpty())
            return "Không có";
        return mSimpleDateFormat.format(new Date(date));
    }

    public static String getDateFromDate(String date) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (date.isEmpty())
            return "Không có";
        return mSimpleDateFormat.format(new Date(date));
    }

    public static String formatPrice(long price) {
        if (price == 0)
            return "0 đ";
        Locale locale = new Locale("VN", "vi");
        DecimalFormat mDoubleFormatter = (DecimalFormat) NumberFormat.getInstance(locale);
        mDoubleFormatter.applyPattern("###,##0");
        return mDoubleFormatter.format(price) + " đ";
    }
}

