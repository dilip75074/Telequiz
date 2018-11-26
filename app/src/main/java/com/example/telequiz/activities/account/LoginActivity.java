package com.example.telequiz.activities.account;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.activities.home.MainActivity;
import com.example.telequiz.services.utilities.Constant;
import com.example.telequiz.services.SessionManager;
import com.example.telequiz.services.utilities.Message;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Context context;
    SessionManager session;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    TextView signupLink, forgotPasswordLink;
    EditText emailText, passwordText;
    Button loginButton;
    CheckBox rememberMeCheckBox;

    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_account_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initAllComponents();

        String userEmail = session.getUserEmailFromLoginHistory();
        if(userEmail != null) {
            emailText.setText(userEmail);
            passwordText.requestFocus();
            session.clearSessionData();
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

            if(http_status_code == Constant.HTTP_SUCCESS) {
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
    }

    public void onLoginSuccess(String userName) {
        String email = emailText.getText().toString();
        boolean isRememberMeChecked = rememberMeCheckBox.isChecked();

        session.createLoginSession(userName, email, isRememberMeChecked);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void onLoginFailed() {
        Message.message(context, "Login failed");
    }

    private void initAllComponents() {
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        signupLink = findViewById(R.id.link_signup);
        forgotPasswordLink = findViewById(R.id.link_forgot_password);
        rememberMeCheckBox = findViewById(R.id.check_box_remeber_me);
        context = getApplicationContext();
        session = new SessionManager(context);
    }
}
