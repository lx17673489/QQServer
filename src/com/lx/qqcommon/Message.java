package com.lx.qqcommon;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/19 21:36
 */

import java.io.Serializable;

public class Message implements Serializable {
    private String content;
    private String sender;
    private String getter;
    private String sendTime;
    private String mestype;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMestype() {
        return mestype;
    }

    public void setMestype(String mestype) {
        this.mestype = mestype;
    }
}
