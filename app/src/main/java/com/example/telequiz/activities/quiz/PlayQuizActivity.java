package com.example.telequiz.activities.quiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.telequiz.R;
import com.example.telequiz.activities.home.MainActivity;
import com.example.telequiz.services.OverflowMenuManager;
import com.example.telequiz.services.ProgressDialogManager;
import com.example.telequiz.services.utilities.Constant;
import com.example.telequiz.services.utilities.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;

public class PlayQuizActivity extends AppCompatActivity {

    Context context;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    //ScrollView mainQuizScrollView;
    CountDownTimer countDownTimer = null;

    Button nextButton, previousButton, reviewCloseButton, quizStartButton;

    TextView timerTextView, quizQuestionTextView;
    public static TextView quesNoTextView;
    TextView totalAttemptedTextView, totalNotAttemptedTextView, totalCorrectAnsTextView, totalIncorrectAnsTextView;

    LinearLayout navButtonContainer, correctIncorrectTextContainer;
    ScrollView gridLayoutScroller;
    GridLayout gridLayout;

    ImageButton scrollToTopButton;
    ListView quizQuestionListView;

    public static List<QuizDataModel> quizDataList;
    public static String mode;
    public static List<Button> quizQuestionStatusButtonList;
    public static int quesNo = 1;
    public static int totalQuestion;
    QuizDataListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAllComponents();
        /*
         * Next quiz
         * */
        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        nextButton = findViewById(R.id.button_next);

        /*
         * Previous quiz
         * */
        // this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

        scrollToTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizQuestionListView.setSelection(1);
                quizQuestionListView.smoothScrollToPosition(0);
            }
        });

        quizQuestionListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem  > 1) {
                    scrollToTopButton.setVisibility(View.VISIBLE);
                }
                else {
                    scrollToTopButton.setVisibility(View.GONE);
                }
                if(mLastFirstVisibleItem<firstVisibleItem) {
                   // Log.i("DK SCROLLING DOWN","TRUE");
                }
                if(mLastFirstVisibleItem>firstVisibleItem) {
                   // Log.i("DK SCROLLING UP","TRUE");
                }
                mLastFirstVisibleItem=firstVisibleItem;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(gridLayoutScroller.getVisibility() == View.VISIBLE) {
            gridLayoutScroller.setVisibility(View.GONE);
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                })

                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        OverflowMenuManager overflowMenu = new OverflowMenuManager(menu);

        overflowMenu.showGroup(R.id.play_quiz_menu_group);
        overflowMenu.hideItem(R.id.app_bar_search);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ScrollView quizIntroContainer = findViewById(R.id.quiz_intro_scrollbar);
        if(quizIntroContainer.getVisibility() == View.VISIBLE)
            return super.onOptionsItemSelected(item);

        if (id == R.id.jump_to_question_menu) {
            onJumpToQuestionMenuSelected();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onJumpToQuestionMenuSelected() {


        if(gridLayoutScroller.getVisibility() == View.VISIBLE) {
            gridLayoutScroller.setVisibility(View.GONE);
            return;
        }

        boolean isPracticeMode = mode.toLowerCase().equals(Constant.MODE_PRACTICE);
        boolean isReviewMode = mode.toLowerCase().equals(Constant.MODE_REVIEW);
        //boolean isExamMode = mode.toLowerCase().equals(Constant.MODE_EXAM);

        if(isPracticeMode || isReviewMode) {
            correctIncorrectTextContainer.setVisibility(View.VISIBLE);
        }
        else {
            correctIncorrectTextContainer.setVisibility(View.GONE);
        }
        updateQuestionNoStatusUI_Style();
        gridLayoutScroller.setVisibility(View.VISIBLE);
    }

    private void updateQuestionNoStatusUI_Style() {
        boolean isPracticeMode = mode.toLowerCase().equals(Constant.MODE_PRACTICE);
        boolean isReviewMode = mode.toLowerCase().equals(Constant.MODE_REVIEW);

        int totalAttempted = 0;
        int totalNotAttempted = 0;
        int totalCorrect = 0;
        int totalIncorrect = 0;

        for (int i = 0; i < totalQuestion; i++) {
            final Button mButton = quizQuestionStatusButtonList.get(i);
            String userOption = quizDataList.get(i).quesData.get(Constant.USER_OPTION);
            userOption = (userOption == null) ? "E" : userOption.toUpperCase();
            String correctOption = quizDataList.get(i).quesData.get(Constant.CORRECT_OPTION).toUpperCase();

            if(userOption.equals("E")){
                totalNotAttempted += 1;
                mButton.setBackgroundResource(R.drawable.circle_background_3d_gray);
            }
            else if(!userOption.equals(correctOption) && (isPracticeMode || isReviewMode)) {
                totalIncorrect += 1;
                mButton.setBackgroundResource(R.drawable.circle_background_3d_green_red);
            }
            else {
                mButton.setBackgroundResource(R.drawable.circle_background_3d_green);
            }
            quizQuestionStatusButtonList.set(i, mButton);
        }

        totalAttempted = totalQuestion - totalNotAttempted;
        totalCorrect= totalAttempted - totalIncorrect;

        totalAttemptedTextView.setText(String.valueOf(totalAttempted));
        totalNotAttemptedTextView.setText(String.valueOf(totalNotAttempted));

        totalCorrectAnsTextView.setText(String.valueOf(totalCorrect));
        totalIncorrectAnsTextView.setText(String.valueOf(totalIncorrect));
    }

    private void jumpToQuestionNo(int buttonId) {
        gridLayoutScroller.setVisibility(View.GONE);
        quesNo = buttonId + 1; // quesNo = 1

        if(mode.toLowerCase().equals(Constant.MODE_REVIEW)) {
            quizQuestionListView.setSelection(quesNo - 1);
            if(quesNo > 2) {
                scrollToTopButton.setVisibility(View.VISIBLE);
            }
            else {
                scrollToTopButton.setVisibility(View.GONE);
            }
        }
        else {
            if (quesNo == totalQuestion) {
                nextButton.setText("Finish");
            } else {
                nextButton.setText("Next");
            }
            displayQuestion(quesNo); //displaying very first question
        }
    }

    private void setTimer(final int durationInHour, int durationInMinute, int durationInSecond) {
        int durationInMilli = durationInHour * 60 * 60 * 1000;
        durationInMilli += durationInMinute * 60 * 1000;
        durationInMilli += durationInSecond * 1000;

        final int[] progress = {durationInMilli};
        progressBar.setMax(progress[0]);
        progressBar.setProgress(progress[0]);

        final long[] elapsedHours = new long[1];
        final long[] elapsedMinutes = new long[1];
        final long[] elapsedSeconds = new long[1];

        countDownTimer = new CountDownTimer(durationInMilli, 1000) {
            public void onTick(long millisUntilFinished) {
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;

                elapsedHours[0] = millisUntilFinished / hoursInMilli;
                millisUntilFinished = millisUntilFinished % hoursInMilli;

                elapsedMinutes[0] = millisUntilFinished / minutesInMilli;
                millisUntilFinished = millisUntilFinished % minutesInMilli;

                elapsedSeconds[0] = millisUntilFinished / secondsInMilli;

                String timerText = "";
                if (elapsedHours[0] > 0)
                    timerText = String.format("%02d:%02d:%02d", elapsedHours[0], elapsedMinutes[0], elapsedSeconds[0]);
                else
                    timerText = String.format("%02d:%02d", elapsedMinutes[0], elapsedSeconds[0]);
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
                if (elapsedHours[0] > 0)
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
                    onPreviousButtonClicked();
                    break;
                case R.id.button_next:
                    onNextButtonClicked();
                    break;
                case R.id.button_review_close:
                    onReviewCloseButtonClick();
                    break;
                case R.id.quiz_start_button:
                    onQuizStartButtonClicked();
                    break;


            }
        }
    };

    private void onPreviousButtonClicked() {
        if (quesNo > 1) {
            quesNo--;
            nextButton.setText("Next");
            displayQuestion(quesNo);
        }
    }

    private void onNextButtonClicked() {
        if(nextButton.getText().toString().toUpperCase().equals("FINISH"))
            onFinishButtonClick();

        if (quesNo < totalQuestion) {
            quesNo++;
            if (quesNo == totalQuestion) {
                nextButton.setText("Finish");
            } else {
                nextButton.setText("Next");
            }
            displayQuestion(quesNo);
        }
    }

    private void onFinishButtonClick() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to submit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final ProgressDialogManager pd = new ProgressDialogManager(context);
                        pd.showProgressDialod("Processing", "Please wait...");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                reviewAllQuestions();
                                pd.hideProgressDialod();
                            }
                        }, 3000);   //3 seconds
                    }
                })

                .setNegativeButton("No", null)
                .show();
    }

    private void onReviewCloseButtonClick() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to close?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                })

                .setNegativeButton("No", null)
                .show();
    }

    private void onQuizStartButtonClicked() {
        RadioButton examModeRadioButon = findViewById(R.id.exam_mode_radio_button);
        //RadioButton practiceModeRadioButon = findViewById(R.id.practice_mode_radio_button);
        if(examModeRadioButon.isChecked())
            mode = Constant.MODE_EXAM;
        else
            mode = Constant.MODE_PRACTICE;
        fetchQuestions("https://script.google.com/macros/s/AKfycbzGvKKUIaqsMuCj7-A2YRhR-f7GZjl4kSxSN1YyLkS01_CfiyE/exec?id=1f74jia8lVqijhy_wVAdd5jliv54-oMBJjpAe9MIulfs&sheet=Sheet1");
    }

    private void hideQuizIntroduction() {
        LinearLayout quizStartButtonContainer = findViewById(R.id.quiz_start_button_container);
        ScrollView quizIntroductionScrollView = findViewById(R.id.quiz_intro_scrollbar);
        quizStartButtonContainer.setVisibility(View.GONE);
        quizIntroductionScrollView.setVisibility(View.GONE);
    }
    private void fetchQuestions(String _url) {
        final ProgressDialogManager pd = new ProgressDialogManager(context);
        pd.showProgressDialod("Loading", "Please wait...");
        ConnectionManager.volleyStringRequest(this, false, null, _url, new VolleyResponse() {
            @Override
            public void onResponse(String response) {
                /**
                 * Handle Response
                 */
                pd.hideProgressDialod();
                hideQuizIntroduction();
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
                pd.hideProgressDialod();
                hideQuizIntroduction();
                Message.message(context, "Couldn't connect to Server");
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void isNetwork(boolean connected) {

                /**
                 * True if internet is connected otherwise false
                 */
                if(!connected) {
                    pd.hideProgressDialod();
                    hideQuizIntroduction();
                    Message.message(context, "Couldn't connect to Internet");
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setCustomListViewAdapter(String quizQuestions) throws JSONException {
        JSONObject obj = new JSONObject(quizQuestions);
        JSONArray m_jArry = obj.getJSONArray("records");

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
            String userOption = !json_inside.getString(Constant.USER_OPTION).isEmpty() ? json_inside.getString(Constant.USER_OPTION) : null;
            String totalViews = !json_inside.getString(Constant.TOTAL_VIEWS).isEmpty() ? json_inside.getString(Constant.TOTAL_VIEWS) : null;
            String totalLikes = !json_inside.getString(Constant.TOTAL_LIKES).isEmpty() ? json_inside.getString(Constant.TOTAL_LIKES) : null;
            String totalUnlikes = !json_inside.getString(Constant.TOTAL_UNLIKES).isEmpty() ? json_inside.getString(Constant.TOTAL_UNLIKES) : null;
            String totalShares = !json_inside.getString(Constant.TOTAL_SHARES).isEmpty() ? json_inside.getString(Constant.TOTAL_SHARES) : null;
            String totalComments = !json_inside.getString(Constant.TOTAL_COMMENTS).isEmpty() ? json_inside.getString(Constant.TOTAL_COMMENTS) : null;
            String comments = !json_inside.getString(Constant.COMMENTS).isEmpty() ? json_inside.getString(Constant.COMMENTS) : null;

            m_li = new HashMap<String, String>();
            m_li.put(Constant.SL_NO, sNo);
            m_li.put(Constant.QUESTION, questionEnglish);
            m_li.put(Constant.OPTION_A, optionAEnglish);
            m_li.put(Constant.OPTION_B, optionBEnglish);
            m_li.put(Constant.OPTION_C, optionCEnglish);
            m_li.put(Constant.OPTION_D, optionDEnglish);
            m_li.put(Constant.CORRECT_OPTION, correctOption);
            m_li.put(Constant.USER_OPTION, userOption);
            m_li.put(Constant.TOTAL_VIEWS, totalViews);
            m_li.put(Constant.TOTAL_LIKES, totalLikes);
            m_li.put(Constant.TOTAL_UNLIKES, totalUnlikes);
            m_li.put(Constant.TOTAL_SHARES, totalShares);
            m_li.put(Constant.TOTAL_COMMENTS, totalComments);
            m_li.put(Constant.COMMENTS, comments);

            quizDataList.add(new QuizDataModel(m_li));
        }

        Collections.shuffle(quizDataList, new Random(System.nanoTime()));
        navButtonContainer.setVisibility(View.VISIBLE);
        totalQuestion = quizDataList.size();
        addQuestionNoButtonToGridLayout();
        setTimer(0, 90, 0);
        quesNo = Constant.VERY_FIRST_QUIZ_QUESTION; // quesNo = 1
        displayQuestion(quesNo); //displaying very first question
    }

    private void reviewAllQuestions() {

        mode = Constant.MODE_REVIEW;
        previousButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        reviewCloseButton.setVisibility(View.VISIBLE);
        //creating the adapter

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        adapter = new QuizDataListAdapter(this, R.layout.activity_play_quiz_question_list, quizDataList);

        //attaching adapter to the listview
        quizQuestionListView.setAdapter(adapter);
    }

    private void addQuestionNoButtonToGridLayout() {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 80);
        gridLayout.setColumnCount(noOfColumns);
        gridLayout.removeAllViews();
        quizQuestionStatusButtonList.clear();
//        Button mButton[] = new Button[totalQuestion];
        for(int i = 0; i < totalQuestion; i++) {
            Button mButton = new Button(context);
            mButton.setText(String.valueOf(i+1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110, 110);
            params.setMargins(5,5,5,5);
            mButton.setLayoutParams(params);
            mButton.setTag(i);

            // Initially no questions are attempted
            mButton.setTextColor(getResources().getColor(R.color.black));
            mButton.setBackgroundResource(R.drawable.circle_background_3d_gray);

            mButton.setTypeface(null, Typeface.BOLD);
            mButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int mButton_id = Integer.parseInt(v.getTag().toString().trim());
                    jumpToQuestionNo(mButton_id);
                }
            });
            quizQuestionStatusButtonList.add(mButton);
            gridLayout.addView(quizQuestionStatusButtonList.get(i));
        }
    }

    private void displayQuestion(int quesNo) {
        List<QuizDataModel> quizSingleQuestionData = new ArrayList<>();
        quizSingleQuestionData.add(quizDataList.get(quesNo-1));
        //creating the adapter
        adapter = new QuizDataListAdapter(this, R.layout.activity_play_quiz_question_list, quizSingleQuestionData);

        //attaching adapter to the listview
        quizQuestionListView.setAdapter(adapter);
        quesNoTextView.setText(quesNo +"/" + totalQuestion);
    }

    private void initAllComponents() {
        setContentView(R.layout.activity_play_quiz);
        setTitle("Revision Classes");

        context = this;
        quizDataList = new ArrayList<>();
        quizQuestionStatusButtonList = new ArrayList<>();
        mode = Constant.MODE_PRACTICE;

        //mainQuizScrollView = findViewById(R.id.mainQuizScrollView);

        progressBar = findViewById(R.id.progressBar);

        quesNoTextView = findViewById(R.id.ques_no);
        timerTextView = findViewById(R.id.timer_text_view);
        quizQuestionTextView = findViewById(R.id.quiz_question);

        totalAttemptedTextView = findViewById(R.id.total_attmpted_text);
//        totalAttempted = 0;

        totalNotAttemptedTextView = findViewById(R.id.total_not_attmpted_text);

        totalCorrectAnsTextView = findViewById(R.id.total_correct_text);
//        totalCorrect = 0;

        totalIncorrectAnsTextView = findViewById(R.id.total_incorrect_text);
        correctIncorrectTextContainer = findViewById(R.id.correct_incorrect_text_container);

        quizQuestionListView = findViewById(R.id.quiz_question_list);
        previousButton = findViewById(R.id.button_previous);
        previousButton.setOnClickListener(onClickListener);

        nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(onClickListener);

        reviewCloseButton = findViewById(R.id.button_review_close);
        reviewCloseButton.setOnClickListener(onClickListener);

        navButtonContainer = findViewById(R.id.navigation_button_container);
        navButtonContainer.setVisibility(View.GONE);

        quizStartButton = findViewById(R.id.quiz_start_button);
        quizStartButton.setOnClickListener(onClickListener);


        scrollToTopButton = findViewById(R.id.button_scroll_to_top);

        gridLayoutScroller = findViewById(R.id.question_no_container_scrollbar);
        gridLayout = findViewById(R.id.jump_to_question_grid_layout_container);
    }
}