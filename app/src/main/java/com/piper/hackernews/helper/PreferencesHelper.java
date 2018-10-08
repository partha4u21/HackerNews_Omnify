package com.piper.hackernews.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesHelper {

    public static final String preferencesName = "OnePreferences";
    private static SharedPreferences.Editor editor = null;

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(preferencesName, MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static void addPreferences(Context context, HashMap<String, String> kvPair) {
        editor = getEditor(context);
        Iterator iterator = kvPair.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = String.valueOf(entry.getKey());
            Object value = entry.getValue();

            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (int) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (float) value);
            }

            iterator.remove();
        }
        editor.apply();
    }

    public static String getPhoneNumber(Context context){
        return PreferencesHelper.getSharedPreferences(context).getString("phone_number","");
    }

    public static void logout(Context context) {
        editor = context.getSharedPreferences(preferencesName, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
