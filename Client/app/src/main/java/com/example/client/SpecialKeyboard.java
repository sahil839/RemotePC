package com.example.client;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SpecialKeyboard extends AppCompatActivity {
    private Button leftArrowBtn, rightArrowBtn, upArrowBtn, downArrowBtn, f5Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_keyboard);
        leftArrowBtn = findViewById(R.id.left_arrow_btn);
        rightArrowBtn = findViewById(R.id.right_arrow_btn);
        upArrowBtn = findViewById(R.id.up_arrow_btn);
        downArrowBtn = findViewById(R.id.down_arrow_btn);
        f5Btn = findViewById(R.id.f5_btn);
        leftArrowBtn.setOnClickListener(v -> {
            RemoteScreen.sendToServer.message_queue.add("KEY_PRESS");
            RemoteScreen.sendToServer.message_queue.add("LEFT");
        });
        rightArrowBtn.setOnClickListener(v -> {
            RemoteScreen.sendToServer.message_queue.add("KEY_PRESS");
            RemoteScreen.sendToServer.message_queue.add("RIGHT");
        });
        upArrowBtn.setOnClickListener(v -> {
            RemoteScreen.sendToServer.message_queue.add("KEY_PRESS");
            RemoteScreen.sendToServer.message_queue.add("UP");
        });
        downArrowBtn.setOnClickListener(v -> {
            RemoteScreen.sendToServer.message_queue.add("KEY_PRESS");
            RemoteScreen.sendToServer.message_queue.add("DOWN");
        });
        f5Btn.setOnClickListener(v -> {
            RemoteScreen.sendToServer.message_queue.add("KEY_PRESS");
            RemoteScreen.sendToServer.message_queue.add("F5");
        });
    }
}
