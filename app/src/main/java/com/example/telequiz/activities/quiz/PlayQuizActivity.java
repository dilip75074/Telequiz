package com.example.telequiz.activities.quiz;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;

public class PlayQuizActivity extends AppCompatActivity {

    Context context;

    ProgressBar progressBar;

    ScrollView mainQuizScrollView;
    CountDownTimer countDownTimer = null;

    Button nextButton, previousButton;

    TextView timerTextView, quizQuestionTextView, quesNoTextView;

    ListView quizQuestionListView;

    private static int quesNo = 1;
    QuizDataListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAllComponents();
        fetchQuestions("https://script.googleusercontent.com/macros/echo?user_content_key=Q1pY--ZDRt0Uvzwqyy4N2pTv1mF21tpmA5hfDLBb6Cwq_umMHIampOyIcmSxHG2PB7Zf4hgfMQBAo75Oks4Dk5X8Wi2p2dN1OJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa5V7SzAZj2xBfFDRtNxpfsmuqfjnOYLBpWrI3G8IWJh29l4LSossvEa_fiNHZ0znxEBErwHi9mmiKsGjjxV69g1lj0UBDMnlBbQK1FmJT9l4MvA28FQO80k4mrHdASoCwoPJ_1nI_0tywqJbeTowOR_fVIgxkfofiQ&lib=M7OO09pfGNQD9igEAo4bouJoiE_6Oxspk");
        setTimer(0, 1, 0);
        /*
         * Next quiz
         * */
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        nextButton = findViewById(R.id.button_next);

        /*
        * Previous quiz
        * */
       // this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

    }

   /* private void animateRightToLeftEntry(final View childView, View containerView) {
//        final View childView = findViewById(R.id.quiz_question);
//        View containerView = findViewById(R.id.quiz_container);
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

    private void animateLeftToRightEntry(final View childView, View containerView) {
//        final View childView = findViewById(R.id.quiz_question);
//        View containerView = findViewById(R.id.quiz_container);
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
    }*/

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
                else {
                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#07f22e")));
                }
                progressBar.setProgress(progress[0]);
            }

            public void onFinish() {
                String timerText = "";
                if (durationInHour > 0)
                    timerText = "00:00:00";
                else
                    timerText = "00:00";
                timerTextView.setText("Time Left: " + timerText);
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                progressBar.setProgress(0);
            }
        }.start();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                /*
                 * Previous button and next button click
                 * */
                case R.id.button_previous:
                    onPreviousButtonClick();
                    break;
                case R.id.button_next:
                    onNextButtonClick();
                    break;
            }
        }
    };

    private void onPreviousButtonClick() {
    }

    private void onNextButtonClick() {

    }

    private void fetchQuestions(String _url) {
        ConnectionManager.volleyStringRequest(this, false, null, _url, new VolleyResponse() {
            @Override
            public void onResponse(String response) {
                /**
                 * Handle Response
                 */
                try {
                    setCustomListViewAdapter(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

                /**
                 * handle Volley Error
                 */
            }

            @Override
            public void isNetwork(boolean connected) {

                /**
                 * True if internet is connected otherwise false
                 */
            }
        });
    }

    private void setCustomListViewAdapter(String quizQuestions) throws JSONException {
        JSONObject obj = new JSONObject(quizQuestions);
        JSONArray m_jArry = obj.getJSONArray("records");

        List<QuizDataModel> quizDataList = new ArrayList<>();
        HashMap<String, String> m_li;

        for (int i = 0; i < m_jArry.length(); i++) {
            JSONObject json_inside = m_jArry.getJSONObject(i);
            String sNo = !json_inside.getString(Constant.SL_NO).isEmpty() ? json_inside.getString(Constant.SL_NO) : null;
            String questionEnglish = !json_inside.getString(Constant.QUESTION).isEmpty() ? json_inside.getString(Constant.QUESTION) : null;
            String optionAEnglish = !json_inside.getString(Constant.OPTION_A).isEmpty() ? json_inside.getString(Constant.OPTION_A) : null;
            String optionBEnglish = !json_inside.getString(Constant.OPTION_B).isEmpty() ? json_inside.getString(Constant.OPTION_B) : null;
            String optionCEnglish = !json_inside.getString(Constant.OPTION_C).isEmpty() ? json_inside.getString(Constant.OPTION_C) : null;
            String optionDEnglish = !json_inside.getString(Constant.OPTION_D).isEmpty() ? json_inside.getString(Constant.OPTION_D) : null;
            String correctOption = !json_inside.getString(Constant.CORRECT_OPTION).isEmpty() ? json_inside.getString(Constant.CORRECT_OPTION) : null;

            m_li = new HashMap<String, String>();
            m_li.put(Constant.SL_NO, sNo);
            m_li.put(Constant.QUESTION, questionEnglish);
            m_li.put(Constant.OPTION_A, optionAEnglish);
            m_li.put(Constant.OPTION_B, optionBEnglish);
            m_li.put(Constant.OPTION_C, optionCEnglish);
            m_li.put(Constant.OPTION_D, optionDEnglish);
            m_li.put(Constant.CORRECT_OPTION, correctOption);

            quizDataList.add(new QuizDataModel(m_li));
        }

        //creating the adapter
        adapter = new QuizDataListAdapter(this, R.layout.activity_play_quiz_qestion_list, quizDataList);

        //attaching adapter to the listview
        quizQuestionListView.setAdapter(adapter);
    }

    private void initAllComponents() {
        setContentView(R.layout.activity_play_quiz);
        setTitle("Revision Classes");
        
        context = this;

        mainQuizScrollView = findViewById(R.id.mainQuizScrollView);

        progressBar = findViewById(R.id.progressBar);

        quesNoTextView = findViewById(R.id.ques_no);
        timerTextView = findViewById(R.id.timer_text_view);
        quizQuestionTextView = findViewById(R.id.quiz_question);

        quizQuestionListView = findViewById(R.id.quiz_question_list);
        previousButton = findViewById(R.id.button_previous);
        previousButton.setOnClickListener(onClickListener);

        nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(onClickListener);
    }
}