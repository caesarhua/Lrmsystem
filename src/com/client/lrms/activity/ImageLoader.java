
package com.client.lrms.activity;

import com.otn.lrms.util.Config;
import com.otn.lrms.util.helper.LogUtil;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader extends Thread {

    public static final int IO_BUFFER_SIZE = 8 * 1024 * 10;

    public static String LogoDir = Environment.getExternalStorageDirectory() + "/"
            + Config.LOGO_DIR;

    private static String TAG = ImageLoader.class.getSimpleName();

    private Handler handler;

    public ImageLoader(Handler handler) {
        this.handler = handler;
        File file = new File(LogoDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private final void sendNotification(int result) {
        Message msg = handler.obtainMessage();
        msg.what = result;
        msg.sendToTarget();
    }

    @Override
    public void run() {
        super.run();

        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        String urlString = Config.getHost() + "/client/logo_s.png";
        // urlString =
        // "http://c.hiphotos.baidu.com/image/w%3D310/sign=80b043346963f6241c5d3f02b745eb32/5882b2b7d0a20cf4430236d274094b36acaf9929.jpg";
        LogUtil.d("ImageLoader", "image downUrl=" + urlString);

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(6000);

            if (urlConnection.getResponseCode() == 200) {
                File file = new File(Config.LOGO_PATH);
                OutputStream outputStream = new FileOutputStream(file);
                in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
                out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
            }

            return;
        } catch (final IOException e) {
            LogUtil.e(TAG, "Error in downloadBitmap - " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                LogUtil.e(TAG, "Error in downloadBitmap - " + e);
            }
            sendNotification(1);
        }

    }
}
