package com.example.telequiz.activities.account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.activities.home.MainActivity;
import com.example.telequiz.services.ConfigManager;

public class ChangePasswordActivity extends AppCompatActivity {

    Context context;
    ConfigManager config;

    TextView backToHomeLink;
    EditText oldPasswordText, newPasswordText, confirmNewPasswordText;
    Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_change_password);
        context = getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Change Password");

        initAllComponents();

        backToHomeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePassword();
            }
        });
    }

    private void validatePassword() {
        if(isValidOldPassword() && isValidNewPassword()) {
            new AlertDialog.Builder(this)
                    .setTitle("Change Password")
                    .setMessage("Password has been changed successfully")
                    .setIcon(R.drawable.icon_success)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();
        }
    }

    private boolean isValidOldPassword() {
        String oldPassword = oldPasswordText.getText().toString();
        if (oldPassword.isEmpty()) {
            oldPasswordText.setError("Enter the old password");
            return false;
        }
        else if(oldPassword.length() < 6) {
            newPasswordText.setError("Password must be at least 6 characters");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isValidNewPassword() {
        String newPassword = newPasswordText.getText().toString();
        String confirmNewPassword = confirmNewPasswordText.getText().toString();
        if (newPassword.isEmpty()) {
            newPasswordText.setError("Enter a new password");
            return false;
        }
        else if(newPassword.length() < 6) {
            newPasswordText.setError("Password must be at least 6 characters");
            return false;
        }
        else if(!confirmNewPassword.equals(newPassword)) {
            confirmNewPasswordText.setError("Password does not match");
            return false;
        }
        else {
            return true;
        }
    }

    private void initAllComponents() {
        oldPasswordText = findViewById(R.id.input_old_password);
        newPasswordText = findViewById(R.id.input_new_password);
        confirmNewPasswordText = findViewById(R.id.input_confirm_new_password);
        backToHomeLink = findViewById(R.id.back_to_home_link);
        changePasswordButton = findViewById(R.id.btn_change_passsword);
    }
}
