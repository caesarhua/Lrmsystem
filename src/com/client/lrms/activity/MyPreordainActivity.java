
package com.client.lrms.activity;

import com.client.lrms.R;
import com.client.lrms.adapter.MyPreordainAdapter;
import com.client.lrms.view.PreordainListView;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.BaseResponse;
import com.otn.lrms.util.entity.HistoryResp;
import com.otn.lrms.util.entity.HistoryResp.History;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

public class MyPreordainActivity extends BaseActivity {
    private PreordainListView preordanList;

    private MyPreordainAdapter adapter;

    private ImageView errorIcon;

    private LinearLayout allPage;

    private DataEnginer dataEnginer;

    private boolean hasNextPager = false;

    private View footView;

    /**
     * 当前页数
     */
    private String pagerCout = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        initView();

        showLoading();
        doHistroyReq();

        errorIcon = (ImageView) findViewById(R.id.error_icon);
        allPage = (LinearLayout) findViewById(R.id.all_page);
        errorIcon.setVisibility(View.GONE);

        allPage.setOnClickListener(null);

    }

    private void doHistroyReq() {
        if (dataEnginer == null) {
            dataEnginer = new DataEnginer(this);
        }
        dataEnginer.setUrl(Constants.METHOD_HISTORY, pagerCout, Constants.NUM_PER_PAGE);
        dataEnginer.request();
    }

    private void initView() {

        preordanList = (PreordainListView) findViewById(R.id.preordain_info);

        adapter = new MyPreordainAdapter(this);
        footView = View.inflate(this, R.layout.layout_progress_dialog, null);
        footView.setVisibility(View.GONE);
        preordanList.addFooterView(footView);
        preordanList.setAdapter(adapter);

        preordanList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                History history = (History) arg0.getAdapter().getItem(arg2);
                Intent i = new Intent(MyPreordainActivity.this, OrderDetailActivity.class);
                i.putExtra("id", history.getId());
                startActivity(i);
            }
        });
        preordanList.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount != 1) {
                    if (hasNextPager) {
                        int currPager = Integer.valueOf(pagerCout) + 1;
                        pagerCout = String.valueOf(currPager);
                        footView.setVisibility(View.VISIBLE);
                        doHistroyReq();
                    }
                }

            }
        });

        findViewById(R.id.ll_list_parent).setVisibility(View.GONE);
    }

    private void setupView(final List<History> infos) {
        if (infos != null && !infos.isEmpty()) {
            findViewById(R.id.ll_list_parent).setVisibility(View.VISIBLE);
            adapter.addAll(infos);

            if (infos.size() < Integer.valueOf(Constants.NUM_PER_PAGE)) {
                hasNextPager = false;
                preordanList.removeFooterView(footView);
            } else {
                hasNextPager = true;
            }
        } else {
            hasNextPager = false;
            preordanList.removeFooterView(footView);
            CustomToast.longtShow("暂无数据");
        }

    }

    @Override
    protected void refreshAfterDialog() {

    }

    @Override
    public void onResponseError(String method, Result result) {
        super.onResponseError(method, result);
        errorIcon.setVisibility(View.VISIBLE);
        allPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                doHistroyReq();
            }
        });
    }

    @Override
    public void onResponseSucess(String method, Result result) {
        if (Constants.METHOD_HISTORY.equals(method)) {
            dismissLoading();
            BaseResponse<HistoryResp> resp = (BaseResponse<HistoryResp>) result.getBody();
            if (resp.getData() != null) {
                setupView(resp.getData().getReservations());
            } else {
                errorIcon.setVisibility(View.VISIBLE);
                allPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoading();
                        doHistroyReq();
                    }
                });
            }
        }
    }

}
