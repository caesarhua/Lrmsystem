
package com.otn.lrms.util.helper;

import com.client.lrms.R;

public enum HistoryStatus {
    RESERVE("RESERVE", "取消预约", R.drawable.red_btn_selector), CANCEL("CANCEL", "已取消",
            R.drawable.yijiesu), AWAY("AWAY", "暂时离开", R.drawable.yijiesu), CHECK_IN("CHECK_IN",
            "履约中", R.drawable.yijiesu), COMPLETE("COMPLETE", "已履约", R.drawable.yijiesu), INCOMPLETE(
            "INCOMPLETE", "早退", R.drawable.yijiesu), MISS("MISS", "失约", R.drawable.yijiesu), STOP(
            "STOP", "已履约", R.drawable.yijiesu);

    private String key;

    private int icon;

    private String name;

    private HistoryStatus(String key, String name, int icon) {
        this.icon = icon;
        this.key = key;
        this.name = name;
    }

    public static int getIcon(String key) {
        for (HistoryStatus data : HistoryStatus.values()) {
            if (data.getKey().equals(key)) {
                return data.icon;
            }
        }
        return 0;
    }

    public static String getName(String key) {
        for (HistoryStatus data : HistoryStatus.values()) {
            if (data.getKey().equals(key)) {
                return data.name;
            }
        }
        return "";
    }

    public String getKey() {
        return key;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

}
