
package com.otn.lrms.util.entity;

import java.io.Serializable;
import java.util.List;

public class RoomsResp {

    private String message;

    private String status;

    private String code;

    private List<RoomInfo> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<RoomInfo> getData() {
        return data;
    }

    public void setData(List<RoomInfo> data) {
        this.data = data;
    }

    public static class RoomInfo implements Serializable {
        /**
         * 注释内容
         */
        private static final long serialVersionUID = 1L;

        private String id;

        private String name;

        private int free = -1;

        private int seatTotal;

        private int floor;

        private String building;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getSeatTotal() {
            return seatTotal;
        }

        public void setSeatTotal(int seatTotal) {
            this.seatTotal = seatTotal;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

    }

}
