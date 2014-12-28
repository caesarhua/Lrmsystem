
package com.otn.lrms.util.entity;

import com.otn.lrms.util.entity.FreeTimes.Times;

import java.util.List;

public class StartTimesForSeatResp extends BaseResponseHeader {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<Times> startTimes;

        public List<Times> getStartTimes() {
            return startTimes;
        }

        public void setStartTimes(List<Times> startTimes) {
            this.startTimes = startTimes;
        }

    }
}
