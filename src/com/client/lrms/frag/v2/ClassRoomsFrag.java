
package com.client.lrms.frag.v2;

import com.client.lrms.R;
import com.client.lrms.adapter.RoomsAdapter;
import com.client.lrms.frag.BaseFragment;
import com.client.lrms.frag.SeatsCallBack;
import com.client.lrms.view.CustomSpinner;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.FiltersInfo.Buildings;
import com.otn.lrms.util.entity.FiltersInfo.Rooms;
import com.otn.lrms.util.entity.FiltersResp;
import com.otn.lrms.util.entity.RoomsResp.RoomInfo;
import com.otn.lrms.util.entity.SeatsResp;
import com.otn.lrms.util.entity.SeatsResp.SeatsInfo;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassRoomsFrag extends BaseFragment {

    public List<Rooms> rooms = null;
    private String bulidingid = "";
    private String date;
    private List<Buildings> builds;
    private RoomsAdapter hoursAdapter;

    public static ClassRoomsFrag instance(String endtime) {
        ClassRoomsFrag f = new ClassRoomsFrag();
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
        return inflater.inflate(R.layout.frag_rooms, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        doBuildingsReq();
    }

    private void initView() {
        CustomSpinner spVenues = (CustomSpinner) getView().findViewById(R.id.sp_building);
        spVenues.setIndex("请选择场馆");

        spVenues = (CustomSpinner) getView().findViewById(R.id.sp_date);
        spVenues.setIndex("请选择日期");

        getView().findViewById(R.id.btn_submit).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (hoursAdapter != null) {
                    hoursAdapter.clear();
                }

                if (isCurrentDate(date)) {

                    doRoomsReq();
                } else if (rooms != null) {

                    List<RoomInfo> roomInfos = new ArrayList<RoomInfo>();
                    for (int i = 0; i < rooms.size(); i++) {
                        Rooms data = rooms.get(i);

                        if (bulidingid.equals("" + data.getBuildId())) {
                            RoomInfo roomInfo = new RoomInfo();

                            roomInfo.setName(data.getName());
                            roomInfo.setId("" + data.getId());
                            roomInfo.setBuilding("" + data.getBuildId());
                            roomInfo.setFloor(data.getFloor());

                            roomInfos.add(roomInfo);
                        }

                    }
                    if (roomInfos.isEmpty()) {
                        CustomToast.shortShow("没有符合条件的教室");
                    } else {
                        setupView(roomInfos);
                    }
                }

            }
        });
    }

    private boolean isCurrentDate(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        String now = simpleDateFormat.format(nowDate);
        return now.equals(date);
    }

    private void doRoomsReq() {
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_ROOMSTATS2, bulidingid);
        dataEnginer.request();

    }

    public void doBuildingsReq() {
        showLoading();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_FILTERS);
        dataEnginer.request();

    }

    private void setupView(List<RoomInfo> roomInfos) {

        GridView gvHours = (GridView) getView().findViewById(R.id.gv_endtimes);

        hoursAdapter = new RoomsAdapter(roomInfos);
        hoursAdapter.setSeatsCallBack((SeatsCallBack) getActivity());
        gvHours.setAdapter(hoursAdapter);

    }

    private void setupBuildings(final List<Buildings> buildings) {

        String[] venues = new String[buildings.size()];
        for (int i = 0; i < venues.length; i++) {
            venues[i] = buildings.get(i).getName();

        }

        final CustomSpinner spVenues = (CustomSpinner) getView().findViewById(R.id.sp_building);
        spVenues.initSpinner(getActivity(), venues, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                bulidingid = String.valueOf(buildings.get(arg2).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spVenues.hideButton();

    }

    private void setupSpinnerTimer(final List<String> dates) {

        String[] venues = new String[dates.size()];
        for (int i = 0; i < venues.length; i++) {
            venues[i] = dates.get(i);

        }

        final CustomSpinner spVenues = (CustomSpinner) getView().findViewById(R.id.sp_date);
        spVenues.initSpinner(getActivity(), venues, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                date = dates.get(arg2);
                ((SeatsCallBack) getActivity()).saveOnDate(date);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spVenues.hideButton();

    }

    @Override
    public void onResponseSucess(String method, Result result) {
        if (Constants.METHOD_FILTERS.equals(method)) {
            dismissLoading();

            FiltersResp resp = (FiltersResp) result.getBody();
            if (resp.getData() != null) {
                setupBuildings(resp.getData().getBuildings());
                setupSpinnerTimer(resp.getData().getDates());
                rooms = resp.getData().getRooms();
                builds = resp.getData().getBuildings();
                bulidingid = "" + builds.get(0).getId();
            } else {
                CustomToast.shortShow(resp.getMessage());
            }
        } else if (Constants.METHOD_ROOMSTATS2.equals(method)) {

            dismissLoading();

            // String jsonString =
            // ParseHelper.getInstance().getAssets("rooms.xml");
            SeatsResp resp = (SeatsResp) result.getBody();
            if (resp.getData() != null) {

                List<RoomInfo> roomInfos = new ArrayList<RoomInfo>();
                for (int i = 0; i < resp.getData().size(); i++) {
                    SeatsInfo data = resp.getData().get(i);

                    RoomInfo roomInfo = new RoomInfo();

                    roomInfo.setName(data.getRoom());
                    roomInfo.setId("" + data.getRoomId());
                    roomInfo.setFree(data.getFree());
                    roomInfo.setFloor(data.getFloor());

                    roomInfos.add(roomInfo);
                }

                setupView(roomInfos);

            } else {
                CustomToast.shortShow(resp.getMessage());
            }

        }

    }

}
