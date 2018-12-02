package com.example.telequiz.activities;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.telequiz.R;

public class AppUpdateNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_update);
        setTitle("Telequiz Update");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.setGroupVisible(R.id.main_activity_menu_group, true);
        menu.setGroupVisible(R.id.creator_studio_menu_group, false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
            return;  //do nothing
    }
}
