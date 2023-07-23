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
import java.util.Set;

public class ServerConnetClientThread extends Thread{
    private Socket socket;
    private String userId;
    private boolean isExist = true;
    public ServerConnetClientThread(Socket socket,String userId){
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {
        System.out.println("服务器正在和"+userId+"保持通信");
        while(isExist){
            //监听通信
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //处理
                switch (message.getMestype()){
                    case ((MessageType.MESSAGE_GET_ONLINE_FRIEND)):
                        StringBuffer sb = new StringBuffer();
                        Set<String> onlineUsers = ManageServerConnetClientThread.getUser();
                        for(String user : onlineUsers){
                            sb.append(user+"\n");
                        }
                        Message message_out = new Message();
                        message_out.setMestype(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        message_out.setContent(sb.toString());
                        oos.writeObject(message_out);
                        break;
                    case (MessageType.MESSAGE_CLIENT_EXIT):
                        ManageServerConnetClientThread.remove(message.getSender());
                        System.out.println("用户"+message.getSender()+"退出聊天");
                        isExist = false;
                        break;
                    case (MessageType.MESSAGE_COMM_MES): //普通消息包
                        if(message.getGetter().equals(message.getSender())){//发送者和接收者一样就
                            break;
                        }
                        System.out.println(message.getSender()+"给"+message.getGetter()+"发送了一条消息");
                        //转发消息
                        oos = new ObjectOutputStream(
                                ManageServerConnetClientThread.get(message.getGetter()).getSocket().getOutputStream());
                        oos.writeObject(message);
                        break;
                    case (MessageType.MESSAGE_TO_ALL_MES)://群发消息
                        System.out.println("用户 "+message.getSender()+"正在群发消息");
                        //遍历在线用户列表
                        Set<String> onlinUsers = ManageServerConnetClientThread.getUser();
                        for(String user: onlinUsers){
                            if(user.equals(message.getSender())){
                                continue;
                            }
                            oos = new ObjectOutputStream(
                                    ManageServerConnetClientThread.get(user).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                        break;
                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
