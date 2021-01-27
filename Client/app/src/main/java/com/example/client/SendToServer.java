package com.example.client;

import java.io.ObjectOutputStream;

public class SendToServer extends Thread{
    String message;
    SendToServer(String msg) {
        message = msg;
        start();
    }
    public void run() {
        try {
            if (MakeConnection.objectOutputStream == null) {
                MakeConnection.objectOutputStream = new ObjectOutputStream(MakeConnection.client_socket.getOutputStream());
            }
            MakeConnection.objectOutputStream.writeObject(message);
            MakeConnection.objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
