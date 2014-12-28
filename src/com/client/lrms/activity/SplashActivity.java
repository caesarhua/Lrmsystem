
package com.client.lrms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.client.lrms.R;

public class SplashActivity extends Activity {

    private Handler imageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new ImageLoader(imageHandler).start();
    }

}
