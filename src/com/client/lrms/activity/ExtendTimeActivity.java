
package com.client.lrms.activity;

import android.os.Bundle;
import android.widget.GridView;

import com.client.lrms.R;
import com.client.lrms.adapter.ExtendTimeAdapter;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.ExtendResp;
import com.otn.lrms.util.entity.ExtendTimeResp;
import com.otn.lrms.util.entity.ExtendTimeResp.Data.EndTimes;
import com.otn.lrms.util.entity.ReservationsResp.ReservationsInfo;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ExtendTimeActivity extends BaseActivity {

    private static final String TAG = ExtendTimeActivity.class.getSimpleName();

    private ExtendTimeAdapter hoursAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_extend);
        initView();
        doTimeExtendReq();
    }

    private void doTimeExtendReq() {

        showLoading();
        ReservationsInfo info = (ReservationsInfo) getIntent().getSerializableExtra("Reservations");
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_TIMEEXTEND, info.getId());
        dataEnginer.request();

    }

    public void doExtendReq(String id) {
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("endTime", id));
        nameValuePairs.add(new BasicNameValuePair("token", Config.getToken()));
        dataEnginer.setUrl(Constants.METHOD_EXTEND);
        dataEnginer.setNameValuePairs(nameValuePairs);
        dataEnginer.request();

    }

    private void initView() {

    }

    private void setupView(List<EndTimes> endTimes) {

        GridView gvHours = (GridView) findViewById(R.id.gv_extendtimes);
        hoursAdapter = new ExtendTimeAdapter(endTimes);
        gvHours.setAdapter(hoursAdapter);

    }

    @Override
    public void onResponseSucess(String method, Result result) {
        super.onResponseSucess(method, result);
        dismissLoading();
        if (Constants.METHOD_TIMEEXTEND.equals(method)) {
            ExtendTimeResp resp = (ExtendTimeResp) result.getBody();
            if (resp.getData() == null) {
                CustomToast.longtShow(resp.getMessage());
            } else {
                setupView(resp.getData().getEndTimes());
            }
        } else if (Constants.METHOD_EXTEND.equals(method)) {
            ExtendResp respone = (ExtendResp) result.getBody();
            if (respone.getData() != null && respone.getData().getReservation() != null) {
                sumbitSuccessShow(respone.getData().getReservation());
            } else {
                sumbitFailShow(respone.getMessage());
            }

        }
    }

    @Override
    protected void refreshAfterDialog() {
        super.refreshAfterDialog();
        finish();
    }
}
