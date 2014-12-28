
package com.otn.lrms.util.helper;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateHelper {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static DateHelper dHelper;

    public synchronized static DateHelper getInstance() {
        if (dHelper == null) {
            dHelper = new DateHelper();
        }
        return dHelper;
    }

    /**
     * 获取现在时间
     * 
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public String getStringDateToDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     * 
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 时间前推或后推分钟,其中mins表示分钟.
     */
    public String getPreTime(String curTime, int mins) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String mydate1 = "";
        try {
            Date date1 = format.parse(curTime);
            long Time = (date1.getTime() / 1000) + mins * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 获取当前日期
     */
    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date());
    }

    /**
     * 获取当前日期及时间
     */
    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(new Date());
    }

}
