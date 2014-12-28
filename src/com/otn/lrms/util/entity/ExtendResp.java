
package com.otn.lrms.util.entity;

public class ExtendResp extends BaseResponseHeader {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        PreordainInfo reservation;

        public PreordainInfo getReservation() {
            return reservation;
        }

        public void setReservation(PreordainInfo reservation) {
            this.reservation = reservation;
        }

    }
}
