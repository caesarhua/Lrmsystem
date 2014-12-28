package com.client.lrms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.client.lrms.R;
import com.client.lrms.frag.HomeFrag;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.BaseResponseHeader;
import com.otn.lrms.util.entity.ReservationsResp;
import com.otn.lrms.util.entity.ReservationsResp.ReservationsInfo;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

public class HomeActivity extends BaseFragmentActivity
{

    private ReservationsInfo latestReservationsInfo = null;

    private int isCheckin = -1;

    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);
        replace(new HomeFrag());
    }

    public void replace(Fragment fragment)
    {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(fragment.toString());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(fragment.toString());
        ft.replace(R.id.home_content, fragment);
        ft.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        doReservationsReq();
    }

    private void doReservationsReq()
    {
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_RESERVATIONS);
        dataEnginer.request();

    }

    private boolean isValite(boolean valite)
    {
        findViewById(R.id.btn_checkin).setEnabled(valite);
        //        findViewById(R.id.btn_stop).setEnabled(valite);
        findViewById(R.id.btn_extend).setEnabled(valite);
        findViewById(R.id.btn_leave_back).setEnabled(valite);
        return false;

    }

    // 签到
    public void onClickCheckIn(View view)
    {
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_CHECKIN);
        dataEnginer.request();

    }

    // 暂离/返回
    public void onClickLeave(View view)
    {
        isCheckin = -1;
        if ("AWAY".equalsIgnoreCase(latestReservationsInfo.getStatus()))
        {
            // 如果是暂离状态可以返回签到
            showLoading();
            isCheckin = 1;
            DataEnginer dataEnginer = new DataEnginer(this);
            dataEnginer.setUrl(Constants.METHOD_CHECKIN);
            dataEnginer.request();
        }
        else
        {
            showLoading();
            // 如果是签到状态可以暂离
            DataEnginer dataEnginer = new DataEnginer(this);
            dataEnginer.setUrl(Constants.METHOD_LEAVE);
            dataEnginer.request();
        }

    }

    // 续座
    public void onClickExtend(View view)
    {
        Intent intent = new Intent(this, ExtendTimeActivity.class);
        intent.putExtra("Reservations", latestReservationsInfo);
        startActivity(intent);
    }

    // 结束
    public void onClickStop(View view)
    {
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_STOP);
        dataEnginer.request();

    }

    public void onClickMyYUyue(View view)
    {
        Intent intent = new Intent(this, MyPreordainActivity.class);
        startActivity(intent);
    }

    private void toggleLeaveBack(boolean checkin)
    {
        Button btn = (Button) findViewById(R.id.btn_leave_back);
        btn.setText(checkin ? "暂离" : "返回");


    }

    @Override
    public void onResponseSucess(String method, Result result)
    {
        super.onResponseSucess(method, result);
        dismissLoading();
        if (Constants.METHOD_RESERVATIONS.equals(method))
        {
            ReservationsResp resp = (ReservationsResp) result.getBody();
            if (resp.getData() == null || resp.getData().isEmpty())
            {
                CustomToast.shortShow(resp.getMessage());
            }
            else
            {
                latestReservationsInfo = resp.getData().get(0);
                isValite(true);
                boolean leave = "AWAY".equalsIgnoreCase(latestReservationsInfo.getStatus());
                Button btn = (Button) findViewById(R.id.btn_leave_back);
                btn.setText(leave ? "返回" : "暂离");

                boolean canCheck = "RESERVE".equalsIgnoreCase(latestReservationsInfo.getStatus());
                findViewById(R.id.btn_checkin).setEnabled(canCheck);

                findViewById(R.id.btn_stop).setEnabled(true);
            }
        }
        else
        {
            BaseResponseHeader resp = (BaseResponseHeader) result.getBody();
            CustomToast.longtShow(resp.getMessage());

            if (Constants.METHOD_LEAVE.equals(method))
            {
                // 暂离后
                latestReservationsInfo.setStatus("AWAY");
                toggleLeaveBack(false);
            }
            else if (Constants.METHOD_STOP.equals(method))
            {
                latestReservationsInfo = null;
                isValite(false);
            }
            else if (Constants.METHOD_CHECKIN.equals(method) && isCheckin == 1)
            {
                // 返回
                latestReservationsInfo.setStatus("CHECK_IN");
                toggleLeaveBack(true);
            }
        }
    }

}
