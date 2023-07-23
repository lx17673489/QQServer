package com.lx.qqserver.service;/*
 *@title
 *@description 管理类
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 20:18
 */

import java.util.HashMap;
import java.util.Set;

public class ManageServerConnetClientThread {
    private static HashMap<String,ServerConnetClientThread> map = new HashMap<>(); //<key:userId>

    public static void put(String userId,ServerConnetClientThread serverConnetClientThread){
        map.put(userId,serverConnetClientThread);
    }
    public static ServerConnetClientThread get(String userId){
        return map.get(userId);
    }
    public static void remove(String userId){
        map.remove(userId);
    }

    public static Set<String> getUser(){
        return map.keySet();
    }
}
