package com.lx.qqserver.service;/*
 *@title
 *@description 服务器连接客户端通信线程
 *@author 梁湘
 *@version 1.0
 *@create 2023/7/20 18:46
 */

import com.lx.qqcommon.Message;
import com.lx.qqcommon.MessageType;
import com.lx.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerConnetClientThread extends Thread{
    private Socket socket;
    private String userId;
    private List<ServerConnetClientThread> onlineUsers = new ArrayList<>();
    public ServerConnetClientThread(Socket socket,String userId,List<ServerConnetClientThread> onlineUsers){
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {
        System.out.println("服务器正在和"+userId+"保持通信");
        while(true){
            //监听通信
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //处理
                switch (message.getMestype()){
                    case ((MessageType.MESSAGE_GET_ONLINE_FRIEND)):
                        StringBuffer sb = new StringBuffer();
                        for(ServerConnetClientThread serverConnetClientThread : onlineUsers){
                            sb.append(serverConnetClientThread.userId+"\n");
                        }
                        Message message_out = new Message();
                        message_out.setMestype(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        message_out.setContent(sb.toString());
                        oos.writeObject(message_out);
                        break;
                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
