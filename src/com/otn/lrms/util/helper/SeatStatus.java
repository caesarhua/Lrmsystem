
package com.otn.lrms.util.helper;

import com.client.lrms.R;

public enum SeatStatus {

    FREE("FREE", R.drawable.seat_01), BOOKED("BOOKED", R.drawable.seat_03), AWAY("AWAY",
            R.drawable.seat_02), IN_USE("IN_USE", R.drawable.seat_04), FULL("FULL",
            R.drawable.seat_03);

    private int status;

    private String name;

    private SeatStatus(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public static int getStatus(String name) {
        for (SeatStatus key : SeatStatus.values()) {
            if (key.getName().equals(name)) {
                return key.status;
            }
        }
        return 0;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}
