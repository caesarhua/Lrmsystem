
package com.otn.lrms.util.entity;

import com.otn.lrms.util.entity.FreeTimes.Times;

import java.util.List;

public class EndTimesForSeatResp extends BaseResponseHeader {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<Times> endTimes;

        public List<Times> getEndTimes() {
            return endTimes;
        }

        public void setEndTimes(List<Times> endTimes) {
            this.endTimes = endTimes;
        }

    }
}
