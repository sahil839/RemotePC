package com.example.client;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class RemoteScreen extends AppCompatActivity {
    public static ImageView screenView;
    public static Timer updateScreenTimer;
    SendToServer sendToServer;
    private int screenViewX, screenViewY;
    private int xCord, yCord, initX, initY, distY;
    boolean mouseMoved = false, multiTouch = false;
    long currentPressTime, lastPressTime;
    Float finalXCord, finalYCord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_remote);
        screenView = (ImageView) findViewById(R.id.screenImageView);
        new UpdateScreen(RemoteScreen.this);
        sendToServer = new SendToServer();

        ViewTreeObserver vto = screenView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                screenViewX = screenView.getHeight();
                screenViewY = screenView.getWidth();
                ViewTreeObserver obs = screenView.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
            }
        });

        screenView.setOnTouchListener((view, event) -> {
            if (MakeConnection.client_socket != null) {
                switch(event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        xCord = screenViewX - (int) event.getY();
                        yCord = (int) event.getX();
                        initX = xCord;
                        initY = yCord;
                        finalXCord = (float) xCord / screenViewX;
                        finalYCord = (float) yCord / screenViewY;
                        sendToServer.message_queue.add("MOUSE_MOVE_LIVE");
                        sendToServer.message_queue.add(Float.toString(finalXCord));
                        sendToServer.message_queue.add(Float.toString(finalYCord));
                        mouseMoved = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (multiTouch == false) {
                            xCord = screenViewX - (int) event.getY();
                            yCord = (int) event.getX();
                            if ((xCord - initX) != 0 && (yCord - initY) != 0) {
                                initX = xCord;
                                initY = yCord;
                                finalXCord = (float) xCord / screenViewX;
                                finalYCord = (float) yCord / screenViewY;
                                sendToServer.message_queue.add("MOUSE_MOVE_LIVE");
                                sendToServer.message_queue.add(Float.toString(finalXCord));
                                sendToServer.message_queue.add(Float.toString(finalYCord));
                                mouseMoved=true;
                            }
                        } else {
                            distY = (int) (initY - event.getY());
                            distY = (int) distY/4;
                            if(distY != 0) {
                                sendToServer.message_queue.add("MOUSE_WHEEL");
                                sendToServer.message_queue.add(Integer.toString(distY));
                                mouseMoved=true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        // Currently supports only single click.
                        currentPressTime = System.currentTimeMillis();
                        long interval = currentPressTime - lastPressTime;
                        if (interval >= 500 && !mouseMoved) {
                            sendToServer.message_queue.add("LEFT_CLICK");
                        }
                        lastPressTime = currentPressTime;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        initY = (int) event.getY();
                        mouseMoved = false;
                        multiTouch = true;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if(!mouseMoved) {
                            sendToServer.message_queue.add("LEFT_CLICK");
                        }
                        multiTouch = false;
                        break;
                }
            }
            return true;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remote_screen, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            updateScreenTimer.cancel();
            updateScreenTimer.purge();
            sendToServer.message_queue.add("CLOSE_CONNECTION");
            MakeConnection.client_socket.close();
            MakeConnection.screen_socket.close();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_keyboard:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }
}
