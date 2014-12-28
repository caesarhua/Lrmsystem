
package com.client.lrms.adapter;

import com.client.lrms.R;
import com.client.lrms.frag.SeatsCallBack;
import com.client.lrms.view.RoomsView;
import com.otn.lrms.util.entity.RoomsResp.RoomInfo;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.List;

public class RoomsAdapter extends BaseAdapter {
    // private int maxFree;

    // private List<String> hours;

    private List<RoomInfo> rooms;

    SparseBooleanArray checkMap = new SparseBooleanArray();

    private SeatsCallBack seatsCallBack;

    public RoomsAdapter(List<RoomInfo> rooms) {
        this.rooms = rooms;

        for (int i = 0; i < rooms.size(); i++) {
            checkMap.put(i, false);
        }
    }

    public void clear() {
        rooms.clear();
        notifyDataSetChanged();
    }

    public void setSeatsCallBack(SeatsCallBack seatsCallBack) {
        this.seatsCallBack = seatsCallBack;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public RoomInfo getItem(int arg0) {
        return rooms.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = (View) View.inflate(parent.getContext(), R.layout.layout_rooms, null);
        final TextView name = (TextView) view.findViewById(R.id.btn_room_name);
        final TextView count = (TextView) view.findViewById(R.id.btn_room_count);
        final RoomsView roomsView = (RoomsView) view.findViewById(R.id.ll_parent);

        name.setText(getItem(position).getFloor() + "F" + " " + getItem(position).getName());
        if (getItem(position).getFree() == -1) {
            count.setVisibility(View.GONE);
        } else {
            count.setVisibility(View.VISIBLE);
            count.setText("当前可用座位数:" + getItem(position).getFree());
        }

        // 大于maxFree不能选择
        // btn.setEnabled((position + 1) <= maxFree);

        // 默认没选中
        roomsView.setChecked(checkMap.get(position));
        roomsView.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMap.put(position, isChecked);
                if (isChecked) {
                    // 保存唯一，互斥
                    checkOther(position);
                    name.setTextColor(parent.getContext().getResources().getColor(R.color.white));
                    if (seatsCallBack != null) {
                        seatsCallBack.gotoLayout(getItem(position));

                    }
                }
            }
        });

        roomsView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                roomsView.setChecked(true);
            }
        });
        if (name.isEnabled()) {
            name.setTextColor(parent.getContext().getResources().getColor(R.color.text_color));
        } else {
            name.setTextColor(Color.rgb(223, 225, 222));
        }

        return view;
    }

    private void checkOther(int current) {
        for (int i = 0; i < rooms.size(); i++) {
            if (current != i) {
                checkMap.put(i, false);
            }

        }
        notifyDataSetChanged();
    }

    public String getHour() {
        for (int i = 0; i < rooms.size(); i++) {
            if (checkMap.get(i)) {
                return String.valueOf(i + 1);
            }
        }
        return "0";
    }

}
