
package com.otn.lrms.util.helper;

import android.widget.Toast;

import com.client.lrms.LrmApplictaion;

/**
 * 公共toast
 * 
 * @author mKF67523
 * @version [版本号, 2012-11-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CustomToast {

    public CustomToast() {
    }

    public static void shortShow(CharSequence charSequence) {
        if (!"".equals(charSequence.toString()) && charSequence != null) {
            Toast.makeText(LrmApplictaion.getContext(), charSequence, Toast.LENGTH_SHORT).show();
        }
    }

    public static void longtShow(CharSequence charSequence) {
        if (!"".equals(charSequence.toString()) && charSequence != null) {
            Toast.makeText(LrmApplictaion.getContext(), charSequence, Toast.LENGTH_LONG).show();
        }
    }
}
