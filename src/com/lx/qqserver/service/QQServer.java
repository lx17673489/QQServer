package com.lx.qqserver.service;/*
 *@title
 *@description
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 16:28
 */

import com.lx.qqcommon.Message;
import com.lx.qqcommon.MessageType;
import com.lx.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class QQServer {
    public static void main(String[] args) {
        new QQServer();
    }
    private ServerSocket serverSocket;
    private static HashMap<String,String> verifiedUser;
    static {
        verifiedUser = new HashMap<>();
        verifiedUser.put("111","123456");
        verifiedUser.put("222","123456");
        verifiedUser.put("333","123456");
    }
    public QQServer(){
        try {
            System.out.println("服务端正在9999端口监听");
            serverSocket = new ServerSocket(9999);
            while(true){
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = null;
                User user = (User)ois.readObject();
                if(verifiedUser.get(user.getUserId()) != null && verifiedUser.get(user.getUserId()).equals(user.getPwd())){
                    ServerConnetClientThread serverConnetClientThread = new ServerConnetClientThread(socket,user.getUserId());
                    serverConnetClientThread.start();
                    ManageServerConnetClientThread.put(user.getUserId(),serverConnetClientThread);
                    //登录成功
                    System.out.println("用户"+user.getUserId()+"登录成功");
                    Message msg = new Message(); //创建登录成功的消息
                    msg.setMestype(MessageType.MESSAGE_LOGIN_SUCCESS);
                    //将登录成功的消息发送给服务端
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(msg);
                }else{
                    //登陆失败
                    System.out.println("用户"+user.getUserId()+"登陆失败");
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
