package com.example.telequiz.activities.creatorStudio;

import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.services.utilities.GoogleSheetJsonParser;

public class UploadQuestionActivity extends AppCompatActivity {

    GoogleSheetJsonParser googleSheetJsonParser;
    TextView linkKnowMore;
    EditText googleSheetIdText, googleWorksheetNameText;
    ClipboardManager clipboard;
    RadioGroup gooleWorksheetNameRadioGroup;
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
    }

    private void initAllComponents() {
        linkKnowMore = findViewById(R.id.link_know_more);
        googleSheetIdText = findViewById(R.id.input_google_spreadsheet_id);
        googleWorksheetNameText =findViewById(R.id.input_google_worksheet_name);
        gooleWorksheetNameRadioGroup = findViewById(R.id.google_sheet_name_radio_group);
    }
}
