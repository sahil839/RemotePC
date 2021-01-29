package com.example.client;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.TimerTask;
import java.util.Timer;

public class UpdateScreen {
    Activity screen_activity;
    long delay = 0;
    long period = 1000;
    UpdateScreen(Activity activity) {
        screen_activity = activity;
        RemoteScreen.updateScreenTimer = new Timer();
        TimerTask updateScreenTask = new TimerTask() {
            @Override
            public void run() {
                MakeConnection.sendToServer = new SendToServer("SEND_SCREEN");

                FileOutputStream fos = null;
                String filename = "screen.png";
                try {
                    MakeConnection.objectInputStream = new ObjectInputStream(MakeConnection.client_socket.getInputStream());
                    fos = screen_activity.openFileOutput(filename, Context.MODE_PRIVATE);
                    byte buffer[] = new byte[4096];
                    int fileSize = (int) MakeConnection.objectInputStream.readObject();
                    int read = 0;
                    int remaining = fileSize;
                    while ((read = MakeConnection.objectInputStream.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                        remaining -= read;
                        fos.write(buffer, 0, read);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                screen_activity.runOnUiThread(() -> {
                    Bitmap bitmap;
                    File imageFile = new File(screen_activity.getFilesDir(), filename);
                    String path = imageFile.getAbsolutePath();
                    bitmap = BitmapFactory.decodeFile(path);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(-90);
                    try {
                        Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        RemoteScreen.screenView.setImageBitmap(rotated);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imageFile.delete();
                });
            }
        };
        RemoteScreen.updateScreenTimer.scheduleAtFixedRate(updateScreenTask, delay, period);
    }
}
