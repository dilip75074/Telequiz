package com.example.telequiz.activities.creatorStudio.uploadQuestion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.telequiz.R;
import com.example.telequiz.services.SessionManager;
import com.example.telequiz.services.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadedQuestionReviewActivity extends AppCompatActivity {

    SessionManager session;
    Context context;

    ImageButton scrollToTopButton;
    ListView questionListView;
    QuestionDataListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_studio_uploaded_question_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Question Review");
        initAllComponents();

        scrollToTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionListView.setSelection(3);
                questionListView.smoothScrollToPosition(0);
            }
        });

        questionListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem  > 2) {
                    scrollToTopButton.setVisibility(View.VISIBLE);
                }
                else {
                    scrollToTopButton.setVisibility(View.GONE);
                }
                if(mLastFirstVisibleItem<firstVisibleItem) {
                    Log.i("DK SCROLLING DOWN","TRUE");
                }
                if(mLastFirstVisibleItem>firstVisibleItem) {
                    Log.i("DK SCROLLING UP","TRUE");
                }
                mLastFirstVisibleItem=firstVisibleItem;
            }
        });

        Bundle bundle = getIntent().getExtras();
        String uploadedQuestions = bundle.getString(Constant.UPLOADED_QUESTIONS);
        try {
            setCustomListViewAdapter(uploadedQuestions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure want to discard this?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, UploadQuestionActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void setCustomListViewAdapter(String uploadedQuestions) throws JSONException {
        JSONObject obj = new JSONObject(uploadedQuestions);
        JSONArray m_jArry = obj.getJSONArray("records");

        List<QuestionData> questionDataList = new ArrayList<>();
        HashMap<String, String> m_li;

        for (int i = 0; i < m_jArry.length(); i++) {
            JSONObject jo_inside = m_jArry.getJSONObject(i);
            String sNo = !jo_inside.getString(Constant.SL_NO).isEmpty() ? jo_inside.getString(Constant.SL_NO) : null;
            String questionEnglish = !jo_inside.getString(Constant.QUESTION_ENGLISH).isEmpty() ? jo_inside.getString(Constant.QUESTION_ENGLISH) : null;
            String questionHindi = !jo_inside.getString(Constant.QUESTION_HINDI).isEmpty() ? jo_inside.getString(Constant.QUESTION_HINDI) : null;
            String optionAEnglish = !jo_inside.getString(Constant.OPTION_A_ENGLISH).isEmpty() ? jo_inside.getString(Constant.OPTION_A_ENGLISH) : null;
            String optionAHindi = !jo_inside.getString(Constant.OPTION_A_HINDI).isEmpty() ? jo_inside.getString(Constant.OPTION_A_HINDI) : null;
            String optionBEnglish = !jo_inside.getString(Constant.OPTION_B_ENGLISH).isEmpty() ? jo_inside.getString(Constant.OPTION_B_ENGLISH) : null;
            String optionBHindi = !jo_inside.getString(Constant.OPTION_B_HINDI).isEmpty() ? jo_inside.getString(Constant.OPTION_B_HINDI) : null;
            String optionCEnglish = !jo_inside.getString(Constant.OPTION_C_ENGLISH).isEmpty() ? jo_inside.getString(Constant.OPTION_C_ENGLISH) : null;
            String optionCHindi = !jo_inside.getString(Constant.OPTION_C_HINDI).isEmpty() ? jo_inside.getString(Constant.OPTION_C_HINDI) : null;
            String optionDEnglish = !jo_inside.getString(Constant.OPTION_D_ENGLISH).isEmpty() ? jo_inside.getString(Constant.OPTION_D_ENGLISH) : null;
            String optionDHindi = !jo_inside.getString(Constant.OPTION_D_HINDI).isEmpty() ? jo_inside.getString(Constant.OPTION_D_HINDI) : null;
            String correctOption = !jo_inside.getString(Constant.CORRECT_OPTION).isEmpty() ? jo_inside.getString(Constant.CORRECT_OPTION) : null;

            //Add your values in your `ArrayList` as below:
            m_li = new HashMap<String, String>();
            m_li.put(Constant.SL_NO, sNo);
            m_li.put(Constant.QUESTION_ENGLISH, questionEnglish);
            m_li.put(Constant.QUESTION_HINDI, questionHindi);
            m_li.put(Constant.OPTION_A_ENGLISH, optionAEnglish);
            m_li.put(Constant.OPTION_A_HINDI, optionAHindi);
            m_li.put(Constant.OPTION_B_ENGLISH, optionBEnglish);
            m_li.put(Constant.OPTION_B_HINDI, optionBHindi);
            m_li.put(Constant.OPTION_C_ENGLISH, optionCEnglish);
            m_li.put(Constant.OPTION_C_HINDI, optionCHindi);
            m_li.put(Constant.OPTION_D_ENGLISH, optionDEnglish);
            m_li.put(Constant.OPTION_D_HINDI, optionDHindi);
            m_li.put(Constant.CORRECT_OPTION, correctOption);

            questionDataList.add(new QuestionData(m_li));
        }

        //creating the adapter
       adapter = new QuestionDataListAdapter(this, R.layout.activity_creator_studio_uploaded_question_list, questionDataList);

        //attaching adapter to the listview
        questionListView.setAdapter(adapter);
    }

    private void initAllComponents() {
        questionListView = findViewById(R.id.question_list_view);
        scrollToTopButton = findViewById(R.id.button_scroll_to_top);
        context = getApplicationContext();
        session = new SessionManager(context);
    }
}
