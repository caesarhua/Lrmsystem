
package com.otn.lrms.util.entity;

import java.io.Serializable;

/**
 * <预约成功的详情>
 * 
 * @author LuYozone
 * @version [版本号, 2013-5-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PreordainInfo implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String onDate;// 预约日期

    private String begin;// 预约开始时间

    private String end;// 预约结束时间

    private String location;// 预约地点

    private String id;

    private String receipt;

    public String getOnDate() {
        return onDate;
    }

    public void setOnDate(String onDate) {
        this.onDate = onDate;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
