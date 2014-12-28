
package com.client.lrms.adapter;

import com.client.lrms.R;
import com.otn.lrms.util.entity.LayoutsResp.Layout.LayoutInfo;
import com.otn.lrms.util.helper.SeatStatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LayoutAdapter extends BaseAdapter {
    private List<LayoutInfo> layouts;

    private LayoutInflater mInflater;

    private int[] icon = new int[] {
            R.drawable.seat_01, R.drawable.seat_02, R.drawable.seat_03, R.drawable.seat_04
    };

    public LayoutAdapter(Context context, List<LayoutInfo> layouts) {
        this.layouts = layouts;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return layouts.size();
    }

    @Override
    public LayoutInfo getItem(int position) {
        return layouts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        LayoutInfo info = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.seat_item, null);
            holder.seatState = (FrameLayout) convertView.findViewById(R.id.ll_seat_bg);
            holder.seatName = (TextView) convertView.findViewById(R.id.seat_name);
            holder.seatRoom = (TextView) convertView.findViewById(R.id.seat_room);
            holder.ivWindow = (ImageView) convertView.findViewById(R.id.iv_window);
            holder.ivPower = (ImageView) convertView.findViewById(R.id.iv_power);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int icon = SeatStatus.getStatus(info.getStatus().toUpperCase());
        holder.seatState.setBackgroundResource(icon);
        holder.seatName.setText(info.getName());
        // holder.seatRoom.setText(info.getLongName());
        // holder.ivWindow.setVisibility(info.isWindow() ? View.VISIBLE :
        // View.INVISIBLE);
        // holder.ivPower.setVisibility(info.isPower() ? View.VISIBLE :
        // View.INVISIBLE);
        return convertView;
    }

    private static class ViewHolder {
        FrameLayout seatState;

        TextView seatName;

        TextView seatRoom;

        ImageView ivWindow;

        ImageView ivPower;
    }
}
