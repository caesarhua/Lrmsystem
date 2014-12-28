
package com.client.lrms.frag;

import com.client.lrms.R;
import com.client.lrms.adapter.RoomsAdapter;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.RoomsResp;
import com.otn.lrms.util.entity.RoomsResp.RoomInfo;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

@Deprecated
public class RoomsFrag extends BaseFragment {

    public static RoomsFrag instance(String endtime) {
        RoomsFrag f = new RoomsFrag();
        Bundle args = new Bundle();
        args.putString("endtime", endtime);
        f.setArguments(args);
        return f;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_endtime, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        doRoomsReq();
    }

    private void initView() {

        tvIndex = (TextView) getView().findViewById(R.id.tv_custom_index);
        tvIndex.setText("请选择教室");

    }

    private void setupView(List<RoomInfo> rooms) {

        GridView gvHours = (GridView) getView().findViewById(R.id.gv_endtimes);

        RoomsAdapter hoursAdapter = new RoomsAdapter(rooms);
        hoursAdapter.setSeatsCallBack((SeatsCallBack) getActivity());
        gvHours.setAdapter(hoursAdapter);

    }

    private void doRoomsReq() {
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_ROOMSBYTIME, "-1", getArguments().getString("endtime"));
        dataEnginer.request();

    }

    @Override
    public void onResponseSucess(String method, Result result) {
        dismissLoading();

        // String jsonString = ParseHelper.getInstance().getAssets("rooms.xml");
        RoomsResp resp = (RoomsResp) result.getBody();

        if (resp.getData() != null) {
            List<RoomInfo> rooms = resp.getData();

            if (rooms.isEmpty()) {
                CustomToast.longtShow("数据获取失败");
                return;
            }

            setupView(rooms);

        } else {
            CustomToast.longtShow(resp.getMessage());
        }
    }

}
