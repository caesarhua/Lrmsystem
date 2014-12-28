
package com.client.lrms.activity;

import android.support.v4.app.FragmentActivity;

import com.client.lrms.view.LoadingDialog;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.net.DataReqObserver;
import com.otn.lrms.util.net.Result;

public class BaseFragmentActivity extends FragmentActivity implements DataReqObserver {
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

    @Override
    public void onResponseSucess(String method, Result result) {

    }

    @Override
    public void onResponseError(String method, Result result) {
        dismissLoading();
        CustomToast.longtShow(result.getHead().getMessage());

    }
}
