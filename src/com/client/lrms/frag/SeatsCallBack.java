
package com.client.lrms.frag;

import com.otn.lrms.util.entity.LayoutsResp.Layout.LayoutInfo;
import com.otn.lrms.util.entity.RoomsResp.RoomInfo;

public interface SeatsCallBack {
    public void gotoRooms(int endtime);

    public void gotoLayout(RoomInfo roomInfo);

    public void freebook(LayoutInfo layoutInfo);

    public void saveOnDate(String onDate);

}
