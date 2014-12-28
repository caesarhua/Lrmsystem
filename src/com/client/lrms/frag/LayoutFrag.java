
package com.client.lrms.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.client.lrms.R;
import com.client.lrms.adapter.LayoutAdapter;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.BaseResponse;
import com.otn.lrms.util.entity.LayoutsResp;
import com.otn.lrms.util.entity.LayoutsResp.Layout.LayoutInfo;
import com.otn.lrms.util.entity.RoomsResp.RoomInfo;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.SeatStatus;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LayoutFrag extends BaseFragment {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public static LayoutFrag instance(RoomInfo roomInfo, String date) {
        LayoutFrag f = new LayoutFrag();
        Bundle args = new Bundle();
        args.putSerializable("roomInfo", roomInfo);
        args.putString("date", date);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.seat_page, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        doLayoutsReq();
    }

    private void initView() {
        RoomInfo roomInfo = (RoomInfo) getArguments().getSerializable("roomInfo");
        tvIndex = (TextView) getView().findViewById(R.id.tv_custom_index);
        tvIndex.setText("当前" + roomInfo.getName() + ",请选择座位");

    }

    private void setupView(List<LayoutInfo> layouts) {

        GridView seatPageGridView = (GridView) getView().findViewById(R.id.gv_seat_page);
        LayoutAdapter adapter = new LayoutAdapter(getActivity(), layouts);
        seatPageGridView.setAdapter(adapter);

        seatPageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                LayoutInfo seats = (LayoutInfo) arg0.getAdapter().getItem(arg2);
                if (SeatStatus.FULL.getName().equals(seats.getStatus().toUpperCase())) {
                    CustomToast.shortShow("当前座位不是空闲座位，无法预约");
                } else {
                    ((SeatsCallBack) getActivity()).freebook(seats);
                }
            }
        });

    }

    private void doLayoutsReq() {
        RoomInfo roomInfo = (RoomInfo) getArguments().getSerializable("roomInfo");
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_ROOMLAYOUT, roomInfo.getId(),
                getArguments().getString("date"));
        dataEnginer.request();

    }

    @Override
    public void onResponseSucess(String method, Result result) {
        dismissLoading();

        // String jsonString =
        // ParseHelper.getInstance().getAssets("layouts.xml");
        BaseResponse<LayoutsResp> resp = (BaseResponse<LayoutsResp>) result.getBody();

        if (resp.getData() != null) {

            if (resp.getData().getLayout() == null
                    || resp.getData().getLayout().getLayoutInfo() == null
                    || resp.getData().getLayout().getLayoutInfo().isEmpty()) {
                CustomToast.longtShow("数据获取失败");
                return;
            }

            List<LayoutInfo> layouts = resp.getData().getLayout().getLayoutInfo();
            Collections.sort(layouts, new Comparator<LayoutInfo>() {

                @Override
                public int compare(LayoutInfo lhs, LayoutInfo rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
            setupView(layouts);

        } else {
            CustomToast.longtShow(resp.getMessage());
        }
    }
}
