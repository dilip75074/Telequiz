package com.example.telequiz.activities.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telequiz.R;

public class NextQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        setContentView(R.layout.activity_play_quiz);
        TextView tv = (TextView) findViewById(R.id.tvTitle);
        tv.setText("First Activity");
        Button bt = (Button) findViewById(R.id.buttonNext);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NextQuizActivity.this, PreviousQuizActivity.class);
                startActivity(i);

            }
        });
    }
}
