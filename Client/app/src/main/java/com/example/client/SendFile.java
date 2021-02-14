package com.example.client;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class SendFile extends Thread{
    ObjectOutputStream opStream;
    Uri file_uri;
    Context context;
    String fileName;
    Long fileSize;
    Activity fileActivity;
    Button selectFile, sendFile;
    TextView filePathTxt;
    SendFile(Uri uri, Context c, String name, Long size, Activity activity, Button selectbtn, Button sendbtn, TextView pathtxt) {
        file_uri = uri;
        context = c;
        fileName = name;
        fileSize = size;
        fileActivity = activity;
        selectFile = selectbtn;
        sendFile = sendbtn;
        filePathTxt = pathtxt;
        start();
    }
    public void run() {
        opStream = MakeConnection.fileOutputStream;
        try {
            InputStream is = context.getContentResolver().openInputStream(file_uri);
            opStream.writeObject("RECEIVE_FILE");
            opStream.writeObject(fileName);
            opStream.writeObject(fileSize);
            byte[] buffer = new byte[4096];
            int read = 0;
            long totalRead = 0;
            int remaining = fileSize.intValue();
            while (totalRead < fileSize && (read = is.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                opStream.write(buffer, 0, read);
                opStream.flush();
            }
            opStream.flush();
            selectFile.postDelayed(()-> selectFile.setEnabled(true), 500);
            sendFile.postDelayed(() -> sendFile.setEnabled(true), 500);
            filePathTxt.setText("");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
