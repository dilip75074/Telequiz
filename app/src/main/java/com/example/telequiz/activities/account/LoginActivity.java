package com.example.telequiz.activities.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.telequiz.R;
import com.example.telequiz.activities.home.MainActivity;
import com.example.telequiz.services.SessionManager;
import com.example.telequiz.services.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;

public class LoginActivity extends AppCompatActivity {

    Context context;
    SessionManager session;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    TextView signupLink, forgotPasswordLink;
    EditText emailText, passwordText;
    Button loginButton;
    CheckBox rememberMeCheckBox;
    ImageButton passwordToggleButton;

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
    }

    public void remoteAuthenticate() {
            /***
             * Make a http post request tp Check if user exist in our database or not.
             */
            final String email = emailText.getText().toString();
            final String password = passwordText.getText().toString();

            String url = Constant.BASE_URL + "account/authenticateuser";

            //your params.
            HashMap<String, String> params = new HashMap<>();
            params.put("email_id", email);
            params.put("password", password);

            ConnectionManager.volleyStringRequest(context, true, null, url, Request.Method.POST, params, new VolleyResponse() {
                @Override
                public void onResponse(String _response) {
                    String firstName = "";
                    String lastName = "";
                    String fullName = "";

                    /**
                     * Handle Response
                     */

                    JSONArray data = null;
                    try {
                        data = new JSONArray(_response);
                        JSONObject data_inside = data.getJSONObject(0);
                        firstName = data_inside.getString("first_name");
                        lastName = data_inside.getString("last_name");
                        fullName = firstName + " " + lastName;
                        onLoginSuccess(fullName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onLoginFailed("Couldn't connect to server");
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    /**
                     * handle Volley Error
                     */
                    onLoginFailed("Invalid email id or password");
                }

                @Override
                public void isNetwork(boolean connected) {
                    /**
                     * True if internet is connected otherwise false
                     */
                    if(!connected) {
                        onLoginFailed("Couldn't connect to server");
                    }
                }
            });
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

    public void onLoginFailed(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage(message)
                .setIcon(R.drawable.icon_error)
                .setCancelable(true)
                .setPositiveButton("Ok", null)
                .show();
    }

    private void initAllComponents() {
        context = this;
        session = new SessionManager(context);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        signupLink = findViewById(R.id.link_signup);
        forgotPasswordLink = findViewById(R.id.link_forgot_password);
        rememberMeCheckBox = findViewById(R.id.check_box_remeber_me);

    }
}
