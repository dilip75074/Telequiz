package com.example.telequiz.services;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.example.telequiz.activities.AppUpdateNotificationActivity;
import com.example.telequiz.services.utilities.Constant;

public class AppUpdateChecker {
    Context context;
    boolean isForceUpdate;
    public static int appUpdateCheckCount = 0; //How many times an update is checked in the current ongoing session

    public AppUpdateChecker(Context context) {
        this.context = context;
        this.isForceUpdate = false;
    }

    public void notifyUserIfAnUpdate() {
        // Each after 10 times an update pop up will be displayed to the user at the main activity
        if(appUpdateCheckCount > 0 )
            return;

        appUpdateCheckCount++;
        if(isForceUpdate) {
            Intent intent = new Intent(context, AppUpdateNotificationActivity.class);
            context.startActivity(intent);
        }
        else {
            showUpdateDialog();
        }
    }

    /**
     * Method to show update dialog
     */
    public void showUpdateDialog() {

        String message = Constant.UPDATE_MESSSAGE ;
        String title = Constant.UPDATE_MESSAGE_TITLE;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")));
                dialog.cancel();
            }
        });

        alertDialogBuilder.setNegativeButton("Remind Later", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
        });

        alertDialogBuilder.show();
    }
}
