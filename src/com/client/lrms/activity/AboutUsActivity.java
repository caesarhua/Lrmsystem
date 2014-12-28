
package com.client.lrms.activity;

import com.client.lrms.R;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.net.Result;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        setTitleText("关于我们");
        initView();

    }

    private void initView() {
        TextView tvAbout = (TextView) findViewById(R.id.tv_about);

        String jsonString = ParseHelper.getInstance().getAssets("about.txt");
        String about;
        try {
            about = new JSONObject(jsonString).getString("about");
            tvAbout.setText(Html.fromHtml(about));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseSucess(String method, Result result) {

    }

}
