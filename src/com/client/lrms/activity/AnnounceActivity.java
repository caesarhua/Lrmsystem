
package com.client.lrms.activity;

import com.client.lrms.R;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnnounceActivity extends BaseActivity {

    private TextView tvAbout;

    private ImageView errorIcon;

    private LinearLayout allPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_announce);

        setTitleText(R.string.about);

        tvAbout = (TextView) findViewById(R.id.tv_announce);
        errorIcon = (ImageView) findViewById(R.id.error_icon);
        allPage = (LinearLayout) findViewById(R.id.all_page);
        errorIcon.setVisibility(View.GONE);
        tvAbout.setVisibility(View.GONE);

        allPage.setOnClickListener(null);

        doannounceReq();
    }

    /**
     * <请求数据> <请求关于本产品的数据> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @author LuYozone
     */
    private void doannounceReq() {
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_ANNOUNCE);
        dataEnginer.request();
    }

    @Override
    public void onResponseError(String method, Result result) {
        super.onResponseError(method, result);
        errorIcon.setVisibility(View.VISIBLE);
        allPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doannounceReq();
            }
        });
    }

    @Override
    public void onResponseSucess(String method, Result result) {
        if (Constants.METHOD_ANNOUNCE.equals(method)) {
            dismissLoading();
            String announce = (String) result.getBody();
            tvAbout.setVisibility(View.VISIBLE);
            tvAbout.setText(Html.fromHtml(announce));
        }
    }
}
