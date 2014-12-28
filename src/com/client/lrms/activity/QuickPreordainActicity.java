
package com.client.lrms.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;

import com.client.lrms.R;
import com.client.lrms.adapter.QuickPreordainAdapter;
import com.client.lrms.view.CustomSpinner;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.AllowedResp;
import com.otn.lrms.util.entity.BaseResponse;
import com.otn.lrms.util.entity.FiltersInfo.Buildings;
import com.otn.lrms.util.entity.FiltersResp;
import com.otn.lrms.util.entity.QuickBook;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.LogUtil;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickPreordainActicity extends BaseNoTitleActivity implements OnClickListener {

    private static final String TAG = QuickPreordainActicity.class.getSimpleName();

    // private Spinner spVenues;

    // private CustomSpinner spTime;

    private String building = "";

    private QuickPreordainAdapter hoursAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_quick);

        initViews();

        doBuildingsReq();
        doAllowedHoursReq();

    }

    public void doBuildingsReq() {
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_FILTERS);
        dataEnginer.request();

    }

    // 快速预约
    public void doQuickBookReq() {
        showLoading();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("token", Config.getToken()));
        nameValuePairs.add(new BasicNameValuePair("building", building));
        nameValuePairs.add(new BasicNameValuePair("hour", hoursAdapter.getHour()));
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_QUCIKBOOK);
        dataEnginer.setNameValuePairs(nameValuePairs);
        dataEnginer.request();
    }

    // 允许最大的预约时长
    private void doAllowedHoursReq() {
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_ALLOWEDHOURS);
        dataEnginer.request();
    }

    private void initViews() {
        String[] venues = new String[] {
            "所有场馆"
        };

        final CustomSpinner spVenues = (CustomSpinner) findViewById(R.id.sp_venues);
        spVenues.initSpinner(this, venues, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spVenues.hideButton();

        // spTime = (CustomSpinner)findViewById(R.id.sp_time);
        //
        // spTime.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // doAllowedHoursReq();
        // }
        // });
        // spTime.hideButton();

        findViewById(R.id.btn_qucik_sumbit).setOnClickListener(this);
    }

    private void setupBuildings(final List<Buildings> buildings) {

        String[] venues = new String[buildings.size()];
        for (int i = 0; i < venues.length; i++) {
            venues[i] = buildings.get(i).getName();

        }

        building = "" + buildings.get(0).getId();

        final CustomSpinner spVenues = (CustomSpinner) findViewById(R.id.sp_venues);
        spVenues.initSpinner(this, venues, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                building = "" + buildings.get(arg2).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spVenues.hideButton();

    }

    private void setupSpinnerTimer(AllowedResp resp) {

        int hours = 0;
        int maxFree = 0;
        try {
            hours = Integer.valueOf(resp.getHours());
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        try {
            maxFree = Integer.valueOf(resp.getMaxFree());
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        String[] times = new String[hours];
        for (int i = 0; i < hours; i++) {
            times[i] = getString(R.string.allowedhours, (i + 1));
        }

        List<String> hoursList = Arrays.asList(times);

        GridView gvHours = (GridView) findViewById(R.id.gv_hours);
        hoursAdapter = new QuickPreordainAdapter(hoursList, maxFree);
        gvHours.setAdapter(hoursAdapter);

        // spTime.initSpinner(this, times, new OnItemSelectedListener() {
        //
        // @Override
        // public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
        // long arg3) {
        // hour = String.valueOf(arg2 + 1);
        // }
        //
        // @Override
        // public void onNothingSelected(AdapterView<?> arg0) {
        //
        // }
        // });
        // spTime.hideButton();
        // spTime.showSpinner();
        // spTime.setOnClickListener(null);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_qucik_sumbit) {
            if (hoursAdapter == null || "0".equals(hoursAdapter.getHour())) {
                CustomToast.shortShow(getString(R.string.hour_forbid));
            } else {
                doQuickBookReq();
            }
        }

    }

    @Override
    protected void refreshAfterDialog() {

        if (getParent() instanceof MainActivity) {
            ((MainActivity) getParent()).gotoHome();
        }
    }

    @Override
    public void onResponseSucess(String method, Result result) {
        dismissLoading();
        if (Constants.METHOD_FILTERS.equals(method)) {
            FiltersResp resp = (FiltersResp) result.getBody();
            if (resp.getData() != null) {
                setupBuildings(resp.getData().getBuildings());
            } else {
                CustomToast.shortShow(getString(R.string.data_error));
            }
        } else if (Constants.METHOD_ALLOWEDHOURS.equals(method)) {
            BaseResponse<AllowedResp> resp = (BaseResponse<AllowedResp>) result.getBody();
            if (resp.getData() != null) {
                setupSpinnerTimer(resp.getData());
            } else {
                CustomToast.shortShow(getString(R.string.data_error));
            }

        } else if (Constants.METHOD_QUCIKBOOK.equals(method)) {
            dismissLoading();
            BaseResponse<QuickBook> respone = (BaseResponse<QuickBook>) result.getBody();
            if (respone.getData() != null && respone.getData().getReservation() != null) {
                sumbitSuccessShow(respone.getData().getReservation());
            } else {
                sumbitFailShow(respone.getMessage());
            }
        }
    }

}
