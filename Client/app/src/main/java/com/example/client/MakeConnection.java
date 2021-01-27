package com.example.client;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MakeConnection implements Runnable{
    String ipAddress;
    String password;
    InetSocketAddress client_socket_addr;
    Socket client_socket;
    DataInputStream passwordVerification;
    DataOutputStream sendPassword;
    Context connectContext;
    MakeConnection( String ip, String pass, Context context) {
        ipAddress = ip;
        password = pass;
        connectContext = context;
    }
    public void run() {
        int port = 8000;
        try {
            client_socket_addr = new InetSocketAddress(ipAddress, port);
            client_socket = new Socket();
            client_socket.connect(client_socket_addr, 3000);
            sendPassword = new DataOutputStream(client_socket.getOutputStream());
            passwordVerification = new DataInputStream(client_socket.getInputStream());
            sendPassword.writeUTF(password);
            String verificationMessage = passwordVerification.readUTF();
            Log.d("myTag", verificationMessage);
            if (verificationMessage.equals("Password verified.")) {
                Intent intent = new Intent(connectContext, RemoteScreen.class);
                connectContext.startActivity(intent);
            } else {
                client_socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
