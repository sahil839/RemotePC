package com.example.client;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Controls extends AppCompatActivity {
    private Button shutdown_btn;
    private String sudoPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controls);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        shutdown_btn = findViewById(R.id.shut_down_btn);
        shutdown_btn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Shut Down");
            final EditText sudoPasswordInput = new EditText(this);
            sudoPasswordInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(sudoPasswordInput);

            builder.setPositiveButton("OK", (dialog, which) -> {
                sudoPassword = sudoPasswordInput.getText().toString();
                shutDownPC();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

    }
    private void shutDownPC() {
        RemoteScreen.sendToServer.message_queue.add("SHUTDOWN_PC");
        RemoteScreen.sendToServer.message_queue.add(sudoPassword);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
