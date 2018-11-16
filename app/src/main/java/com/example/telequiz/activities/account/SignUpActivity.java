package com.example.telequiz.activities.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.telequiz.R;

public class SignUpActivity extends AppCompatActivity {

    TextView _loginLink, _forgotPasswordLink;
    EditText _emailText, _passwordText, _confirmPasswordText;
    Button _signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Sign Up");

        initAllViews();

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initAllViews() {
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _confirmPasswordText = findViewById(R.id.input_confirm_password);
        _signupButton = findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);
        _forgotPasswordLink = findViewById(R.id.link_forgot_password);
    }
}
