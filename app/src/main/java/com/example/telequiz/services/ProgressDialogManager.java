package com.example.telequiz.services;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogManager {
    Context context;
    ProgressDialog progressDialog;

    public ProgressDialogManager(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    public void showProgressDialod(String title, String message) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgressDialod() {
        progressDialog.dismiss();
    }
}
