
package com.otn.lrms.util;

public class Constants {

    public static final String QUICK_PAGE = "quick";

    public static final String OTHER_PAGE = "other";

    public static final String MY_PAGE = "my";

    public static final String HTTP_NET_ERROR = "9999";

    public static final String HTTP_HOST_NULL = "8888";

    public static final String DEFAULT_BATCH = "16";

    public static final String DEFAULT_PER = "8";

    public static final String NUM_PER_PAGE = "10";

    public static final String CODE_100 = "-100";

    public static final String CODE_101 = "-101";

    public static final String CODE_102 = "-102";

    public static final String CODE_TOKEN = "12";

    public static final String MSG_TOKEN = "invalide token";

    public static final String SUCCESS = "success";

    public static final String FAIL = "fail";

    /**
     * 空闲
     */
    public static final String seat_free = "FREE";

    /**
     * 已预约
     */
    public static final String seat_BOOKED = "BOOKED";

    /**
     * 离开
     */
    public static final String seat_AWAY = "AWAY";

    /**
     * 使用中
     */
    public static final String seat_IN_USE = "IN_USE";

    /**
     * 不可用
     */
    public static final String seat_FULL = "FULL";

    /**
     * 登陆鉴权接口
     */
    public static final String METHOD_AUTH = "auth";

    /**
     * 快速预约允许最大的时长
     */
    public static final String METHOD_ALLOWEDHOURS = "allowedHours";

    /**
     * 快速预约
     */
    public static final String METHOD_QUCIKBOOK = "quickBook";

    /**
     * 公告
     */
    public static final String METHOD_ANNOUNCE = "announce";

    /**
     * 预约记录
     */
    public static final String METHOD_HISTORY = "history";

    /**
     * 查看预约信息
     */
    public static final String METHOD_VIEW = "view";

    /**
     * 获取可能的终止时间列表
     */
    @Deprecated
    public static final String METHOD_ENDTIMESTODAY = "endTimesToday";

    /**
     * 获得有空座位的教室列表
     */
    @Deprecated
    public static final String METHOD_ROOMSBYTIME = "roomsByTime";

    /**
     * 获取教室布局信息
     * room/layout
     */
    public static final String METHOD_ROOMLAYOUT = "room/layoutByDate";

    public static final String METHOD_ROOMSTATS2 = "room/stats2";

    public static final String METHOD_STARTTIMESFORSEAT = "startTimesForSeat";

    public static final String METHOD_ENDTIMESFORSEAT = "endTimesForSeat";

    /**
     * 自选预约：获取filters条件
     */
    public static final String METHOD_FILTERS = "free/filters";

    /**
     * 获取座位的可预约开始时间
     */
    @Deprecated
    public static final String METHOD_STARTTIME = "free/startTime";

    /**
     * 获取座位的可预约开始时间
     */
    @Deprecated
    public static final String METHOD_ENDTIME = "free/endTime";

    /***
     * 自选预约：查询座位
     */
    @Deprecated
    public static final String METHOD_FREE_SEATS = "free/seats";

    /** 自选预约 */
    public static final String METHOD_FREEBOOK = "freeBook";

    /** 取消预约 */
    public static final String METHOD_CANCEL = "cancel";

    /** 续座时间延长列表 */
    public static final String METHOD_TIMEEXTEND = "timeExtend";

    /** 最近一次的有效预约信息 */
    public static final String METHOD_RESERVATIONS = "user/reservations";

    /** 签到 */
    public static final String METHOD_CHECKIN = "checkIn";

    /** 暂离 */
    public static final String METHOD_LEAVE = "leave";

    /** 结束 */
    public static final String METHOD_STOP = "stop";

    /** 续座 */
    public static final String METHOD_EXTEND = "extend";

    /** web方式访问服务器 */
    public static final int WEB_ACCESSING_SERVER = 0;

    /** IP端口方式访问服务器 */
    public static final int IP_PORT_ACCESSING_SERVER = 1;

    public static final String SERVER_TYPE = "serverType";

    public static final String URL_WEB = "urlWeb";

    public static final String URL_IP = "urlIp";

    public static final String URL_PORT = "urlPort";
}
