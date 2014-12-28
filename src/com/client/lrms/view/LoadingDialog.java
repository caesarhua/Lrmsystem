/*
 * 文 件 名:  OperationProgressDialog.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  wKF49931
 * 修改时间:  2011-12-26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.client.lrms.view;

import com.client.lrms.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author wKF49931
 * @version [版本号, 2011-12-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LoadingDialog extends ProgressDialog {

    private String progressText;

    private boolean mCanBack = true;

    private Context mContext;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, String text) {
        super(context);
        this.progressText = text;
    }

    public LoadingDialog(Context context, String text, boolean canBack) {
        super(context);
        this.progressText = text;
        mContext = context;
        mCanBack = canBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progress_dialog);
    }

    public void setDismissListener(OnDismissListener listener) {
        setOnDismissListener(listener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!mCanBack) {
            Toast.makeText(mContext, "操作正在进行，请稍后！", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
