/*
 * 文 件 名:  Logger.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  wKF46829
 * 修改时间:  2011-4-19
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.otn.lrms.util.helper;

import com.otn.lrms.util.Config;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 日志工具类 <功能详细描述>
 * 
 * @author wKF46829
 * @version [版本号, 2011-4-19]
 * @see writeMsg(String msg) : 外部调用的写日志接口
 * @since 初始版本
 */
public class Logger {

    private static final String LOG_TAG = "Logger";

    private Handler handler;

    /**
     * 构造函数
     * 
     * @param: String dir 目录名
     * @return void
     * @exception throws 无
     * @see [类、类#方法、类#成员]
     */
    public Logger(String dir) {

        String dir2 = genDir(dir);
        String fileName = genFileName(dir2);
        HandlerThread handlerThread = new HandlerThread(LOG_TAG);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        handler = new LogHandler(looper, fileName);
    }

    private String genDir(String dir) {
        if (!externalMemoryAvailable()) {
            return null;
        }
        String tempDir = Environment.getExternalStorageDirectory() + "/" + dir;
        File file = new File(tempDir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                tempDir = null;
            }
        }
        return tempDir;
    }

    private static final Boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 生成文件名
     */
    private String genFileName(String dir) {
        if (null != dir) {
            String currentday = DateHelper.getInstance().getCurrentDate();
            String fileName = dir + "/" + currentday + ".txt";

            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        android.util.Log.i(LOG_TAG, "file [" + fileName + "] create ok !");
                        return fileName;
                    }
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.toString());
                }
            } else {
                return fileName;
            }
        }
        return null;
    }

    /**
     * 写入消息到SD卡
     * 
     * @param msg [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void writeLog(String tag, String msg) {
        Message message = new Message();

        msg = DateHelper.getInstance().getCurrentDateTime() + ":" + tag + ":" + msg + "\r\n";

        message.obj = msg;
        handler.handleMessage(message);
    }

    /**
     * 写日志
     * 
     * @param: String msg 日志的内容
     * @return void
     * @exception throws
     * @see
     */
    public void e(String tag, String msg) {
        if (!Config.IS_PRINT_LOG) {
            return;
        }
        Log.e(tag, msg);

        writeLog(tag, msg);
    }

    /**
     * 输出信息
     * 
     * @param: String msg 信息的内容
     * @return void
     * @exception throws
     * @see
     */
    public void i(String tag, String msg) {
        if (!Config.IS_PRINT_LOG) {
            return;
        }
        Log.i(tag, msg);
        writeLog(tag, msg);
    }

    public void d(String tag, String msg) {
        if (!Config.IS_PRINT_LOG) {
            return;
        }
        Log.d(tag, msg);
        writeLog(tag, msg);
    }

    /**
     * 输出警告信息
     * 
     * @param: String msg 信息的内容
     * @return void
     * @exception throws
     * @see
     */
    public void w(String tag, String msg) {
        if (!Config.IS_PRINT_LOG) {
            return;
        }
        Log.w(tag, msg);
        writeLog(tag, msg);
    }

    private class LogHandler extends Handler {
        private File file;

        public LogHandler(Looper looper, String fileName) {
            super(looper);
            if (null != fileName) {
                file = new File(fileName);
            }
        }

        /**
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != file) {
                String message = msg.obj.toString();
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(file, true), 1024 * 9);
                    writer.write(message);
                    writer.newLine();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.toString());
                } finally {
                    if (null != writer) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            Log.e(LOG_TAG, e.toString());
                        }
                    }
                }
            }
        }
    }
}
