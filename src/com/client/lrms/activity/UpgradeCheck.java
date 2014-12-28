
package com.client.lrms.activity;

import com.otn.lrms.util.Config;
import com.otn.lrms.util.entity.UpgradeInfo;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.LogUtil;
import com.otn.lrms.util.helper.ParseHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * <获取版本升级的配置文件> <功能详细描述>
 * 
 * @author eKF73344
 * @version [版本号, 2012-6-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UpgradeCheck extends AsyncTask<Void, Void, UpgradeInfo> {

    private static final String LOG_TAG = UpgradeCheck.class.getSimpleName();

    private static final int HTTPTIMEOUT = 6000;

    private static final String ROOT = "VersionInformation";

    private Context context;

    private boolean isLoading = false;

    public UpgradeCheck(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        setLoading(true);
        super.onPreExecute();
    }

    @Override
    protected UpgradeInfo doInBackground(Void... params) {
        HttpURLConnection conn;
        InputStream inputStream = null;
        UpgradeInfo upgradeInfo = null;
        try {
            conn = (HttpURLConnection) new URL(Config.UPGRADE_URL).openConnection();
            conn.setConnectTimeout(HTTPTIMEOUT);
            conn.connect();
            // inputStream = conn.getInputStream();
            // XmlPullParser parser = Xml.newPullParser();
            // if (inputStream != null)
            // {
            // parser.setInput(inputStream, "utf-8");
            // }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));

                StringBuffer strBuffer = new StringBuffer("");

                String line = null;

                while (!isCancelled() && (line = reader.readLine()) != null) {
                    strBuffer.append(line);
                }

                upgradeInfo = ParseHelper.getInstance().parseVersion(strBuffer.toString());
                LogUtil.i("upgrade check", "upgradeInfo="+upgradeInfo.toString());
            }
        } catch (MalformedURLException e) {
            LogUtil.e(LOG_TAG, e.toString());
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, e.toString());
        } finally {
            // 关闭流
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogUtil.e(LOG_TAG, e.toString());

                }
            }
        }
        return upgradeInfo;

    }

    @Override
    protected void onPostExecute(UpgradeInfo result) {
        super.onPostExecute(result);
        setLoading(false);
        if (result == null || result.getVerNo() == null || "".equals(result.getVerNo())
                || Integer.valueOf(result.getVerNo()) <= getCurrentVerNo()) {
            CustomToast.shortShow("没有检测到新版本");
            return;
        }

        new UpgradeDownload(context, result).execute();
    }

    @Override
    protected void onCancelled() {
        setLoading(false);
        super.onCancelled();
    }

    /**
     * <解析获取的配置文件> <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private UpgradeInfo parserVersionInfo(final XmlPullParser parser) {
        int event;
        Map<String, String> map = new HashMap<String, String>();

        try {
            event = parser.getEventType();
            while (event != XmlResourceParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
                        if (!ROOT.equals(tagName)) {
                            map.put(tagName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = parser.next();// 获取解析下一个事件

            }

            return setUpgradeInfo(map);

        } catch (XmlPullParserException e) {
            LogUtil.e(LOG_TAG, e.toString());
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, e.toString());
        }

        return null;

    }

    /**
     * <保存升级信息> <功能详细描述>
     * 
     * @param map [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private UpgradeInfo setUpgradeInfo(Map<String, String> map) {
        Field[] fields = UpgradeInfo.class.getDeclaredFields();
        UpgradeInfo instance = null;
        try {
            instance = UpgradeInfo.class.newInstance();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (!"serialVersionUID".equals(fields[i].getName())) {
                    String value = map.get(fields[i].getName());
                    field.setAccessible(true);
                    field.set(instance, value);
                }
            }
            LogUtil.i(LOG_TAG, instance.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private int getCurrentVerNo() {

        PackageManager pm = context.getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo info = pm.getPackageInfo(context.getApplicationContext().getPackageName(),
                    0);
            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            LogUtil.e(LOG_TAG, "getVersionCode");
        }
        return versionCode;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

}
