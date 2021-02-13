package com.example.client;

import android.view.KeyEvent;
import android.database.Cursor;
import android.provider.OpenableColumns;

import java.util.HashMap;

public class Util {
    HashMap<Integer, String> keyboard_hash = new HashMap<Integer, String>();
    Util() {
        keyboard_hash.put(KeyEvent.KEYCODE_GRAVE, "`");
        keyboard_hash.put(KeyEvent.KEYCODE_MINUS, "-");
        keyboard_hash.put(KeyEvent.KEYCODE_EQUALS, "=");
        keyboard_hash.put(KeyEvent.KEYCODE_AT, "@");
        keyboard_hash.put(KeyEvent.KEYCODE_POUND, "#");
        keyboard_hash.put(KeyEvent.KEYCODE_STAR, "*");
        keyboard_hash.put(KeyEvent.KEYCODE_NUMPAD_LEFT_PAREN, "(");
        keyboard_hash.put(KeyEvent.KEYCODE_NUMPAD_RIGHT_PAREN, ")");
        keyboard_hash.put(KeyEvent.KEYCODE_PLUS, "+");
        keyboard_hash.put(KeyEvent.KEYCODE_TAB, "\t");
        keyboard_hash.put(KeyEvent.KEYCODE_ENTER, "\n");
        keyboard_hash.put(KeyEvent.KEYCODE_LEFT_BRACKET, "[");
        keyboard_hash.put(KeyEvent.KEYCODE_RIGHT_BRACKET, "]");
        keyboard_hash.put(KeyEvent.KEYCODE_BACKSLASH, "\\");
        keyboard_hash.put(KeyEvent.KEYCODE_SEMICOLON, ";");
        keyboard_hash.put(KeyEvent.KEYCODE_APOSTROPHE, "\"");
        keyboard_hash.put(KeyEvent.KEYCODE_COMMA, ",");
        keyboard_hash.put(KeyEvent.KEYCODE_PERIOD, ".");
        keyboard_hash.put(KeyEvent.KEYCODE_SLASH, "/");
        keyboard_hash.put(KeyEvent.KEYCODE_SPACE, " ");
        keyboard_hash.put(KeyEvent.KEYCODE_DEL, "\b");
    }
    public String get_string_for_keycode(int keycode) {
        return keyboard_hash.get(keycode);
    }
    public boolean check_key_implemented(int keycode) {
        return keyboard_hash.containsKey(keycode);
    }
    public static String get_file_name_from_uri(Cursor fileCursor) {
        int nameIndex = fileCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        fileCursor.moveToFirst();
        String fileName = fileCursor.getString(nameIndex);
        return fileName;
    }
    public static Long get_file_size_from_uri(Cursor fileCursor) {
        int sizeIndex = fileCursor.getColumnIndex(OpenableColumns.SIZE);
        fileCursor.moveToFirst();
        Long fileSize = fileCursor.getLong(sizeIndex);
        return fileSize;
    }

}
