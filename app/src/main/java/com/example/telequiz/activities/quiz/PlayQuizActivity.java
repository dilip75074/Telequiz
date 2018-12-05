package com.example.telequiz.activities.quiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;
import com.example.telequiz.services.utilities.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;

public class PlayQuizActivity extends AppCompatActivity {

    Context context;

    ProgressBar progressBar;

    ScrollView mainQuizScrollView;
    CountDownTimer countDownTimer = null;

    Button nextButton, previousButton;

    TextView timerTextView, quizQuestionTextView, quesNoTextView;

    LinearLayout commentLinearLayout, quizContainerLayout;

    RadioButton optionRadioButton[] = new RadioButton[4];

    ImageButton likeButton, unlikeButton, shareButton, commentButton;

    boolean isLikeButtonPressed, isUnlikeButtonPressed;

    private static int quesNo = 1;
    private static int totalQuestion;
    JSONObject allQuestionsDataObj = null;
    JSONObject currentQuesDataObj;
    JSONArray questionDataArray;

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


        /*nextButton.setOnClickListener(new View.OnClickListener() {
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
        });*/

        /*
        * Previous quiz
        * */
       // this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

    }

    private void animateRightToLeftEntry(final View childView, View containerView) {
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
//                Quiz.this.onNextButtonPressed(nextButton);
            }
        }.start();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case R.id.like_button:
                    onLikeButtonClick();
                    break;

                case R.id.unlike_button:
                    onUnlikeButtonClick();
                    break;

                case R.id.share_button:
                    onShareButtonClick();
                    break;

                case R.id.comment_button:
                   onCommentButtonClick();
                   break;
               /*
               * Action for Option radio button click
               * */
                case R.id.radio_button_a:
                    onOptionAClick();
                    break;
                case R.id.radio_button_b:
                    onOptionBClick();
                    break;
                case R.id.radio_button_c:
                    onOptionCClick();
                    break;
                case R.id.radio_button_d:
                    onOptionDClick();
                    break;

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
//        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        initAllComponents();
        if (quesNo > 1) {
            quesNo--;
            nextButton.setText("Next");
            setCurrentQuizData();
        } else {
            return;
        }
    }

    private void onNextButtonClick() {
//        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        initAllComponents();
        if (quesNo < questionDataArray.length()) {
            quesNo++;
            nextButton.setText("Next");
            setCurrentQuizData();
        } else if (quesNo == questionDataArray.length()) {
            nextButton.setText("Finish");
            Message.message(context, "Quiz Finished, see the Quiz Report");
        } else  {
            return;
        }
    }

    private void onLikeButtonClick() {
        isLikeButtonPressed = !isLikeButtonPressed;
        int likeButtonBackgroundImage = (isLikeButtonPressed)
                ? R.drawable.icon_like_pressed
                : R.drawable.icon_like_not_pressed;

        likeButton.setBackgroundResource(likeButtonBackgroundImage);
    }

    private void onUnlikeButtonClick() {
        isUnlikeButtonPressed = !isUnlikeButtonPressed;
        int unlikeButtonBackgroundImage = (isUnlikeButtonPressed)
                ? R.drawable.icon_unlike_pressed
                : R.drawable.icon_unlike_not_pressed;

        unlikeButton.setBackgroundResource(unlikeButtonBackgroundImage);
    }

    private void onShareButtonClick() {
    }

    private void onCommentButtonClick() {
        if (commentLinearLayout.getVisibility() == View.VISIBLE) {
            commentLinearLayout.setVisibility(View.GONE);
        }
        else {
            commentLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void onOptionAClick() {
        for (int i = 0; i<4; i++ ) {
            if(i == 0) {
                optionRadioButton[i].setChecked(true);
            }
            else {
                optionRadioButton[i].setChecked(false);
            }
        }
    }

    private void onOptionBClick() {
        for (int i = 0; i<4; i++ ) {
            if(i == 1) {
                optionRadioButton[i].setChecked(true);
            }
            else {
                optionRadioButton[i].setChecked(false);
            }
        }
    }

    private void onOptionCClick() {
        for (int i = 0; i<4; i++ ) {
            if(i == 2) {
                optionRadioButton[i].setChecked(true);
            }
            else {
                optionRadioButton[i].setChecked(false);
            }
        }
    }

    private void onOptionDClick() {
        for (int i = 0; i<4; i++ ) {
            if(i == 3) {
                optionRadioButton[i].setChecked(true);
            }
            else {
                optionRadioButton[i].setChecked(false);
            }
        }
    }

    private void startQuiz() {
        setCurrentQuizData();
    }

    private void setCurrentQuizData() {
          String option[]= new String[4];
//        option_RadioGroup.clearCheck();
//        resetRadioButtons();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        setTimer(0, 1, 0);

        if (quesNo == 1)
            enableButton(previousButton, false);
        else if (quesNo > 1)
            enableButton(previousButton, true);

        if (quesNo == questionDataArray.length()) {
            // enableButton(nextButton, false);
            nextButton.setText("Finish");
        }
        else if (quesNo < questionDataArray.length())
            enableButton(nextButton, true);

        currentQuesDataObj = questionDataArray.optJSONObject(quesNo - 1);
        String question = currentQuesDataObj.optString(Constant.QUESTION);

        option[0] = currentQuesDataObj.optString(Constant.OPTION_A);
        option[1] = currentQuesDataObj.optString(Constant.OPTION_B);
        option[2] = currentQuesDataObj.optString(Constant.OPTION_C);
        option[3] = currentQuesDataObj.optString(Constant.OPTION_D);

        quizQuestionTextView.setText(question);
        quesNoTextView.setText(quesNo + "/" + totalQuestion);
        quizQuestionTextView.setText(question);
        for (int i = 0; i < 4; i++) {
            optionRadioButton[i].setText(option[i]);
        }
    }

    private void fetchQuestions(String _url) {
        ConnectionManager.volleyStringRequest(this, false, null, _url, new VolleyResponse() {
            @Override
            public void onResponse(String response) {
                /**
                 * Handle Response
                 */
                try {
                    allQuestionsDataObj = new JSONObject(response);
                    enableButton(nextButton, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                prepareAllQuestions();
                startQuiz();
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

    private void prepareAllQuestions() {
        questionDataArray = allQuestionsDataObj.optJSONArray("records");
        totalQuestion = questionDataArray.length();
//        for (int i = 0; i < questionDataArray.length(); i++) {
//
//        }
    }

    public void enableButton(Button button, Boolean isEnabled) {
        button.setEnabled(isEnabled);
    }

    private void initAllComponents() {
        setContentView(R.layout.activity_play_quiz);
        setTitle("Revision Classes");
        
        context = this;

        mainQuizScrollView = findViewById(R.id.mainQuizScrollView);

        quizContainerLayout = findViewById(R.id.quiz_container);

        progressBar = findViewById(R.id.progressBar);

        quesNoTextView = findViewById(R.id.ques_no);
        timerTextView = findViewById(R.id.timer_text_view);
        quizQuestionTextView = findViewById(R.id.quiz_question);

        commentLinearLayout = findViewById(R.id.comment_linear_layout);
        commentLinearLayout.setVisibility(View.GONE);

        likeButton = findViewById(R.id.like_button);
        likeButton.setOnClickListener(onClickListener);
        isLikeButtonPressed = false;

        unlikeButton = findViewById(R.id.unlike_button);
        unlikeButton.setOnClickListener(onClickListener);
        isUnlikeButtonPressed = false;

        shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(onClickListener);

        commentButton = findViewById(R.id.comment_button);
        commentButton.setOnClickListener(onClickListener);

        optionRadioButton[0] = findViewById(R.id.radio_button_a);
        optionRadioButton[1] = findViewById(R.id.radio_button_b);
        optionRadioButton[2] = findViewById(R.id.radio_button_c);
        optionRadioButton[3] = findViewById(R.id.radio_button_d);
        for (int i = 0; i<4; i++) {
            optionRadioButton[i].setOnClickListener(onClickListener);
        }

        previousButton = findViewById(R.id.button_previous);
        previousButton.setOnClickListener(onClickListener);

        nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(onClickListener);
        enableButton(previousButton, false);
        enableButton(nextButton, false);

    }
}
