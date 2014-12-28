
package com.otn.lrms.util.net;

import android.os.AsyncTask;

import com.client.lrms.LrmApplictaion;
import com.client.lrms.R;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.helper.LogUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class HttpTask extends AsyncTask<String, Object, Result> {
    private static final String TAG = HttpTask.class.getSimpleName();

    private IHttpTaskCallBack iHttpTaskCallBack;

    private HttpClient client;

    private boolean isPost = false;

    private List<NameValuePair> nameValuePairs = null;

    public HttpTask(IHttpTaskCallBack iHttpTaskCallBack) {
        this.iHttpTaskCallBack = iHttpTaskCallBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        isPost = (nameValuePairs != null);
    }

    @Override
    protected Result doInBackground(String... params) {
        Result result = null;
        String url = params[0];
        LogUtil.i(TAG, "request url = " + url);

        if (Config.is_offline) {
            result = new Result();
            result.setStatusCode("200");
            return result;
        }

        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 1000 * 60 * 10);
        HttpConnectionParams.setSoTimeout(httpParams, 1000 * 60 * 10);
        client = new DefaultHttpClient(httpParams);
        if (isPost) {
            result = doPost(url);
        } else {
            result = doGet(url);
        }

        return result;
    }

    private Result doGet(String url) {
        Result result = new Result();
        try {

            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            result = parseResponse(response);
        } catch (ClientProtocolException e) {
            LogUtil.e(TAG, e.toString());
        } catch (IOException e) {
            LogUtil.e(TAG, e.toString());
        } catch (IllegalArgumentException e) {
            result.setStatusCode(Constants.HTTP_HOST_NULL);
            LogUtil.e(TAG, e.toString());
        }
        return result;

    }

    private Result doPost(String url) {
        Result result = new Result();

        try {
            HttpPost request = new HttpPost(url);
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            printParams(nameValuePairs);

            HttpResponse response = client.execute(request);
            result = parseResponse(response);

        } catch (ClientProtocolException e) {
            LogUtil.e(TAG, e.toString());
        } catch (IOException e) {
            LogUtil.e(TAG, e.toString());
        } catch (IllegalArgumentException e) {
            result.setStatusCode(Constants.HTTP_HOST_NULL);
            LogUtil.e(TAG, e.toString());
        }

        return result;
    }

    private void printParams(List<NameValuePair> nameValuePairs) {
        for (int i = 0; i < nameValuePairs.size(); i++) {
            NameValuePair temp = nameValuePairs.get(i);
            LogUtil.w(TAG, "request params< " + temp.getName() + " : " + temp.getValue() + " >");
        }

    }

    private Result parseResponse(HttpResponse response) {
        BufferedReader reader = null;
        Result result = new Result();

        int statusCode = response.getStatusLine().getStatusCode();

        LogUtil.w(TAG, "respone statusCode=" + statusCode);

        result.setStatusCode(String.valueOf(statusCode));

        try {
            reader = new BufferedReader(new InputStreamReader(response

            .getEntity().getContent()));

            StringBuffer strBuffer = new StringBuffer("");

            String line = null;

            while (!isCancelled() && (line = reader.readLine()) != null) {
                strBuffer.append(line);
            }
            if (isCancelled()) {
                return null;
            }
            LogUtil.i(TAG, "respone Body =" + strBuffer.toString());
            result.setJsonString(strBuffer.toString());
        } catch (IllegalStateException e) {
            LogUtil.e(TAG, e.toString());
        } catch (IOException e) {
            LogUtil.e(TAG, e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e) {
                    LogUtil.e(TAG, e.toString());
                }
            }
        }
        return result;

    }

    @Override
    protected void onPostExecute(Result result) {
        if (iHttpTaskCallBack != null && !isCancelled()) {
            iHttpTaskCallBack.requestReturned(result);
        }
    }

    public void setNameValuePairs(List<NameValuePair> nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }

}
