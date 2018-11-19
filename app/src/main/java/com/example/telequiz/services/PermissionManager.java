package com.example.telequiz.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class PermissionManager {
    boolean networkPermission = false;
    boolean internetPermission = false;
    boolean contactPermission = false;

    public PermissionManager() {
        this.networkPermission = isNetworkPermissionGranted();
        this.internetPermission = isInternetPermissionGranted();
        this.contactPermission = isContactPermissionGranted();
    }

    public boolean isNetworkPermissionGranted() {
        return true;
    }

    public boolean isInternetPermissionGranted() {
        return true;
    }

    public boolean isContactPermissionGranted() {
        return true;
    }
}
