
package com.otn.lrms.util.entity;

import java.io.Serializable;
import java.util.List;

public class SeatsResp extends BaseResponseHeader {

    private List<SeatsInfo> data;

    public List<SeatsInfo> getData() {
        return data;
    }

    public void setData(List<SeatsInfo> data) {
        this.data = data;
    }

    public static class SeatsInfo implements Serializable {
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
