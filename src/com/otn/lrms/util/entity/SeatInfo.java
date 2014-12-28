
package com.otn.lrms.util.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 座位信息
 * 
 * @author wWX173427
 * @version [版本号, 2013-5-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SeatInfo implements Serializable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    private List<Seats> seats;

    private String onDate;

    public List<Seats> getSeats() {
        return seats;
    }

    public void setSeats(List<Seats> seats) {
        this.seats = seats;
    }

    public String getOnDate() {
        return onDate;
    }

    public void setOnDate(String onDate) {
        this.onDate = onDate;
    }

    public static class Seats implements Serializable {
        /**
         * 注释内容
         */
        private static final long serialVersionUID = 1L;

        private int id;

        private String name;

        private String longName;

        private String status;

        private boolean power;

        private boolean window;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isPower() {
            return power;
        }

        public void setPower(boolean power) {
            this.power = power;
        }

        public boolean isWindow() {
            return window;
        }

        public void setWindow(boolean window) {
            this.window = window;
        }
    }

}
