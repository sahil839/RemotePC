package com.example.client;

import java.util.concurrent.ArrayBlockingQueue;

public class SendToServer extends Thread{
    String message;
    public ArrayBlockingQueue <String> message_queue;
    SendToServer() {
        message_queue = new ArrayBlockingQueue<>(100);
        start();
    }
    public void run() {
        while(true) {
            if (MakeConnection.objectOutputStream != null) {
                if (message_queue.size() != 0) {
                    try {
                        message = message_queue.remove();
                        MakeConnection.objectOutputStream.writeObject(message);
                        MakeConnection.objectOutputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
