
package com.otn.lrms.util.entity;

import java.io.Serializable;
import java.util.List;

public class ReservationsResp extends BaseResponseHeader {

    private List<ReservationsInfo> data;

    public List<ReservationsInfo> getData() {
        return data;
    }

    public void setData(List<ReservationsInfo> data) {
        this.data = data;
    }

    public static class ReservationsInfo implements Serializable {
        /**
         * 注释内容
         */
        private static final long serialVersionUID = 1L;
        private String id;
        private String receipt;
        private String onDate;
        private String seatId;
        private String status;
        private String begin;
        private String end;
        private String actualBegin;
        private String actualEnd;
        private String awayBegin;
        private String awayEnd;
        private String location;
        private String message;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReceipt() {
            return receipt;
        }

        public void setReceipt(String receipt) {
            this.receipt = receipt;
        }

        public String getOnDate() {
            return onDate;
        }

        public void setOnDate(String onDate) {
            this.onDate = onDate;
        }

        public String getSeatId() {
            return seatId;
        }

        public void setSeatId(String seatId) {
            this.seatId = seatId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBegin() {
            return begin;
        }

        public void setBegin(String begin) {
            this.begin = begin;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getActualBegin() {
            return actualBegin;
        }

        public void setActualBegin(String actualBegin) {
            this.actualBegin = actualBegin;
        }

        public String getActualEnd() {
            return actualEnd;
        }

        public void setActualEnd(String actualEnd) {
            this.actualEnd = actualEnd;
        }

        public String getAwayBegin() {
            return awayBegin;
        }

        public void setAwayBegin(String awayBegin) {
            this.awayBegin = awayBegin;
        }

        public String getAwayEnd() {
            return awayEnd;
        }

        public void setAwayEnd(String awayEnd) {
            this.awayEnd = awayEnd;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}
