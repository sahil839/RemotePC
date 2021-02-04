package com.example.client;

import android.content.Context;
import android.content.Intent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MakeConnection implements Runnable{
    String ipAddress;
    String password;
    InetSocketAddress client_socket_addr, screen_socket_addr;
    public static Socket client_socket, screen_socket;
    DataInputStream passwordVerification;
    DataOutputStream sendPassword;
    public static ObjectOutputStream objectOutputStream, screenOutputStream;
    public static ObjectInputStream screenInputStream;
    Context connectContext;
    MakeConnection( String ip, String pass, Context context) {
        ipAddress = ip;
        password = pass;
        connectContext = context;
    }
    public void run() {
        int port = 8000, screen_port = 8001;
        try {
            client_socket_addr = new InetSocketAddress(ipAddress, port);
            client_socket = new Socket();
            client_socket.connect(client_socket_addr, 3000);

            screen_socket_addr = new InetSocketAddress(ipAddress, screen_port);
            screen_socket = new Socket();
            screen_socket.connect(screen_socket_addr, 3000);

            sendPassword = new DataOutputStream(client_socket.getOutputStream());
            passwordVerification = new DataInputStream(client_socket.getInputStream());
            sendPassword.writeUTF(password);
            String verificationMessage = passwordVerification.readUTF();
            if (verificationMessage.equals("Password verified.")) {
                MakeConnection.screenOutputStream = new ObjectOutputStream(MakeConnection.screen_socket.getOutputStream());
                MakeConnection.screenInputStream = new ObjectInputStream(MakeConnection.screen_socket.getInputStream());

                MakeConnection.objectOutputStream = new ObjectOutputStream(MakeConnection.client_socket.getOutputStream());

                Intent intent = new Intent(connectContext, RemoteScreen.class);
                connectContext.startActivity(intent);
            } else {
                client_socket.close();
                screen_socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
