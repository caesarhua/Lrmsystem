
package com.otn.lrms.util.entity;

import java.util.List;

/**
 * 自选座位的开始/结束时间
 * 
 * @author wWX173427
 * @version [版本号, 2013-5-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FreeTimes {

    private String msg;

    private List<Times> startTimes;

    private List<Times> endTimes;

    public List<Times> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(List<Times> startTimes) {
        this.startTimes = startTimes;
    }

    public List<Times> getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(List<Times> endTimes) {
        this.endTimes = endTimes;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class Times {
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
