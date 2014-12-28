
package com.client.lrms.frag;

import com.client.lrms.R;
import com.client.lrms.adapter.EndTimeAdapter;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.BaseResponse;
import com.otn.lrms.util.entity.EndTimeResp;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Deprecated
public class EndTimeFrag extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_endtime, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doEndTimesReq();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("HH:mm");
        String current = sdf.format(new Date());

        tvIndex = (TextView) getView().findViewById(R.id.tv_custom_index);
        tvIndex.setText("当前时间" + current + ",请选择使用结束时间");

    }

    private void setupView(List<String> timeDesc, List<Integer> times) {

        GridView gvHours = (GridView) getView().findViewById(R.id.gv_endtimes);
        EndTimeAdapter hoursAdapter = new EndTimeAdapter(timeDesc, times);
        hoursAdapter.setSeatsCallBack((SeatsCallBack) getActivity());
        gvHours.setAdapter(hoursAdapter);

    }

    private void doEndTimesReq() {
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_ENDTIMESTODAY);
        dataEnginer.request();

    }

    @Override
    public void onResponseSucess(String method, Result result) {
        dismissLoading();

        // String jsonString =
        // ParseHelper.getInstance().getAssets("endtimes.xml");
        BaseResponse<EndTimeResp> resp = (BaseResponse<EndTimeResp>) result.getBody();

        if (resp.getData() != null && resp.getData().getEndTimes() != null) {
            List<Integer> endtimes = resp.getData().getEndTimes();

            if (endtimes.isEmpty()) {
                CustomToast.longtShow("数据获取失败");
                return;
            }

            List<String> timeDesc = new ArrayList<String>();
            for (int i = 0; i < endtimes.size(); i++) {
                Integer time = endtimes.get(i);
                int hour = time / 60;
                int min = time % 60;
                String timeString = zeroPad(hour, 2) + ":" + zeroPad(min, 2);
                timeDesc.add(timeString);
            }

            setupView(timeDesc, endtimes);

        } else {
            CustomToast.longtShow(resp.getMessage());
        }
    }

    /**
     * 前缀补0到指定位数
     * 
     * @param inValue []
     * @param inMinDigits [指定位数]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String zeroPad(int inValue, int inMinDigits) {
        String val = String.valueOf(inValue);

        if (val.length() < inMinDigits) {
            char[] buf = new char[inMinDigits];

            for (int i = 0; i < inMinDigits; i++)
                buf[i] = '0';

            val.getChars(0, val.length(), buf, inMinDigits - val.length());
            val = new String(buf);
        }
        return val;
    }

}
