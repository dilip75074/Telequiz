package com.example.telequiz.activities.account;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.telequiz.R;
import com.example.telequiz.services.SessionManager;

import java.util.HashMap;

import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;

public class SignUpActivity extends AppCompatActivity {

    Context context;
    TextView loginLink, forgotPasswordLink;
    EditText firstNameText, lastNameText, emailText, passwordText, confirmPasswordText, mobileText;
    Button signupButton;
    CheckBox termOfServiceCheckBox;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Sign Up");
        context = getApplicationContext();
        initAllComponents();

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void validateUserData() {
        int statusCode = 401;
        if(isValidFirstName() &&
                isValidLastName() &&
                isValidEmail() &&
                isValidPassword() &&
                isValidConfirmPassword() &&
                isValidMobile() &&
                isTermOfServiceAccepted()) {
            new AlertDialog.Builder(this)
                    .setTitle("Sign Up")
                    .setMessage("Do you want to proceed ?")
                    .setIcon(R.drawable.icon_permission_warning)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            newUserRegistration();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    private void newUserRegistration() {

        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        String mobile = mobileText.getText().toString();

        String url = "http://localhost:9090/tlq_backend/account/registeruser";
        //your params.
        HashMap<String, String> params = new HashMap<>();
        params.put("first_name", firstName);
        params.put("last_name", lastName);
        params.put("email", email);
        params.put("password", password);
        params.put("confirm_password", confirmPassword);
        params.put("mobile", mobile);


        //your Header.
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("apiKey", "iuodslkadsi930923kldkljs43io");

        int statusCode = 400;
        ConnectionManager.volleyStringRequest(context, true, null, url, Request.Method.POST, params, headers, new VolleyResponse() {
            @Override
            public void onResponse(String _response) {

                Log.i("DK Response:", _response);
                /**
                 * Handle Response
                 */
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("DK Error:", String.valueOf(error));

                /**
                 * handle Volley Error
                 */
            }

            @Override
            public void isNetwork(boolean connected) {

                Log.i("DK connected:", String.valueOf(connected));
                /**
                 * True if internet is connected otherwise false
                 */
            }
        });

        /*int statusCode = 201;
        if(statusCode == 200) {
            new AlertDialog.Builder(this)
                    .setTitle("Sign Up")
                    .setMessage("User registration successful")
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
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Sign Up")
                    .setMessage("User registration Failed")
                    .setIcon(R.drawable.icon_error)
                    .setCancelable(false)
                    .setPositiveButton("Reason", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    })
                    .show();
        }*/
    }

    private boolean isValidFirstName() {
        String firstName = firstNameText.getText().toString();
        if (firstName.isEmpty()) {
            firstNameText.setError("Enter first name");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isValidLastName() {
        String firstName = lastNameText.getText().toString();
        if (firstName.isEmpty()) {
            lastNameText.setError("Enter last name");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isValidGender() {
        return false;
    }

    private boolean isValidEmail() {
        String email = emailText.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email id");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isValidPassword() {
        String password = passwordText.getText().toString();
        if (password.isEmpty()) {
            passwordText.setError("Enter a valid password");
            return false;
        }
        else if(password.length() < 6) {
            passwordText.setError("Password must be at least 6 characters");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isValidConfirmPassword() {
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        if (confirmPassword.isEmpty()) {
            confirmPasswordText.setError("Enter the confirmation password");
            return false;
        }
        else if (!confirmPassword.equals(password)  ) {
            confirmPasswordText.setError("Password does not match");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isValidMobile() {
        String mobileNo = mobileText.getText().toString();
        if (mobileNo.isEmpty()) {
            mobileText.setError("Enter a valid mobile number");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isTermOfServiceAccepted() {
        if(termOfServiceCheckBox.isChecked()) {
            return true;
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Term of Service")
                    .setMessage("You must accept our term of service.")
                    .setIcon(R.drawable.icon_warning)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    })
                    .show();
            return false;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            }
        }
    };


    private void initAllComponents() {
        context = this;
        session = new SessionManager(context);
        firstNameText = findViewById(R.id.input_first_name);
        lastNameText = findViewById(R.id.input_last_name);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        confirmPasswordText = findViewById(R.id.input_confirm_password);
        mobileText = findViewById(R.id.input_mobile_no);
        signupButton = findViewById(R.id.btn_signup);
        loginLink = findViewById(R.id.link_login);
        forgotPasswordLink = findViewById(R.id.link_forgot_password);
        termOfServiceCheckBox = findViewById(R.id.term_of_service_check_box);
    }
}
