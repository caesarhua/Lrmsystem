
package com.otn.lrms.util.entity;

public class StartTimesResp {
    private StartTimes startTimes;

    public StartTimes getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(StartTimes startTimes) {
        this.startTimes = startTimes;
    }

    public static class StartTimes {
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
