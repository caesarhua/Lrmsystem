
package com.otn.lrms.util.helper;

import com.client.lrms.LrmApplictaion;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 缓存帮助类
 */
public class SharedPreferencesHelper {
    private static SharedPreferences sharedPreferences;

    /**
     * 初始化缓存（该方法只应在系统启动时调用）
     * 
     * @param context
     */
    public static void init() {
        sharedPreferences = LrmApplictaion.getContext().getSharedPreferences("LRMS",
                Context.MODE_PRIVATE);
    }

    public static int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public static float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0F);
    }

    public static long getLong(String key) {
        return sharedPreferences.getLong(key, 0L);
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setInt(String key, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setFloat(String key, float value) {
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void setLong(String key, long value) {
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void setString(String key, String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setBoolean(String key, boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
