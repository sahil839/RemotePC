package com.example.client;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class RemoteScreen extends AppCompatActivity {
    public static ImageView screenView;
    public static Timer updateScreenTimer;
    SendToServer sendToServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_remote);
        screenView = (ImageView) findViewById(R.id.screenImageView);
        new UpdateScreen(RemoteScreen.this);
        sendToServer = new SendToServer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            updateScreenTimer.cancel();
            updateScreenTimer.purge();
            MakeConnection.objectOutputStream = null;
            sendToServer.message_queue.add("CLOSE_CONNECTION");
            MakeConnection.client_socket.close();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
