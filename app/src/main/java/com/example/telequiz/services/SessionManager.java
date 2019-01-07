package com.example.telequiz.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.activities.account.LoginActivity;
import com.example.telequiz.activities.home.MainActivity;
import com.example.telequiz.services.utilities.Constant;
import com.example.telequiz.services.utilities.Message;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref, emailPref;

    // Editor for Shared preferences
    Editor editor, emailEditor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    /*
    * User session
    * */
    private static final String PREF_NAME = Constant.PREF_NAME;
    private static final String IS_LOGGED_IN = Constant.IS_LOGGED_IN;
    private static final String KEY_NAME = Constant.KEY_NAME;
    private static final String KEY_EMAIL = Constant.KEY_EMAIL;

    /*
    * Session for the use of Remember me functionality while logging in
    * */
    private static final String EMAIL_PREF_NAME = Constant.EMAIL_PREF_NAME;
    private static final String IS_REMEMBER_ME_CHECKED = Constant.IS_REMEMBER_ME_CHECKED;
    private static int SESSION_FLAG; //session flag variable is mainly used to implement the remember check box functionality.


    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        emailPref = context.getSharedPreferences(EMAIL_PREF_NAME, PRIVATE_MODE);

        editor = pref.edit();
        emailEditor = emailPref.edit();

        if(isRemeberMeChecked()) {
            SESSION_FLAG = 1;
        }
        else if(isLoggedIn() && SESSION_FLAG == 0 && !isRemeberMeChecked()){
            clearSessionData();
        }
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email, boolean isRememberMeChecked){
        SESSION_FLAG = 1;
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGGED_IN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        emailEditor.putString(KEY_EMAIL, email);

        editor.putBoolean(IS_REMEMBER_ME_CHECKED, isRememberMeChecked);

        // commit changes
        editor.commit();
        emailEditor.commit();
        Message.message(context, "Successfully logged in");
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

    /*
    * Get user email from login history
    * */
    public String getUserEmailFromLoginHistory() {
        return emailPref.getString(KEY_EMAIL, null);
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
     * User Logout
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
        Message.message(context, "Successfully logged Out");
    }

    /**
     * User Logout
     * */
    public void clearSessionData(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login status
     * **/
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    /**
     * Quick check for remember me check box status of last login
     * **/
    public boolean isRemeberMeChecked(){
        return pref.getBoolean(IS_REMEMBER_ME_CHECKED, false);
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