package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ObjectOutputStream;

public class FileTransfer extends AppCompatActivity {
    private Button selectFile, sendFile;
    private TextView filePathText;

    ObjectOutputStream opStream;
    Uri uri;
    String fileName;
    Long fileSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_share);
        selectFile = findViewById(R.id.select_file);
        filePathText = findViewById(R.id.file_path);
        sendFile = findViewById(R.id.send_file);
        opStream = MakeConnection.fileOutputStream;
        selectFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, 2);
        });
        sendFile.setOnClickListener(v -> {
            new SendFile(uri, getApplicationContext(), fileName, fileSize, FileTransfer.this, selectFile, sendFile, filePathText);
            sendFile.setEnabled(false);
            selectFile.setEnabled(false);
            filePathText.setText("Sending " + fileName + ".......");
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 2
                && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uri = resultData.getData();

                Cursor fileCursor = getContentResolver().query(uri, null, null, null, null);
                fileName = Util.get_file_name_from_uri(fileCursor);;
                fileSize = Util.get_file_size_from_uri(fileCursor);
                filePathText.setText(fileName);
            }
        }
    }
}
