package com.example.telequiz.activities.account;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.services.SessionManager;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView loginLink;
    EditText emailText;
    Button passwordRecoverButton;
    SessionManager session;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Forgot Password");
        initAllComponents();

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void initAllComponents() {
        emailText = findViewById(R.id.input_email);
        passwordRecoverButton = findViewById(R.id.btn_password_recover);
        loginLink = findViewById(R.id.link_login);
        context = getApplicationContext();
        session = new SessionManager(context);
    }
}
