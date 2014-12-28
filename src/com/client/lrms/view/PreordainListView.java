
package com.client.lrms.view;

import com.client.lrms.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class PreordainListView extends ListView {

    public PreordainListView(Context context) {
        super(context);
    }

    public PreordainListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreordainListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                int itemNum = pointToPosition(x, y);

                /** 如果点击的是第一项 */
                if (itemNum == 0) {
                    /** 如果只有一项 */
                    if (itemNum == getAdapter().getCount() - 1) {
                        setSelector(R.drawable.list_item_corner_round);
                    } else {
                        setSelector(R.drawable.list_item_corner_round_top);
                    }
                }
                /** 如果点击的是最后一项 */
                else if (itemNum == getAdapter().getCount() - 1) {
                    setSelector(R.drawable.list_item_corner_round_bottom);
                }
                /** 如果点击的是中间项 */
                else {
                    setSelector(R.drawable.list_item_corner_round_center);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
