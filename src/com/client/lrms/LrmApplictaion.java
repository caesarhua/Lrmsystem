
package com.client.lrms;

import com.otn.lrms.util.helper.SharedPreferencesHelper;

import android.app.Application;
import android.content.Context;

public class LrmApplictaion extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SharedPreferencesHelper.init();
    }

    public static Context getContext() {
        return context;
    }

}
