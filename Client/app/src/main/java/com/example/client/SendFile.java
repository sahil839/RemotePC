package com.example.client;

import android.content.Context;
import android.net.Uri;

import java.io.InputStream;
import java.io.ObjectOutputStream;

public class SendFile extends Thread{
    ObjectOutputStream opStream;
    Uri file_uri;
    Context context;
    String fileName;
    Long fileSize;
    SendFile(Uri uri, Context c, String name, Long size) {
        file_uri = uri;
        context = c;
        fileName = name;
        fileSize = size;
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
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
