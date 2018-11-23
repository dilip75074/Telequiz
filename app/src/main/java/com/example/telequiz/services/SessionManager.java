package com.example.telequiz.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.activities.home.MainActivity;
import com.example.telequiz.activities.account.LoginActivity;
import com.example.telequiz.services.utilities.Message;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGGED_IN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGGED_IN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
        Message.message(context, "Successfully Logged in");
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
        Message.message(context, "Successfully Logged Out");
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    /**
     *Set navigation drawer based on logged in user and guest user.
     * */

    public void setEssentialComponentsBasedOnSession(Context context, NavigationView navigationView) {
        HashMap<String, String> userDetail;
        View header = navigationView.getHeaderView(0);
        TextView userNameText = (TextView) header.findViewById(R.id.nav_header_logged_in_user_name);
        TextView userEmailText = (TextView) header.findViewById(R.id.nav_header_logged_in_user_email);
        RelativeLayout logInButtonLayout = header.findViewById(R.id.nav_header_login_button_layout);

        if(this.isLoggedIn()) {
            userDetail = this.getUserDetails();
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_drawer_menu_logged_in);
            logInButtonLayout.setVisibility(View.GONE);
            userNameText.setText(userDetail.get(this.KEY_NAME));
            userEmailText.setText(userDetail.get(this.KEY_EMAIL));
        } else {
            logInButtonLayout.setVisibility(View.VISIBLE);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_drawer_menu_logged_out);
        }
    }
}