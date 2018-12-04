package com.example.vvdn.demoproject.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CryptoUtilty {

    public static boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static NumberFormat initDecimalFormate(NumberFormat decimalFormat, int value) {
        decimalFormat = DecimalFormat.getInstance();
        if (value > -1) {
            decimalFormat.setMinimumFractionDigits(value);
        } else {
            decimalFormat.setMinimumFractionDigits(2);
        }
        return decimalFormat;
    }

    public static boolean isNotNull(String value) {
        if (value == null || value.equalsIgnoreCase("NA") || value.equalsIgnoreCase("") || value.equalsIgnoreCase(" ")) {
            return false;
        }
        return true;
    }


    public static String getCurrencySymbol(String currencySymbel) {
        if (isNotNull(currencySymbel)) {
            try {
                if (currencySymbel.contains("\\u")) {
                    return String.valueOf((char) Integer.parseInt(currencySymbel.replace("\\u", "").trim(), 16));
                }
                return currencySymbel;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return " ";
    }

    public static String encrypt(Context context, String data) {
        return data;
    }

    public static String decrypt(Context context, String data) {
        return data;
    }
}


