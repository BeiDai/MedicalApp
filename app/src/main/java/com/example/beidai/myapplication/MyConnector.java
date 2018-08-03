package com.example.beidai.myapplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MyConnector {

    Socket socket = null;
    DataInputStream din = null;
    DataOutputStream dout = null;

    public MyConnector(String address,int port){
        try{
            socket = new Socket(address,port);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sayBye(){
        try{
            dout.writeUTF("<#USER_LOGOUT#>");
            din.close();
            dout.close();
            socket.close();
            socket = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
