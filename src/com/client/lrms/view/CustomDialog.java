
package com.client.lrms.view;

import com.client.lrms.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

public class CustomDialog extends Dialog {

    private Context context;

    private int height = 0;

    public CustomDialog(Context context) {
        this(context, R.style.dialog);
        this.context = context;
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        // if (height != 0) {
        // WindowManager.LayoutParams lp = getWindow().getAttributes();
        // lp.height = 600;
        // getWindow().setAttributes(lp);
        // }
        super.show();
    }

    @Override
    public void setContentView(View view) {
        // LinearLayout parent =
        // (LinearLayout)view.findViewById(R.id.ll_dialog_parent);
        // if (parent != null) {
        // for (int i = 0; i < parent.getChildCount(); i++) {
        // View v = parent.getChildAt(i);
        // v.measure(0, 0);
        // height += v.getMeasuredHeight();
        // LayoutParams lp = (LinearLayout.LayoutParams)v.getLayoutParams();
        // height += v.getPaddingBottom() + v.getPaddingTop() + lp.topMargin +
        // lp.bottomMargin;
        // }
        //
        // }
        super.setContentView(view);
    }

}
