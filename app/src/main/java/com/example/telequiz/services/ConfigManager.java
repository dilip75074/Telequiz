package com.example.telequiz.services;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private Map<String, String> constant_values = new HashMap<>();
    private Map<String, Integer> status_codes = new HashMap<>();
    private String userName;
    private String userEmail;
    public ConfigManager() {
        this.setConstantValues();
        this.setHttpStatusCode();
        this.setUserName("Telequiz");
        this.setUserEmail("info.teqequiz@gmail.com");
    }

    public String getConstantValue(String key) {
        return constant_values.get(key);
    }

    public void setConstantValues() {
//        App Related
       constant_values.put("APP_NAME", "Telequiz");

//       Server Related
       constant_values.put("SERVER_URL", "http://localhost:9090/telequiz/");

//       User Session related
        constant_values.put("USER_LOGIN_SESSION_SHARED_PREFERENCES_FILE_NAME", "_telequiz_pref");
        constant_values.put("USER_LOGIN_SESSION_NAME_KEY", "_user_name");
        constant_values.put("USER_LOGIN_SESSION_EMAIL_KEY", "_user_email");
        constant_values.put("USER_LOGIN_SESSION_IS_LOGGED_IN_KEY", "_is_user_logged_in");
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public int getHttpStatusCode(String key) {
        return status_codes.get(key);
    }
    public void setHttpStatusCode() {
        status_codes.put("SUCCESS", 200);
    }
}
