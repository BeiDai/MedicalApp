package com.example.beidai.myapplication;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketUtil {

    private String str;
    private Socket socket;
    private String ip;

    public SocketUtil(String str, String ip){
        this.str = str;
        this.ip = ip;
    }

    public String sendMessage(){
        String result = "123134";
        try{
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, 8086),5000);
            OutputStream out = socket.getOutputStream();
            out.write(str.getBytes());
            out.flush();
            out.close();
            socket.close();
            Log.i("AppDebug","close");
        }catch (SocketTimeoutException e){
            Log.i("socket",e.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

}
