package com.example.telequiz.services.utilities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}