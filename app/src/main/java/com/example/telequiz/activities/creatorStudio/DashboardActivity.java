package com.example.telequiz.activities.creatorStudio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.telequiz.R;
import com.example.telequiz.activities.creatorStudio.channel.channelAnalytics.ChannelAnalyticsActivity;
import com.example.telequiz.activities.creatorStudio.uploadQuestion.UploadQuestionActivity;
import com.example.telequiz.activities.home.MainActivity;

public class DashboardActivity extends AppCompatActivity {

    Context context;
    CardView buttonSetNewQuiz, buttonFullAnalytics, buttonRevenueReports,
                buttonAllSubscribers, buttonAllViews, buttonAllLikes, buttonAllShares;

    Menu menu;

    String currency = "INR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_studio_dashboard);
        setTitle("Creator Dashboard");

        initAllComponents();

        buttonSetNewQuiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Upload question activity
                Intent intent = new Intent(context, UploadQuestionActivity.class);
                startActivity(intent);
            }
        });

        buttonFullAnalytics.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Upload question activity
                Intent intent = new Intent(context, UploadQuestionActivity.class);
                startActivity(intent);
            }
        });

        buttonRevenueReports.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Upload question activity
                Intent intent = new Intent(context, UploadQuestionActivity.class);
                startActivity(intent);
            }
        });

        buttonAllSubscribers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Upload question activity
                Intent intent = new Intent(context, UploadQuestionActivity.class);
                startActivity(intent);
            }
        });

        buttonAllViews.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Upload question activity
                Intent intent = new Intent(context, UploadQuestionActivity.class);
                startActivity(intent);
            }
        });

        buttonAllLikes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Upload question activity
                Intent intent = new Intent(context, UploadQuestionActivity.class);
                startActivity(intent);
            }
        });

        buttonAllShares.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Upload question activity
                Intent intent = new Intent(context, UploadQuestionActivity.class);
                startActivity(intent);
            }
        });

        buttonFullAnalytics.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Full analytics activity
                Intent intent = new Intent(context, ChannelAnalyticsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);

        menu.setGroupVisible(R.id.main_activity_menu_group, false);
        menu.setGroupVisible(R.id.creator_studio_menu_group, true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.channel_settings_menu:
                
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    private void initAllComponents() {
        context = getApplicationContext();
        buttonSetNewQuiz = findViewById(R.id.button_new_quiz);
        buttonFullAnalytics = findViewById(R.id.button_full_analytics);
        buttonSetNewQuiz = findViewById(R.id.button_all_quiz);
        buttonRevenueReports = findViewById(R.id.button_revenue_reports);
        buttonAllSubscribers = findViewById(R.id.button_all_subscribers);
        buttonAllViews = findViewById(R.id.button_all_views);
        buttonAllLikes = findViewById(R.id.button_all_likes);
        buttonAllShares = findViewById(R.id.button_all_shares);
    }
}