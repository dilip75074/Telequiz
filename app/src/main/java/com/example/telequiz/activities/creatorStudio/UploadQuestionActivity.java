package com.example.telequiz.activities.creatorStudio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;

public class UploadQuestionActivity extends AppCompatActivity {

    Context context;
    TextView linkKnowMore;
    EditText googleSheetIdText, googleWorksheetNameText;
    RadioGroup gooleWorksheetNameRadioGroup;
    RadioButton defaultWorksheetNameRadioButton, customeWorksheetNameRadioButton;
    Button loadQuestionButton, clearFieldButton;
    ProgressBar progressBar;
    Constant c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createor_studio_upload_question);
        setTitle("Upload Questions");
        initAllComponents();

        linkKnowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String navigateTo = "https://support.google.com/drive/answer/2494822";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(navigateTo));
                startActivity(browserIntent);
            }
        });

        gooleWorksheetNameRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.google_sheet_name_default_radio_button){
                    googleWorksheetNameText.setText("Sheet1");
                    googleWorksheetNameText.setEnabled(false);
                }

                if (checkedId == R.id.google_sheet_name_custom_radio_button){
                    googleWorksheetNameText.setText("");
                    googleWorksheetNameText.setEnabled(true);
                }
            }
        });

        loadQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidData()) {
                    loadQuestions();
                }
            }
        });

        clearFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSheetIdText.setText("");
                defaultWorksheetNameRadioButton.setChecked(false);
                customeWorksheetNameRadioButton.setChecked(true);
            }
        });
    }

    private void loadQuestions() {
        String scriptBaseUrl = Constant.GOOGLE_SCRIPT_BASE_URL;
        final String googleSheetId = googleSheetIdText.getText().toString();
        String googleSheetName =  googleWorksheetNameText.getText().toString();
        final String scriptURL = scriptBaseUrl + "id=" + googleSheetId + "&sheet=" + googleSheetName;

        enableUserInteraction(false);

        ConnectionManager.volleyStringRequest(context, true, progressBar, scriptURL, new VolleyResponse() {
            @Override
            public void onResponse(String _response) {
                enableUserInteraction(true);
                /**
                 * Handle Response
                 */
                try {
                    JSONArray questionDataArray =  new JSONObject(_response).optJSONArray("questions");
//                    TextView impNoteText= findViewById(R.id.important_note_text);
//                    impNoteText.setText(_response.toString());
                    sendQuestionDataForReview(_response);
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

    private void sendQuestionDataForReview(String response) throws JSONException {
        JSONObject obj = new JSONObject(response);
        JSONArray m_jArry = obj.getJSONArray("records");
        ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
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

            formList.add(m_li);
        }
        TextView impNoteText= findViewById(R.id.important_note_text);
        impNoteText.setText(formList.toString());
    }

    private void enableUserInteraction(boolean b) {
        if(b) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private boolean isValidData() {
        String googleSpreadsheetId = googleSheetIdText.getText().toString();
        String googleWorksheetName = googleWorksheetNameText.getText().toString();

        if(googleSpreadsheetId.isEmpty()) {
            googleSheetIdText.setError("Enter a valid Google spreadsheet id");
            return false;
        }
        else if(googleWorksheetName.isEmpty()) {
            googleWorksheetNameText.setError("Enter a valid Google worksheet name");
            return false;
        }
        else {
            googleWorksheetNameText.setError(null);
            googleSheetIdText.setError(null);
            return true;
        }
    }

    private void initAllComponents() {
        context = getApplicationContext();
        linkKnowMore = findViewById(R.id.link_know_more);
        googleSheetIdText = findViewById(R.id.input_google_spreadsheet_id);
        googleWorksheetNameText =findViewById(R.id.input_google_worksheet_name);
        gooleWorksheetNameRadioGroup = findViewById(R.id.google_sheet_name_radio_group);
        loadQuestionButton = findViewById(R.id.btn_load_question);
        clearFieldButton = findViewById(R.id.btn_field_clear);
        progressBar = findViewById(R.id.progressBar);
        customeWorksheetNameRadioButton = findViewById(R.id.google_sheet_name_custom_radio_button);
        defaultWorksheetNameRadioButton = findViewById(R.id.google_sheet_name_default_radio_button);
    }
}
