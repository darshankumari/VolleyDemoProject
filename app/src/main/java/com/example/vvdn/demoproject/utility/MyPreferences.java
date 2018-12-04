package com.example.vvdn.demoproject.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyPreferences {
    private static String PREF_NAME = "mePref";
    private static String THEME_COLOR = "themeColor";
    private static MyPreferences myPreferences;
    private Context context;
    private Editor editor = getPreferences().edit();
    private SharedPreferences preferences;

    public static MyPreferences getInstance(Context context) {
        if (myPreferences == null) {
            myPreferences = new MyPreferences(context.getApplicationContext());
        }
        return myPreferences;
    }

    @SuppressLint("WrongConstant")
    public MyPreferences(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, 4);
        this.context = context;
    }

    public SharedPreferences getPreferences() {
        return this.preferences;
    }


    public void setThemeColor(String data) {
        this.editor.putString(THEME_COLOR, data);
        this.editor.commit();
    }

    public String getThemeColor() {
        return getPreferences().getString(THEME_COLOR, "#F55933");
    }


}
