
package com.otn.lrms.util.entity;

@Deprecated
public class UserInfo {
    private String id;// user id

    private boolean enabled;// 用户是否被禁用

    private String name;// 真实姓名

    private String username;// 登录名

    private String status;// 用户状态 (normal = 正常, locked = 上黑名单)

    private String lastLogin;// 上次登录时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

}
