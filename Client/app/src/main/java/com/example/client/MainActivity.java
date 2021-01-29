package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button connectButton;
    private EditText ipAddressEditText, passwordText;
    private Context connectContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectButton = findViewById(R.id.connect_button);
        connectContext = this.getApplicationContext();
        connectButton.setOnClickListener((View.OnClickListener) v -> {
            ipAddressEditText = (EditText) findViewById(R.id.ipAddressText);
            passwordText = findViewById(R.id.passwordText);
            String ipAddress = ipAddressEditText.getText().toString();
            String password = passwordText.getText().toString();
            Runnable r = new MakeConnection(ipAddress, password, connectContext);
            new Thread(r).start();
        });

    }
}