package com.example.telequiz.activities.creatorStudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadedQuestionReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_studio_uploaded_question_review);

        Bundle bundle = getIntent().getExtras();
        String uploadedQuestions = bundle.getString(Constant.UPLOADED_QUESTIONS);
        Log.i("All questions:-", uploadedQuestions );
        try {
            setCustomListViewAdapter(uploadedQuestions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCustomListViewAdapter(String uploadedQuestions) throws JSONException {
        JSONObject obj = new JSONObject(uploadedQuestions);
        JSONArray m_jArry = obj.getJSONArray("records");

        List<QuestionData> questionDataList = new ArrayList<>();
        ListView questionListView = findViewById(R.id.question_list_view);
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
        QuestionDataListAdapter adapter = new QuestionDataListAdapter(this, R.layout.question_list, questionDataList);

        //attaching adapter to the listview
        questionListView.setAdapter(adapter);
    }
}
