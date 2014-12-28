
package com.client.lrms.activity;

import android.app.ActivityGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.client.lrms.R;
import com.client.lrms.view.CustomDialog;
import com.client.lrms.view.LoadingDialog;
import com.otn.lrms.util.entity.PreordainInfo;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.net.DataReqObserver;
import com.otn.lrms.util.net.Result;

public abstract class BaseNoTitleActivity extends ActivityGroup implements DataReqObserver {
    private static final String TAG = BaseNoTitleActivity.class.getSimpleName();

    private LoadingDialog loadingDialog;

    private int dialogCount = 0;

    protected synchronized void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
        dialogCount++;
    }

    protected synchronized void dismissLoading() {
        if (dialogCount > 1) {
            dialogCount--;
            return;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            dialogCount = 0;
        }
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
                refreshAfterDialog();
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
                refreshAfterDialog();
            }
        });
        dialog.show();
    }

    /**
     * 请求成功弹出的对话框消失后刷新界面
     */
    protected abstract void refreshAfterDialog();

    @Override
    public void onResponseError(String method, Result result) {
        dismissLoading();
        CustomToast.longtShow(result.getHead().getMessage());
    }

    @Override
    public void onResponseSucess(String method, Result result) {

    }

}
