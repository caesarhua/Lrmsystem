
package com.otn.lrms.util.entity;

public class EndTimesResp {
    private EndTimes endTimes;

    public EndTimes getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(EndTimes endTimes) {
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
