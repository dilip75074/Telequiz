package com.example.telequiz.activities.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.telequiz.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView _loginLink;
    EditText _emailText;
    Button _passwordRecoverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Forgot Password");
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
        _passwordRecoverButton = findViewById(R.id.btn_password_recover);
        _loginLink = findViewById(R.id.link_login);
    }
}
