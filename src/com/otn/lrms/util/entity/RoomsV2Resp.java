
package com.otn.lrms.util.entity;

import java.io.Serializable;
import java.util.List;

public class RoomsV2Resp {

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

        private String roomId;

        private String room;

        private int free;

        private int totalSeats;

        private int floor;

        private String reserved;

        private String inUse;
        private String away;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getTotalSeats() {
            return totalSeats;
        }

        public void setTotalSeats(int totalSeats) {
            this.totalSeats = totalSeats;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public String getReserved() {
            return reserved;
        }

        public void setReserved(String reserved) {
            this.reserved = reserved;
        }

        public String getInUse() {
            return inUse;
        }

        public void setInUse(String inUse) {
            this.inUse = inUse;
        }

        public String getAway() {
            return away;
        }

        public void setAway(String away) {
            this.away = away;
        }

    }

}
