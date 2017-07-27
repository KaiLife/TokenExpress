package com.ane.expresstokenapp.utils;

import android.widget.Toast;

import com.ane.expresstokenapp.App;

public class ToastUtil {

    public static void showShort(CharSequence text) {
        Toast.makeText(App.getApp().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showlong(CharSequence text) {
        Toast.makeText(App.getApp().getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
