
package com.client.lrms.activity;

import com.client.lrms.R;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.PreordainInfo;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orderdetail);

        setTitleText(R.string.title_orderdetail);

        doViewReq();
    }

    private void setupView(PreordainInfo prod) {
        findViewById(R.id.ll_parent).setVisibility(View.VISIBLE);
        TextView tvReceipt = (TextView) findViewById(R.id.tv_receipt);
        tvReceipt.setText(prod.getReceipt());

        TextView tvDate = (TextView) findViewById(R.id.tv_datetime);
        TextView tvSeat = (TextView) findViewById(R.id.tv_seat);

        tvDate.setText(prod.getOnDate() + "   " + prod.getBegin() + "——" + prod.getEnd());
        tvSeat.setText(prod.getLocation());
    }

    private void doViewReq() {
        String id = getIntent().getStringExtra("id");
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_VIEW, id);
        dataEnginer.request();
    }

    @Override
    public void onResponseSucess(String method, Result result) {
        dismissLoading();
        if (Constants.METHOD_VIEW.equals(method)) {
            PreordainInfo info = (PreordainInfo) result.getBody();
            if (info != null) {
                setupView(info);
            }
        }

    }

}
