
package com.client.lrms.view;

import com.client.lrms.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class CustomSpinner extends FrameLayout {

    private Button btn;

    private Spinner sp;

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        sp = new Spinner(context);
        sp.setBackgroundResource(R.drawable.spinner_bg);
        sp.setVisibility(View.GONE);
        addView(sp);

        btn = new Button(context);
        btn.setBackgroundResource(R.drawable.spinner_bg);
        btn.setVisibility(View.VISIBLE);
        addView(btn);
    }

    public void setIndex(String s) {
        btn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        btn.setText(s);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        this.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    public void hideButton() {
        btn.setVisibility(View.GONE);
        sp.setVisibility(View.VISIBLE);
    }

    public void showButton() {
        btn.setVisibility(View.VISIBLE);
        sp.setVisibility(View.GONE);
    }

    public void initSpinner(Context context, String[] items,
            OnItemSelectedListener spinnerSelectedListener) {
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, items);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(timeAdapter);
        sp.setOnItemSelectedListener(spinnerSelectedListener);

    }

    /** 自动弹出 */
    public void showSpinner() {
        sp.performClick();
    }

}
