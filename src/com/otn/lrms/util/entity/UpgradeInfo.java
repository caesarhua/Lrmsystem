
package com.otn.lrms.util.entity;

import java.io.Serializable;

/**
 * <升级信息的实体类> <功能详细描述>
 * 
 * @author eKF73344
 * @version [版本号, 2012-6-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UpgradeInfo implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    private String verNo;

    private String updates;

    private String downURL;

    

    public String getVerNo()
    {
        return verNo;
    }

    public void setVerNo(String verNo)
    {
        this.verNo = verNo;
    }

    public String getUpdates() {
        return updates;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public String getDownURL() {
        return downURL;
    }

    public void setDownURL(String downURL) {
        this.downURL = downURL;
    }

    @Override
    public String toString() {
        return "UpgradeInfo [VerNo=" + verNo + ", Updates=" + updates + ", DownURL=" + downURL
                + "]";
    }

}
