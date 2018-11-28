package com.example.telequiz.activities.creatorStudio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.telequiz.R;
import com.example.telequiz.activities.creatorStudio.uploadQuestion.UploadQuestionActivity;

public class DashboardActivity extends AppCompatActivity {

    Context context;
    Button buttonFullAnalytics, buttonUploadQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_studio_dashboard);

        context = getApplicationContext();
        initAllComponents();

        buttonUploadQuestion.setOnClickListener(new View.OnClickListener() {

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
                Intent intent = new Intent(context, FullAnalyticsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initAllComponents() {
        buttonUploadQuestion = findViewById(R.id.btn_upload_question);
        buttonFullAnalytics = findViewById(R.id.btn_full_analytics);
    }
}