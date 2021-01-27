package com.example.client;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

public class UpdateScreen implements Runnable {
    Activity screen_activity;
    UpdateScreen(Activity activity) {
        screen_activity = activity;
    }
    public void run () {
        FileOutputStream fos = null;
        String filename = "screen.png";
        try {
            if (MakeConnection.objectInputStream == null) {
                MakeConnection.objectInputStream = new ObjectInputStream(MakeConnection.client_socket.getInputStream());
            }
            fos = screen_activity.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
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
            String path = new File(screen_activity.getFilesDir(), filename).getAbsolutePath();
            bitmap = BitmapFactory.decodeFile(path);
            Matrix matrix = new Matrix();
            matrix.postRotate(-90);
            try {
                Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                RemoteScreen.screenView.setImageBitmap(rotated);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
