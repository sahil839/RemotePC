package com.example.client;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.Socket;

public class RemoteScreen extends AppCompatActivity {
    public static ImageView screenView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_remote);
        screenView = (ImageView) findViewById(R.id.screenImageView);
        Socket socket = MakeConnection.client_socket;
        new UpdateScreen(RemoteScreen.this);
    }
}
