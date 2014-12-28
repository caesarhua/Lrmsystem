
package com.otn.lrms.util.entity;

import java.util.List;

public class HistoryResp {

    private List<History> reservations;

    public List<History> getReservations() {
        return reservations;
    }

    public void setReservations(List<History> reservations) {
        this.reservations = reservations;
    }

    public static class History {

        private String id;

        private String date;

        private String loc;

        private String stat;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

    }

}
