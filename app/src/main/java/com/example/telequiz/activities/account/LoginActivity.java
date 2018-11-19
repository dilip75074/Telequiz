package com.example.telequiz.activities.account;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telequiz.R;
import com.example.telequiz.activities.home.MainActivity;
import com.example.telequiz.services.ConfigManager;
import com.example.telequiz.services.SessionManager;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Context context;
    ConfigManager config;
    SessionManager session;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    TextView signupLink, forgotPasswordLink;
    EditText emailText, passwordText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_account_login);
        context = getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        config = new ConfigManager();

        initAllComponents();

        // Session class instance
        session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        if(session.isLoggedIn()) {
            emailText.setText(user.get(SessionManager.KEY_EMAIL));
            emailText.setSelection(emailText.length());
            passwordText.requestFocus();
        }
        else {
            Toast.makeText(context, "Not Logged In", Toast.LENGTH_SHORT).show();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    public void login() {

        if(isValidEmail() && isValidPassword()) {
            remoteAuthenticate();
        }
        else {
            onLoginFailed();
        }
    }

    public void remoteAuthenticate() {
            /***
             * Make a http post request tp Check if user exist in our database or not.
             */

            loginButton.setEnabled(false);
            final String email = emailText.getText().toString();
            final String password = passwordText.getText().toString();
            int http_status_code;

            http_status_code = 200;

            if(http_status_code == config.getHttpStatusCode("SUCCESS")) {
                onLoginSuccess("Dilip Kumar");
            }
            else  {
                onLoginFailed();
            }
    }

    public boolean isValidEmail() {
        String email = emailText.getText().toString();
        boolean isValid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }

    public boolean isValidPassword() {
        boolean isValid = false;
        String password = passwordText.getText().toString();
        if (password.isEmpty() || password.length() < 6 || password.length() > 20) {
            passwordText.setError("Enter a valid password");
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }(true);
        SessionManager session = new SessionManager(context);
        session.createLoginSession(userName, email);
//        Toast.makeText(ge
//
//    public void onLoginSuccess(String userName) {
//        String email = emailText.getText().toString();
//        loginButton.setEnabledtBaseContext(), "Login Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(context, "Successfully Logged in", Toast.LENGTH_SHORT).show();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
        loginButton.setEnabled(true);
    }

    private void initAllComponents() {
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        signupLink = findViewById(R.id.link_signup);
        forgotPasswordLink = findViewById(R.id.link_forgot_password);
    }
}
