package com.tnv.moneymanager.views;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.tnv.moneymanager.R;

public class ImageUtils {
    public static Drawable getIcon(Context context, int number) {
        switch (number) {
            case 100:
                return context.getDrawable(R.drawable.ic_food_drinks);
            case 101:
                return context.getDrawable(R.drawable.ic_invoice);
            case 102:
                return context.getDrawable(R.drawable.ic_tranffic);
            case 103:
                return context.getDrawable(R.drawable.ic_children);
            case 104:
                return context.getDrawable(R.drawable.ic_shopping);
            case 105:
                return context.getDrawable(R.drawable.ic_temple);
            case 106:
                return context.getDrawable(R.drawable.ic_healthcare);
            case 107:
                return context.getDrawable(R.drawable.ic_friend_lover);
            case 108:
                return context.getDrawable(R.drawable.ic_travel);
            case 109:
                return context.getDrawable(R.drawable.ic_transfer_insurance);
                /*MenuItem*/
            case 1000:
                return context.getDrawable(R.drawable.ic_eat);
            case 1001:
                return context.getDrawable(R.drawable.ic_coffee);
            case 1002:
                return context.getDrawable(R.drawable.ic_market);
                /**/
            case 1003:
                return context.getDrawable(R.drawable.ic_invoice_electricity);
            case 1004:
                return context.getDrawable(R.drawable.ic_invoice_water);
            case 1005:
                return context.getDrawable(R.drawable.ic_invoice_phone);
            case 1006:
                return context.getDrawable(R.drawable.ic_invoice_internet);
            case 1007:
                return context.getDrawable(R.drawable.ic_invoice_gas);
            /**/
            case 1008:
                return context.getDrawable(R.drawable.ic_transfer_insurance);
            case 1009:
                return context.getDrawable(R.drawable.ic_transfer_car_wash);
            case 1010:
                return context.getDrawable(R.drawable.ic_transfer_car_repair_mechanic);
            case 1011:
                return context.getDrawable(R.drawable.ic_transfer_taxi);
            case 1012:
                return context.getDrawable(R.drawable.ic_transfer_gasonline);
            /**/
            case 1013:
                return context.getDrawable(R.drawable.ic_game);
            case 1014:
                return context.getDrawable(R.drawable.ic_money);
            case 1015:
                return context.getDrawable(R.drawable.ic_book);
            case 1016:
                return context.getDrawable(R.drawable.ic_money_2);
            /**/
            case 1017:
                return context.getDrawable(R.drawable.ic_shoes);
            case 1018:
                return context.getDrawable(R.drawable.ic_shirt);
            case 1019:
                return context.getDrawable(R.drawable.ic_watch);
            /**/
            case 1020:
                return context.getDrawable(R.drawable.ic_wedding);
            case 1021:
                return context.getDrawable(R.drawable.ic_temple);
            case 1022:
                return context.getDrawable(R.drawable.ic_church);
            case 1023:
                return context.getDrawable(R.drawable.ic_medical_examination);
            case 1024:
                return context.getDrawable(R.drawable.ic_medical);
            default:
                return context.getDrawable(R.drawable.ic_image_error);
        }
    }
}
