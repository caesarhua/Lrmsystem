
package com.client.lrms.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.TextView;

import com.client.lrms.R;
import com.client.lrms.frag.LayoutFrag;
import com.client.lrms.frag.RoomsFrag;
import com.client.lrms.frag.SeatsCallBack;
import com.client.lrms.frag.v2.ClassRoomsFrag;
import com.client.lrms.view.CustomDialog;
import com.client.lrms.view.CustomSpinner;
import com.client.lrms.view.LoadingDialog;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.EndTimesForSeatResp;
import com.otn.lrms.util.entity.FreeBookResp;
import com.otn.lrms.util.entity.FreeTimes.Times;
import com.otn.lrms.util.entity.LayoutsResp.Layout.LayoutInfo;
import com.otn.lrms.util.entity.PreordainInfo;
import com.otn.lrms.util.entity.RoomsResp.RoomInfo;
import com.otn.lrms.util.entity.StartTimesForSeatResp;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.DataReqObserver;
import com.otn.lrms.util.net.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 自选座位
 */
public class SeatsPreordainActivity extends FragmentActivity implements SeatsCallBack,
        DataReqObserver {

    private List<String> toggleTab = new ArrayList<String>();

    private LoadingDialog loadingDialog;

    private DataEnginer dataEnginer;

    private int endtime;

    private Times startData;

    private Times endData;

    private CustomSpinner spEnd;

    private TextView preordainInfo;

    private CustomSpinner spStart;

    private String onDate;

    private String seatId;

    private String roomName;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_custom);
        toggleView(R.id.ll_content_parent, new ClassRoomsFrag());
    }

    public void toggleView(int containerViewId, Fragment fragment) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(fragment.toString());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (frag == null) {
            hideFragment(ft);
            ft.add(containerViewId, fragment, fragment.toString());
            // ft.addToBackStack(fragment.toString());
            toggleTab.add(fragment.toString());
        } else {
            hideFragment(ft);
            ft.show(frag);
        }

        ft.commit();
    }

    public void replace(int containerViewId, Fragment fragment) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(fragment.toString());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(fragment.toString());
        ft.replace(containerViewId, fragment);
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        for (int i = 0; i < toggleTab.size(); i++) {
            Fragment hisFragment = getSupportFragmentManager().findFragmentByTag(toggleTab.get(i));
            ft.hide(hisFragment);
        }
    }

    public boolean goBack() {
        if (toggleTab.size() > 1) {
            String tag = toggleTab.get(toggleTab.size() - 1);
            Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(frag);
            toggleTab.remove(tag);

            tag = toggleTab.get(toggleTab.size() - 1);
            frag = getSupportFragmentManager().findFragmentByTag(tag);
            ft.show(frag);

            ft.commit();
            return false;
        } else {
            return true;
        }

    }

    protected void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    protected void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    private void doFreeBookReq() {
        showLoading();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("token", Config.getToken()));
        nameValuePairs.add(new BasicNameValuePair("startTime",
                "now".equals(startData.getId()) ? "-1" : startData.getId()));
        nameValuePairs.add(new BasicNameValuePair("endTime", endData.getId()));
        nameValuePairs.add(new BasicNameValuePair("seat", seatId));
        nameValuePairs.add(new BasicNameValuePair("date", onDate));
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_FREEBOOK);
        dataEnginer.setNameValuePairs(nameValuePairs);
        dataEnginer.request();

    }

    @Override
    public void gotoRooms(int endtime) {
        this.endtime = endtime;

        toggleView(R.id.ll_content_parent, RoomsFrag.instance(String.valueOf(endtime)));

    }

    @Override
    public void gotoLayout(RoomInfo roomInfo) {

        roomName = roomInfo.getName();
        toggleView(R.id.ll_content_parent, LayoutFrag.instance(roomInfo, this.onDate));

    }

    @Override
    public void saveOnDate(String onDate) {
        this.onDate = onDate;

    }

    @Override
    public void freebook(final LayoutInfo layoutInfo) {

        // this.seat = layoutInfo.getId();
        //
        // View view = View.inflate(this, R.layout.layout_date_select, null);
        // Button btnConfirm = (Button)view.findViewById(R.id.btn_select_ok);
        // Button btnCancel = (Button)view.findViewById(R.id.btn_select_cancel);
        // TextView tvError = (TextView)view.findViewById(R.id.layouts_info);
        // tvError.setText("确认预约当前" + layoutInfo.getName() + "座位？");
        // final CustomDialog dialog = new CustomDialog(this);
        // dialog.setContentView(view);
        // btnConfirm.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // dialog.dismiss();
        // doFreeBookReq();
        // }
        // });
        // btnCancel.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // dialog.dismiss();
        // }
        // });
        // dialog.show();
        //
        selectStartTime(layoutInfo);

    }

    private void doStartTimeReq() {
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_STARTTIMESFORSEAT, seatId, onDate);
        dataEnginer.request();

    }

    private void doFreeEndTimeReq() {
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_ENDTIMESFORSEAT, seatId, onDate, startData.getId());
        dataEnginer.request();

    }

    private void selectStartTime(final LayoutInfo layoutInfo) {
        seatId = layoutInfo.getId();
        startData = null;
        endData = null;
        View view = View.inflate(this, R.layout.layout_book_confirm, null);
        final CustomDialog dialg = new CustomDialog(this);
        dialg.setContentView(view);

        preordainInfo = (TextView) view.findViewById(R.id.address_info);
        preordainInfo.setText("当前教室: " + roomName + ", 座位号: " + layoutInfo.getName());

        spStart = (CustomSpinner) view.findViewById(R.id.sp_start);
        spStart.setIndex("选择开始时间");
        spStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                doStartTimeReq();
            }
        });

        spEnd = (CustomSpinner) view.findViewById(R.id.sp_end);

        initSpinnerEndtime();

        view.findViewById(R.id.btn_select_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (startData == null) {
                    CustomToast.shortShow("请选择开始时间");
                } else if (endData == null) {
                    CustomToast.shortShow("请选择结束时间");
                } else {
                    doFreeBookReq();
                    dialg.dismiss();
                }
            }
        });

        view.findViewById(R.id.btn_select_cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialg.dismiss();
            }
        });

        dialg.show();
    }

    private void setupSpinnerEndtime(final List<Times> ends) {

        String[] startArr = new String[ends.size()];
        for (int i = 0; i < ends.size(); i++) {
            startArr[i] = ends.get(i).getValue();
        }

        spEnd.initSpinner(this, startArr, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                endData = ends.get(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spEnd.hideButton();
        spEnd.showSpinner();
        spEnd.setOnClickListener(null);

    }

    private void setupSpinnerStarttime(final List<Times> starts) {

        String[] startArr = new String[starts.size()];
        for (int i = 0; i < starts.size(); i++) {
            startArr[i] = starts.get(i).getValue();
        }

        spStart.initSpinner(this, startArr, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (startData != null && !starts.get(arg2).getId().equals(startData.getId())) {
                    // 重新选择开始时间后，结束时间要初始化
                    endData = null;
                    initSpinnerEndtime();
                }
                startData = starts.get(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spStart.hideButton();
        spStart.showSpinner();
        spStart.setOnClickListener(null);

    }

    private void initSpinnerEndtime() {

        spEnd.setIndex("选择结束时间");

        spEnd.showButton();

        spEnd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (startData == null) {
                    CustomToast.longtShow("请先选择开始时间");
                } else {
                    doFreeEndTimeReq();
                }
            }
        });

    }

    /**
     * 占位成功的提示框
     */
    protected void sumbitSuccessShow(PreordainInfo info) {
        View view = View.inflate(this, R.layout.layout_success, null);
        Button btnSuccess = (Button) view.findViewById(R.id.btn_success_ok);
        btnSuccess.setVisibility(View.VISIBLE);

        TextView tvReceipt = (TextView) view.findViewById(R.id.tv_receipt);
        tvReceipt.setText(info.getReceipt());

        TextView tvDatetime = (TextView) view.findViewById(R.id.tv_datetime);
        tvDatetime.setText(info.getOnDate() + "  " + info.getBegin() + "-" + info.getEnd());

        TextView tvSeat = (TextView) view.findViewById(R.id.tv_seat);
        tvSeat.setText(info.getLocation());

        final CustomDialog dialog = new CustomDialog(this);
        dialog.setContentView(view);
        btnSuccess.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (getParent() instanceof MainActivity) {
                    ((MainActivity) getParent()).gotoHome();
                }
            }
        });

        dialog.show();
    }

    /**
     * 占位失败的提示框
     */
    protected void sumbitFailShow(String msg) {
        View view = View.inflate(this, R.layout.layout_error, null);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_fail_ok);
        TextView tvError = (TextView) view.findViewById(R.id.tv_resp_error);
        tvError.setText(msg);
        final CustomDialog dialog = new CustomDialog(this);
        dialog.setContentView(view);
        btnConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResponseSucess(String method, Result result) {

        dismissLoading();

        if (Constants.METHOD_FREEBOOK.equals(method)) {

            FreeBookResp resp = (FreeBookResp) result.getBody();
            if (resp.getData() != null) {
                sumbitSuccessShow(resp.getData());
            } else {
                sumbitFailShow(resp.getMessage());
            }
        } else if (Constants.METHOD_STARTTIMESFORSEAT.equals(method)) {

            StartTimesForSeatResp respone = (StartTimesForSeatResp) result.getBody();

            if (respone.getData() != null && respone.getData().getStartTimes() != null
                    && !respone.getData().getStartTimes().isEmpty()) {
                setupSpinnerStarttime(respone.getData().getStartTimes());
            } else {
                CustomToast.shortShow("无可用时间");
            }

        } else if (Constants.METHOD_ENDTIMESFORSEAT.equals(method)) {
            EndTimesForSeatResp respone = (EndTimesForSeatResp) result.getBody();

            if (respone.getData() != null && respone.getData().getEndTimes() != null
                    && !respone.getData().getEndTimes().isEmpty()) {
                setupSpinnerEndtime(respone.getData().getEndTimes());
            } else {
                CustomToast.shortShow("无可用时间");
            }

        }

    }

    @Override
    public void onResponseError(String method, Result result) {
        dismissLoading();
        CustomToast.longtShow(result.getHead().getMessage());
    }

}
