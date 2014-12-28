
package com.otn.lrms.util;

import android.os.Environment;

import com.otn.lrms.util.helper.SharedPreferencesHelper;

public class Config {

    public static final boolean IS_PRINT_LOG = false;

    public static boolean is_offline = false;

    public static final String LOG_DIR = "LRMSystem/log";

    public static final String LOGO_DIR = "LRMSystem/logo";

    public static final String LOGO_PATH = Environment.getExternalStorageDirectory() + "/"
            + LOGO_DIR + "/logo_s.png";

    public static final String APK_DIR = "LRMSystem/download";

    // public static String UPGRADE_URL =
    // "http://192.168.1.105:8080/manager/version.txt";

    public static String UPGRADE_URL = "http://www.leosys.com.cn/app/lib/version.txt";

    private static String hostname = "211.80.179.233:80";

    public static String getHost() {
         return "http://" + Config.hostname;
//        return SharedPreferencesHelper.getString("host");
    }

    public static void setHost(String hostname) {
        // String serverUrl = "http://" + Config.hostname;
        String serverUrl = "http://" + hostname;
        SharedPreferencesHelper.setString("host", serverUrl);
    }

    public static String getToken() {
        return SharedPreferencesHelper.getString("token");
    }

    public static void setToken(String token) {
        SharedPreferencesHelper.setString("token", token);
    }

}
