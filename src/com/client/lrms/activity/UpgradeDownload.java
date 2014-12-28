/*
 * 文 件 名:  OpenUpgradeDialog.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-7-5
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.client.lrms.activity;

import com.client.lrms.R;
import com.client.lrms.view.CustomDialog;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.entity.UpgradeInfo;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.LogUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author cKF46827
 * @version [版本号, 2011-7-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UpgradeDownload {

    private static final String TAG = "OpenUpgradeDialog";

    private static final int PROGRESS_MAX = 100;

    private static final int BUFFER_SIZE = 1024 * 8 * 100;// 16KB

    private Context context;

    private ProgressBar mProgressBar;

    private TextView mProgressText;

    private UpgradeInfo upgradeInfo;

    private DownloadTask downloadTask;

    private CustomDialog dialog;

    private String savePath;

    private String fileName;

    public UpgradeDownload(Context context, UpgradeInfo info) {
        this.context = context;
        this.upgradeInfo = info;
        init();
    }

    private void init() {
        savePath = Environment.getExternalStorageDirectory() + "/" + Config.APK_DIR;

        String url = upgradeInfo.getDownURL();
        fileName = url.split("/")[url.split("/").length - 1];
    }

    @SuppressWarnings("deprecation")
    public final void execute() {
        View view = View.inflate(context, R.layout.layout_update_comfirm, null);
        dialog = new CustomDialog(context);
        dialog.setContentView(view);
        TextView tvMsg = (TextView) view.findViewById(R.id.tv_update_msg);

        final LinearLayout progress = (LinearLayout) view.findViewById(R.id.ll_bar_parent);
        this.mProgressBar = (ProgressBar) view.findViewById(R.id.pb_downloadBar);
        this.mProgressText = (TextView) view.findViewById(R.id.tv_progressText);
        progress.setVisibility(View.INVISIBLE);

        Button btnOk = (Button) view.findViewById(R.id.btn_update_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_update_cancel);

        // 提示信息
        String msg = "检测到新版本,是否下载进行升级?\n\n" + upgradeInfo.getUpdates();

        tvMsg.setText(msg);

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                download();
                v.setEnabled(false);
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
                dialog.dismiss();

            }

        });

        dialog.setCancelable(false);
        dialog.show();

    }

    private void download() {
        downloadTask = new DownloadTask();
        downloadTask.execute();
    }

    private void cancel() {
        if (downloadTask != null) {
            downloadTask.cancel(true);
        }
    }

    private class DownloadTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            FileOutputStream output = null;
            String result = "下载失败";
            try {
                URL urlAddress = new URL(upgradeInfo.getDownURL());

                conn = (HttpURLConnection) urlAddress.openConnection();
                conn.setConnectTimeout(6 * 1000);
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = conn.getInputStream();
                    int totalFileSize = conn.getContentLength();

                    File saveFile = new File(savePath);
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }

                    output = new FileOutputStream(savePath + "/" + fileName);

                    byte[] b = new byte[BUFFER_SIZE];
                    int length = 0;
                    float fileSize = 0;
                    while (!isCancelled() && (length = inputStream.read(b)) != -1) {
                        output.write(b, 0, length);
                        fileSize += length;
                        float gress = (fileSize / totalFileSize);
                        publishProgress((int) (gress * 100));
                        if (gress >= 1.0) {
                            result = "0000";
                        }
                    }
                    if (output != null) {
                        output.flush();
                    }

                }

                conn.disconnect();

            } catch (MalformedURLException e) {
                result = e.toString();
            } catch (IOException e) {
                result = e.toString();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }

                } catch (IOException e) {
                    LogUtil.i(TAG, e.toString());
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            LogUtil.i(TAG, e.toString());
                        }
                    }
                }

            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            if ("0000".equals(result)) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("file://" + savePath + "/" + fileName),
                        "application/vnd.android.package-archive");
                context.startActivity(i);
            } else {
                CustomToast.longtShow(result);
            }
            super.onPostExecute(result);
        }
    }

    /**
     * 设置当前进度
     * 
     * @param currentProgress [当前进度]
     * @return void [返回类型说明]
     */
    private void setProgress(int currentProgress) {
        mProgressBar.setProgress(currentProgress);
        int result = (int) (((float) currentProgress / (float) mProgressBar.getMax()) * PROGRESS_MAX);
        mProgressText.setText(result + "%");
    }

}
