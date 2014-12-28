
package com.client.lrms.view;

import com.client.lrms.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class RoomsView extends LinearLayout {

    private OnCheckedChangeListener checkedChangeListener;

    public RoomsView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RoomsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private void init() {

    }

    public void setChecked(boolean checked) {

        setBackgroundResource(checked ? R.drawable.normal : R.drawable.hours_selector);
        if (this.checkedChangeListener != null) {
            this.checkedChangeListener.onCheckedChanged(null, checked);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;

    }

}
