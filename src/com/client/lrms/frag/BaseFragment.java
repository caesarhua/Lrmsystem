
package com.client.lrms.frag;

import com.client.lrms.R;
import com.client.lrms.view.LoadingDialog;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.DataReqObserver;
import com.otn.lrms.util.net.Result;

import android.support.v4.app.Fragment;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment implements DataReqObserver {

    private LoadingDialog loadingDialog;

    protected DataEnginer dataEnginer;

    protected TextView tvIndex;

    protected void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    protected void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onResponseError(String method, Result result) {

        dismissLoading();
        CustomToast.longtShow(getString(R.string.data_error));

    }

}
