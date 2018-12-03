package com.example.telequiz.activities.quiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.telequiz.R;

import static java.lang.Integer.*;

public class PlayQuizActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    CountDownTimer countDownTimer = null;
    Button nextButton, previousButton;
    TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);
        setTitle("Revision Classes");

        initAllComponents();
//        runTimerProgress();
        setTimer(0, 1, 0);
        /*
         * Next quiz
         * */
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        nextButton = findViewById(R.id.button_next);
        previousButton = findViewById(R.id.button_previous);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRightToLeftEntry();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateLeftToRightEntry();
            }
        });

        /*
        * Previous quiz
        * */
       // this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);



    }

    private void animateRightToLeftEntry() {
        final View childView = findViewById(R.id.quiz_question);
        View containerView = findViewById(R.id.quiz_container);
        childView.setTranslationX(containerView.getWidth());
        long TRANSLATION_DURATION = 1000;
        childView.animate()
                .translationXBy(-containerView.getWidth())
                .setDuration(TRANSLATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        childView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void animateLeftToRightEntry() {
        final View childView = findViewById(R.id.quiz_question);
        View containerView = findViewById(R.id.quiz_container);
        childView.setTranslationX(-containerView.getWidth());
        long TRANSLATION_DURATION = 1000;
        childView.animate()
                .translationXBy(containerView.getWidth())
                .setDuration(TRANSLATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        childView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void animateRightToLeftExit() {
        final View childView = findViewById(R.id.quiz_question);
        View containerView = findViewById(R.id.quiz_container);
        long TRANSLATION_DURATION = 1000;
        childView.animate()
                .translationXBy(-containerView.getWidth())
                .setDuration(TRANSLATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        childView.setVisibility(View.GONE);
                    }
                });
    }

    private void setTimer(final int durationInHour, int durationInMinute, int durationInSecond) {
        int durationInMilli = durationInHour * 60 * 60 * 1000;
        durationInMilli += durationInMinute * 60 * 1000;
        durationInMilli += durationInMinute * 1000;

        final int[] progress = {durationInMilli};
        progressBar.setMax(progress[0]);
        progressBar.setProgress(progress[0]);

        countDownTimer = new CountDownTimer(durationInMilli, 1000) {
            public void onTick(long millisUntilFinished) {
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;

                long elapsedHours = millisUntilFinished / hoursInMilli;
                millisUntilFinished = millisUntilFinished % hoursInMilli;

                long elapsedMinutes = millisUntilFinished / minutesInMilli;
                millisUntilFinished = millisUntilFinished % minutesInMilli;

                long elapsedSeconds = millisUntilFinished / secondsInMilli;

                String timerText = "";
                if (durationInHour > 0)
                    timerText = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
                else
                    timerText = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
                timerTextView.setText("Time Left: " + timerText);

                progress[0] -= 1000;

                if(progress[0] < (progressBar.getMax() / 3)) {
                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                }
                else if(progress[0] < (progressBar.getMax() / 2)) {
                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#b7c960")));
                }
                //progressBar.setProgress(progressBar.getMax() - progress);
                progressBar.setProgress(progress[0]);
            }

            public void onFinish() {
                String timerText = "";
                if (durationInHour > 0)
                    timerText = "00:00:00";
                else
                    timerText = "00:00";
                timerTextView.setText("Time Left: " + timerText);
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ff5e69")));
                progressBar.setProgress(0);
//                Quiz.this.onNextButtonPressed(nextButton);
            }
        }.start();
    }

    private void initAllComponents() {
        progressBar = findViewById(R.id.progressBar);
        timerTextView = findViewById(R.id.timer_text_view);
    }
}
