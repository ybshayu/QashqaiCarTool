package com.qashqai.cartool;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {
    private static final String PREFS_NAME = "NissanCarToolPrefs";
    public static final String KEY_AUTO_HOTSPOT = "auto_hotspot";
    public static final String KEY_BLUETOOTH_RESTART = "bluetooth_restart";
    public static final String KEY_HEADLIGHT_THEME = "headlight_theme";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getPrefs(context).getBoolean(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return getPrefs(context).getString(key, defValue);
    }
}
