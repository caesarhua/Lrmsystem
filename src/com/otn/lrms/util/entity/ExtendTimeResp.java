
package com.otn.lrms.util.entity;

import java.util.List;

public class ExtendTimeResp extends BaseResponseHeader {

    private Data data;

    public static class Data {

        private List<EndTimes> endTimes;

        public List<EndTimes> getEndTimes() {
            return endTimes;
        }

        public void setEndTimes(List<EndTimes> endTimes) {
            this.endTimes = endTimes;
        }

        public static class EndTimes {
            private String id;
            private String value;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
