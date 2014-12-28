
package com.otn.lrms.util.helper;

import com.client.lrms.LrmApplictaion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.AllowedResp;
import com.otn.lrms.util.entity.AuthResp;
import com.otn.lrms.util.entity.BaseResponse;
import com.otn.lrms.util.entity.BaseResponseHeader;
import com.otn.lrms.util.entity.EndTimeResp;
import com.otn.lrms.util.entity.EndTimesForSeatResp;
import com.otn.lrms.util.entity.ExtendResp;
import com.otn.lrms.util.entity.ExtendTimeResp;
import com.otn.lrms.util.entity.FailInfo;
import com.otn.lrms.util.entity.FiltersInfo;
import com.otn.lrms.util.entity.FiltersInfo.Buildings;
import com.otn.lrms.util.entity.FiltersInfo.Rooms;
import com.otn.lrms.util.entity.FiltersResp;
import com.otn.lrms.util.entity.FreeBookResp;
import com.otn.lrms.util.entity.FreeTimes;
import com.otn.lrms.util.entity.HistoryResp;
import com.otn.lrms.util.entity.LayoutsResp;
import com.otn.lrms.util.entity.LayoutsResp.Layout.LayoutInfo;
import com.otn.lrms.util.entity.PreordainInfo;
import com.otn.lrms.util.entity.QuickBook;
import com.otn.lrms.util.entity.ReservationsResp;
import com.otn.lrms.util.entity.RoomsResp;
import com.otn.lrms.util.entity.SeatInfo;
import com.otn.lrms.util.entity.SeatsResp;
import com.otn.lrms.util.entity.StartTimesForSeatResp;
import com.otn.lrms.util.entity.UpgradeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParseHelper {
    private static final String TAG = ParseHelper.class.getSimpleName();

    private static ParseHelper instance;

    public synchronized static ParseHelper getInstance() {
        if (instance == null) {
            instance = new ParseHelper();
        }
        return instance;
    }

    public Object parseResp(String method, String jsonString) {

        Object resp = null;
        if (Constants.METHOD_FILTERS.equals(method)) {
            resp = parseBuildings(jsonString);

        } else if (Constants.METHOD_ALLOWEDHOURS.equals(method)) {
            resp = parseAllowedHours(jsonString);

        } else if (Constants.METHOD_QUCIKBOOK.equals(method)) {

            resp = parseQuickBook(jsonString);

        } else if (Constants.METHOD_FREEBOOK.equals(method)) {

            resp = parseFreeBook(jsonString);

        } else if (Constants.METHOD_STARTTIMESFORSEAT.equals(method)) {

            resp = parseStartTiemsResp(jsonString);

        } else if (Constants.METHOD_ENDTIMESFORSEAT.equals(method)) {
            resp = parseEndTiemsResp(jsonString);

        } else if (Constants.METHOD_ROOMSTATS2.equals(method)) {
            resp = parseSeats2(jsonString);

        } else if (Constants.METHOD_ROOMLAYOUT.equals(method)) {
            resp = parseLayouts(jsonString);

        } else if (Constants.METHOD_CANCEL.equals(method)) {
            resp = parseCancel(jsonString);

        } else if (Constants.METHOD_HISTORY.equals(method)) {
            resp = parseHistoryInfo(jsonString);

        } else if (Constants.METHOD_VIEW.equals(method)) {
            resp = parseViewDetail(jsonString);

        } else if (Constants.METHOD_ANNOUNCE.equals(method)) {
            resp = parseAnnounce(jsonString);

        } else if (Constants.METHOD_AUTH.equals(method)) {
            resp = parseAuth(jsonString);

        } else if (Constants.METHOD_RESERVATIONS.equals(method)) {
            resp = parseReservations(jsonString);

        } else if (Constants.METHOD_TIMEEXTEND.equals(method)) {
            resp = parseExtendTime(jsonString);

        } else if (Constants.METHOD_LEAVE.equals(method)) {
            resp = parseLeave(jsonString);

        } else if (Constants.METHOD_STOP.equals(method)) {
            resp = parseStop(jsonString);

        } else if (Constants.METHOD_CHECKIN.equals(method)) {
            resp = parseCheckIn(jsonString);

        } else if (Constants.METHOD_EXTEND.equals(method)) {
            resp = parseExtend(jsonString);
        }

        return resp;

    }

    public BaseResponse<AuthResp> parseAuth(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        BaseResponse<AuthResp> respone = gson2.fromJson(jsonString,
                new TypeToken<BaseResponse<AuthResp>>() {
                }.getType());

        return respone;
    }

    public SeatsResp parseSeats2(String jsonString) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        SeatsResp respone = gson2.fromJson(jsonString, SeatsResp.class);

        return respone;
    }

    /**
     * 解析自选座位的可预约开始/结束时间
     * 
     * @param tag 开始/结束
     * @param jsonString
     * @return [参数说明]
     * @return List<FreeTimes> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public FreeTimes parseFreeTimes(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        FreeTimes respone = gson2.fromJson(jsonString, new TypeToken<FreeTimes>() {
        }.getType());
        return respone;
    }

    public FiltersResp parseBuildings(String jsonString) {
        FiltersResp respone = new FiltersResp();

        JSONObject result = null;
        try {
            result = new JSONObject(jsonString);
        } catch (JSONException e2) {
            LogUtil.e(TAG, e2.toString());
        }

        if (result == null) {
            return respone;
        }
        try {
            respone.setStatus(result.getString("status"));
        } catch (JSONException e1) {
            LogUtil.e(TAG, e1.toString());
        }
        // try
        // {
        //
        // respone.setMessage(result.getString("msg"));
        // }
        // catch (JSONException e1)
        // {
        // LogUtil.e(TAG, e1.toString());
        // }
        //
        // try
        // {
        // respone.setCode(result.getString("code"));
        // }
        // catch (JSONException e1)
        // {
        // LogUtil.e(TAG, e1.toString());
        // }

        if (!Constants.SUCCESS.equals(respone.getStatus())) {
            return respone;
        }

        JSONObject dataJson = null;
        try {
            dataJson = new JSONObject(jsonString).getJSONObject("data");
        } catch (JSONException e1) {
            LogUtil.e(TAG, e1.toString());
        }

        if (respone.getData() == null) {
            respone.setData(new FiltersInfo());
        }

        // 解析场馆building信息
        try {
            List<Buildings> buildings = new ArrayList<FiltersInfo.Buildings>();
            JSONArray roomsArr = dataJson.getJSONArray("buildings");
            for (int i = 0; i < roomsArr.length(); i++) {
                JSONArray temp = roomsArr.getJSONArray(i);
                Buildings buliding = new Buildings();
                buliding.setId(temp.getInt(0));
                buliding.setName(temp.getString(1));
                buliding.setFloor(temp.getInt(2));
                buildings.add(buliding);
            }
            respone.getData().setBuildings(buildings);
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }

        // 解析房间rooms信息
        try {
            List<Rooms> rooms = new ArrayList<FiltersInfo.Rooms>();
            JSONArray roomsArr = dataJson.getJSONArray("rooms");
            for (int i = 0; i < roomsArr.length(); i++) {
                JSONArray temp = roomsArr.getJSONArray(i);
                Rooms room = new Rooms();
                room.setId(temp.getInt(0));
                room.setName(temp.getString(1));
                room.setBuildId(temp.getInt(2));
                room.setFloor(temp.getInt(3));
                rooms.add(room);
            }
            respone.getData().setRooms(rooms);
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }
        // 解析时长hours信息
        try {
            int hours = dataJson.getInt("hours");
            respone.getData().setHours(hours);
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }

        // 解析时间dates信息
        try {
            JSONArray datesArr = dataJson.getJSONArray("dates");
            List<String> dates = new ArrayList<String>();
            for (int i = 0; i < datesArr.length(); i++) {
                dates.add(datesArr.getString(i));
            }
            respone.getData().setDates(dates);
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }

        return respone;
    }

    /**
     * 解析自选座位的过滤条件
     * 
     * @param jsonString [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public FiltersInfo parseFiltersJson(String jsonString) {
        FiltersInfo filtersInfo = new FiltersInfo();

        JSONObject resultJson = null;
        try {
            resultJson = new JSONObject(jsonString);
        } catch (JSONException e1) {
            LogUtil.e(TAG, e1.toString());
        }
        if (resultJson == null) {
            return filtersInfo;
        }

        // 解析场馆building信息
        try {
            List<Buildings> buildings = filtersInfo.getBuildings();
            JSONArray roomsArr = resultJson.getJSONArray("buildings");
            for (int i = 0; i < roomsArr.length(); i++) {
                JSONArray temp = roomsArr.getJSONArray(i);
                Buildings buliding = new Buildings();
                buliding.setId(temp.getInt(0));
                buliding.setName(temp.getString(1));
                buildings.add(buliding);
            }
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }

        // 解析房间rooms信息
        try {
            List<Rooms> rooms = filtersInfo.getRooms();
            JSONArray roomsArr = resultJson.getJSONArray("rooms");
            for (int i = 0; i < roomsArr.length(); i++) {
                JSONArray temp = roomsArr.getJSONArray(i);
                Rooms room = new Rooms();
                room.setId(temp.getInt(0));
                room.setName(temp.getString(1));
                room.setBuildId(temp.getInt(2));
                rooms.add(room);
            }
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }

        // 解析时长hours信息
        try {
            int hours = resultJson.getInt("hours");
            filtersInfo.setHours(hours);
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }

        // 解析时间dates信息
        try {
            JSONArray datesArr = resultJson.getJSONArray("dates");
            List<String> dates = filtersInfo.getDates();
            for (int i = 0; i < datesArr.length(); i++) {
                dates.add(datesArr.getString(i));
            }
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }

        return filtersInfo;

    }

    /**
     * 解析 自选预约：查询座位
     * 
     * @param jsonString
     * @return [参数说明]
     * @return SeatInfo [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public SeatInfo parseFreeSeats(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        SeatInfo resopn = gson2.fromJson(jsonString, new TypeToken<SeatInfo>() {
        }.getType());

        return resopn;

    }

    /**
     * v2版本，解析可能的终止时间列表
     * 
     * @param jsonString
     * @return BaseResponse<EndTimeResp> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public BaseResponse<EndTimeResp> parseEndTime(String jsonString) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        BaseResponse<EndTimeResp> respone = gson2.fromJson(jsonString,
                new TypeToken<BaseResponse<EndTimeResp>>() {
                }.getType());

        return respone;
    }

    /**
     * v2版本，解析有空座位的教室列表
     * 
     * @param jsonString
     * @return RoomsResp [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public RoomsResp parseRooms(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        RoomsResp respone = gson2.fromJson(jsonString, RoomsResp.class);
        return respone;
    }

    /**
     * v2版本，解析教室布局信息
     * 
     * @param jsonString
     * @return BaseResponse<LayoutsResp> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public BaseResponse<LayoutsResp> parseLayouts(String jsonString) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        BaseResponse<LayoutsResp> respone = gson2.fromJson(jsonString,
                new TypeToken<BaseResponse<LayoutsResp>>() {
                }.getType());

        if (Constants.SUCCESS.equals(respone.getStatus())) {
            try {

                JSONObject resultJson = new JSONObject(jsonString).getJSONObject("data");
                JSONObject layoutJo = resultJson.getJSONObject("layout");

                JSONArray names = layoutJo.names();

                for (int i = 0; i < names.length(); i++) {
                    String name = names.getString(i);
                    LayoutInfo layoutInfo = gson2.fromJson(layoutJo.getJSONObject(name).toString(),
                            LayoutInfo.class);
                    if ("seat".equals(layoutInfo.getType())) {
                        respone.getData().getLayout().getLayoutInfo().add(layoutInfo);
                    }
                }
            } catch (JSONException e) {
                LogUtil.e(TAG, e.toString());
            }
        }

        return respone;

    }

    /**
     * 查看预约信息
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public PreordainInfo parseViewDetail(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        PreordainInfo respone = gson2.fromJson(jsonString, new TypeToken<PreordainInfo>() {
        }.getType());

        return respone;
    }

    /** 解析 快速预约 */
    public BaseResponse<QuickBook> parseQuickBook(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();

        BaseResponse<QuickBook> respone = gson2.fromJson(jsonString,
                new TypeToken<BaseResponse<QuickBook>>() {
                }.getType());

        return respone;
    }

    /** 解析 自选座位 */
    public FreeBookResp parseFreeBook(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        FreeBookResp respone = gson2.fromJson(jsonString, FreeBookResp.class);
        return respone;
    }

    /**
     * 解析 预约历史记录
     * 
     * @param jsonString
     * @return
     */
    public BaseResponse<HistoryResp> parseHistoryInfo(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();

        BaseResponse<HistoryResp> respone = gson2.fromJson(jsonString,
                new TypeToken<BaseResponse<HistoryResp>>() {
                }.getType());

        return respone;

    }

    /**
     * 解析 最长预约时长 <功能详细描述>
     * 
     * @param jsonString
     * @return [参数说明]
     * @return AllowedInfo [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public BaseResponse<AllowedResp> parseAllowedHours(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        BaseResponse<AllowedResp> respone = gson2.fromJson(jsonString,
                new TypeToken<BaseResponse<AllowedResp>>() {
                }.getType());

        return respone;
    }

    /**
     * 解析取消预约
     * 
     * @param jsonString
     * @return [参数说明]
     * @return CancelInfo [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public BaseResponse<String> parseCancel(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();

        BaseResponse<String> respone = gson2.fromJson(jsonString,
                new TypeToken<BaseResponse<String>>() {
                }.getType());

        return respone;
    }

    public UpgradeInfo parseVersion(String jsonString) {

        JSONObject result;
        UpgradeInfo response = null;
        try {
            result = new JSONObject(jsonString).getJSONObject("android");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson2 = gsonBuilder.create();
            response = gson2.fromJson(result.toString(), UpgradeInfo.class);
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }
        return response;

    }

    public String parseAnnounce(String jsonString) {
        String announce = "";
        try {
            announce = new JSONObject(jsonString).getString("announce");
        } catch (JSONException e) {
            LogUtil.e(TAG, e.toString());
        }

        return announce;
    }

    /**
     * 异常信息
     * 
     * @param jsonString
     * @return
     */
    @Deprecated
    public FailInfo parseFailInfo(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        FailInfo respone = gson2.fromJson(jsonString, new TypeToken<FailInfo>() {
        }.getType());

        return respone;
    }

    /**
     * 座位的开始时间列表
     * 
     * @param jsonString
     * @return
     */
    public StartTimesForSeatResp parseStartTiemsResp(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        StartTimesForSeatResp respone = gson2.fromJson(jsonString, StartTimesForSeatResp.class);

        return respone;
    }

    /**
     * 座位的结束时间列表
     * 
     * @param jsonString
     * @return
     */
    public EndTimesForSeatResp parseEndTiemsResp(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        EndTimesForSeatResp respone = gson2.fromJson(jsonString, EndTimesForSeatResp.class);

        return respone;
    }

    /**
     * 续座的时间延长列表
     * 
     * @param jsonString
     * @return [参数说明]
     * @return ExtendTimeResp [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public ExtendTimeResp parseExtendTime(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        ExtendTimeResp respone = gson2.fromJson(jsonString, ExtendTimeResp.class);

        return respone;

    }

    /**
     * 解析当天最近一次的有效预约信息
     * 
     * @param jsonString
     * @return [参数说明]
     * @return ReservationsResp [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public ReservationsResp parseReservations(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        ReservationsResp respone = gson2.fromJson(jsonString, ReservationsResp.class);

        return respone;
    }

    public ExtendResp parseExtend(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        ExtendResp respone = gson2.fromJson(jsonString, ExtendResp.class);
        return respone;
    }

    public BaseResponseHeader parseStop(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        BaseResponseHeader respone = gson2.fromJson(jsonString, BaseResponseHeader.class);
        return respone;
    }

    public BaseResponseHeader parseCheckIn(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        BaseResponseHeader respone = gson2.fromJson(jsonString, BaseResponseHeader.class);
        return respone;
    }

    public BaseResponseHeader parseLeave(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        BaseResponseHeader respone = gson2.fromJson(jsonString, BaseResponseHeader.class);
        return respone;
    }

    @Deprecated
    public FreeTimes parseTest(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson2 = gsonBuilder.create();
        FreeTimes respone = gson2.fromJson(jsonString, new TypeToken<FreeTimes>() {
        }.getType());

        return respone;
    }

    public String getJsonString(String name, String def) {
        if (!Config.is_offline) {
            return def;
        }

        return getAssets(name);
    }

    public String getAssets(String name) {
        StringBuffer strBuffer = new StringBuffer("");
        try {
            InputStream is = LrmApplictaion.getContext().getResources().getAssets().open(name);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuffer.append(line);
            }
        } catch (IOException e) {
            LogUtil.e(TAG, e.toString());
        }

        return strBuffer.toString();

    }

}
