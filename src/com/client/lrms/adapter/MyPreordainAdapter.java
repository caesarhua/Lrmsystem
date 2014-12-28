
package com.client.lrms.adapter;

import com.client.lrms.R;
import com.client.lrms.view.CustomDialog;
import com.client.lrms.view.LoadingDialog;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.BaseResponse;
import com.otn.lrms.util.entity.HistoryResp.History;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.HistoryStatus;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.DataReqObserver;
import com.otn.lrms.util.net.Result;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author LuYozone
 * @version [版本号, 2013-5-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyPreordainAdapter extends BaseAdapter implements DataReqObserver {
    private List<History> preordainInfos = new ArrayList<History>();

    private LayoutInflater mInflater;

    private int cancelId = -1;

    private LoadingDialog loadingDialog;

    private Context context;

    public MyPreordainAdapter(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addAll(List<History> preordainInfos) {
        this.preordainInfos.addAll(preordainInfos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return preordainInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return preordainInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final History info = preordainInfos.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.preordain_info_item, null);
            holder.dateText = (TextView) convertView.findViewById(R.id.prordainDate);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.cancelBtn = (Button) convertView.findViewById(R.id.cancel);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.dateText.setText(info.getDate());
        holder.address.setText(info.getLoc());
        holder.cancelBtn.setText(HistoryStatus.getName(info.getStat()));
        holder.cancelBtn.setBackgroundResource(HistoryStatus.getIcon(info.getStat()));
        if ("RESERVE".equals(info.getStat())) {
            holder.cancelBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    cancelId = position;
                    doCancelReq(info.getId());
                }
            });

        } else {
            holder.cancelBtn.setOnClickListener(null);
        }

        return convertView;
    }

    private void doCancelReq(final String id) {

        View view = View.inflate(context, R.layout.layout_date_select, null);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_select_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_select_cancel);
        TextView tvError = (TextView) view.findViewById(R.id.layouts_info);
        tvError.setText("确认取消已预约的座位？");
        final CustomDialog dialog = new CustomDialog(context);
        dialog.setContentView(view);
        btnConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

                showLoading();
                DataEnginer dataEnginer = new DataEnginer(MyPreordainAdapter.this);
                dataEnginer.setUrl(Constants.METHOD_CANCEL, id);
                dataEnginer.request();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    static class ViewHolder {
        TextView dateText;

        TextView startTime;

        TextView stopTime;

        TextView address;

        Button cancelBtn;
    }

    @Override
    public void onResponseSucess(String method, Result result) {
        dismissLoading();
        if (Constants.METHOD_CANCEL.equals(method)) {
            // 取消
            BaseResponse<String> resp = (BaseResponse<String>) result.getBody();
            preordainInfos.remove(cancelId);
            notifyDataSetChanged();
            CustomToast.shortShow(context.getString(R.string.cancel_sucess));

        }

    }

    @Override
    public void onResponseError(String method, Result result) {
        CustomToast.shortShow(result.getHead().getMessage());
        dismissLoading();
    }

    protected void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
        }
        loadingDialog.show();
    }

    protected void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
