
package com.client.lrms.activity;

import com.client.lrms.R;
import com.otn.lrms.util.net.Result;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseActivity extends BaseNoTitleActivity {

    private LinearLayout backBtn;

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_custom_title);
        initTitle();
    }

    private void initTitle() {
        backBtn = (LinearLayout) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();

            }
        });

        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    protected void setTitleBackVisiable(boolean visiable) {
        backBtn.setVisibility(visiable ? View.VISIBLE : View.INVISIBLE);

    }

    protected void setTitleText(String title) {
        tvTitle.setText(title);
    }

    protected void setTitleText(int titleId) {
        tvTitle.setText(titleId);
    }

    @Override
    protected void refreshAfterDialog() {

    }

}
