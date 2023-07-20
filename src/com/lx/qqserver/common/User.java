package com.lx.qqserver.common;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/19 21:33
 */

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String pwd;

    public User(String userId, String pwd) {
        this.userId = userId;
        this.pwd = pwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
