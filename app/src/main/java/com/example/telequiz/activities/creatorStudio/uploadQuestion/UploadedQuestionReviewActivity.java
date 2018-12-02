package com.example.telequiz.activities.creatorStudio.uploadQuestion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

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
            String messagePartA = Constant.QUESTIOND_DATA_JSON_READING_ERROR_MESSSAGE_PART_A ;
            String messagePartB = Constant.QUESTIOND_DATA_JSON_READING_ERROR_MESSSAGE_PART_B ;
            String title = Constant.QUESTIOND_DATA_JSON_READING_ERROR_MESSSAGE_TITLE;

            ScrollView scrollView = new ScrollView(context);
            scrollView.setPadding(10,10, 10, 10);
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                scrollView.addView(layout);

                    final TextView errorMeassagePartATextView = new TextView(context);
                    errorMeassagePartATextView.setText(messagePartA);
                    errorMeassagePartATextView.setTextSize(15);
                    layout.addView(errorMeassagePartATextView);

                    final Button templateFileDownloadLink = new Button(context);
                    templateFileDownloadLink.setText("Google Sheet Template File");
                    templateFileDownloadLink.setTextColor(Color.parseColor("#5036f9"));
                    templateFileDownloadLink.setTextSize(15);
                    templateFileDownloadLink.setCompoundDrawablesWithIntrinsicBounds( R.drawable.icon_attachment_black,0, 0, 0);
                    templateFileDownloadLink.setAutoLinkMask(Linkify.WEB_URLS);
                    templateFileDownloadLink.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String navigateTo = "https://docs.google.com/spreadsheets/d/1o5TV-aWfpV2P4Dx8sDwa_wleyhIAt3UvbSMLvB2brI4/edit?usp=sharing";
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(navigateTo));
                            startActivity(browserIntent);
                        }
                    });
                    layout.addView(templateFileDownloadLink);

                    final TextView errorMeassagePartBTextView = new TextView(context);
                    errorMeassagePartBTextView.setText(messagePartB);
                    errorMeassagePartBTextView.setTextSize(15);
                    layout.addView(errorMeassagePartBTextView);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setIcon(R.drawable.icon_error);
            alertDialogBuilder.setView(scrollView);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(context, UploadQuestionActivity.class);
                    startActivity(intent);
                    dialog.cancel();
                }
            });

            alertDialogBuilder.show();
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
        context = this;
        session = new SessionManager(context);
    }
}


/*
 * *******************************************************************************
 * Adapter class
 * ********************************************************************************
 * */

class QuestionDataListAdapter extends ArrayAdapter<QuestionData> {

    //the list values in the List of type hero
    List<QuestionData> questionDataList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public QuestionDataListAdapter(Context context, int resource, List<QuestionData> questionDataList) {
        super(context, resource, questionDataList);
        this.context = context;
        this.resource = resource;
        this.questionDataList = questionDataList;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        TextView quesSerialNoTextView = view.findViewById(R.id.ques_sl_no);
        TextView questionTextView = view.findViewById(R.id.question);
        TextView optionAtextView = view.findViewById(R.id.option_a);
        TextView optionBtextView = view.findViewById(R.id.option_b);
        TextView optionCtextView = view.findViewById(R.id.option_c);
        TextView optionDtextView = view.findViewById(R.id.option_d);
        TextView correctOptionTextView = view.findViewById(R.id.correct_option);
        Button deleteButton = view.findViewById(R.id.button_delete);
        Button editButton = view.findViewById(R.id.button_edit);

        //getting the hero of the specified position
        QuestionData qData = questionDataList.get(position);

        //adding values to the list item
        quesSerialNoTextView.setText(qData.quesData.get(Constant.SL_NO));
        questionTextView.setText(qData.quesData.get(Constant.QUESTION));
        optionAtextView.setText(qData.quesData.get(Constant.OPTION_A));
        optionBtextView.setText(qData.quesData.get(Constant.OPTION_B));
        optionCtextView.setText(qData.quesData.get(Constant.OPTION_C));
        optionDtextView.setText(qData.quesData.get(Constant.OPTION_D));
        correctOptionTextView.setText(qData.quesData.get(Constant.CORRECT_OPTION));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editQuestion(position);
            }
        });

        //adding a click listener to the button to remove item from the list
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method
                removeQuestion(position);
            }
        });

        //finally returning the view
        return view;
    }

    private void editQuestion(final int position) {
        //getting the hero of the specified position
        final QuestionData qData = questionDataList.get(position);

        ScrollView scrollView = new ScrollView(context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(layout);
        // {
        LinearLayout title_layout = new LinearLayout(context);
        title_layout.setOrientation(LinearLayout.HORIZONTAL);
        title_layout.setBackgroundResource(R.drawable.background_login_activity_header);
        title_layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(title_layout);
        //{
        final ImageView titleIcon = new ImageView(context);
        titleIcon.setBackgroundResource(R.drawable.icon_edit);
        title_layout.addView(titleIcon);

        final TextView titleTextView = new TextView(context);
        titleTextView.setText("Edit Question");
        titleTextView.setTypeface(null, Typeface.BOLD);
        title_layout.addView(titleTextView);
        //}

        // Add a TextView here for the "Title" label, as noted in the comments
        final EditText questionEditBox = new EditText(context);
        questionEditBox.setHint("Type here your Question");
        questionEditBox.setText(qData.quesData.get(Constant.QUESTION));
        layout.addView(questionEditBox); // Notice this is an add method

        // Add another TextView here for the "Description" label
        final EditText optionAEnglishEditBox = new EditText(context);
        optionAEnglishEditBox.setHint("Type option: a");
        optionAEnglishEditBox.setText(qData.quesData.get(Constant.OPTION_A));
        layout.addView(optionAEnglishEditBox); // Another add method

        // Add a TextView here for the "Title" label, as noted in the comments
        final EditText optionBEnglishEditBox = new EditText(context);
        optionBEnglishEditBox.setHint("Type option: b");
        optionBEnglishEditBox.setText(qData.quesData.get(Constant.OPTION_B));
        layout.addView(optionBEnglishEditBox); // Notice this is an add method

        // Add another TextView here for the "Description" label
        final EditText optionCEnglishEditBox = new EditText(context);
        optionCEnglishEditBox.setHint("Type option: c");
        optionCEnglishEditBox.setText(qData.quesData.get(Constant.OPTION_C));
        layout.addView(optionCEnglishEditBox); // Another add method

        // Add another TextView here for the "Description" label
        final EditText optionDEnglishEditBox = new EditText(context);
        optionDEnglishEditBox.setHint("Type option: d");
        optionDEnglishEditBox.setText(qData.quesData.get(Constant.OPTION_D));
        layout.addView(optionDEnglishEditBox); // Another add method

        // Add another TextView here for the "Description" label
        final String[] correctOption = {qData.quesData.get(Constant.CORRECT_OPTION)};
        final TextView correctOptionLabel = new TextView(context);
        correctOptionLabel.setText("Correct Option: " + correctOption[0]);
        layout.addView(correctOptionLabel); // Another add method

        final RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);
        layout.addView(radioGroup); // Another add method
        //{
        //Option radio button : A
        final RadioButton optionARadioButton = new RadioButton(context);
        final RadioButton optionBRadioButton = new RadioButton(context);
        final RadioButton optionCRadioButton = new RadioButton(context);
        final RadioButton optionDRadioButton = new RadioButton(context);

        optionARadioButton.setText("A");
        optionARadioButton.setChecked(correctOption[0].equals("A"));
        radioGroup.addView(optionARadioButton);

        //Option radio button : B
        optionBRadioButton.setText("B");
        optionBRadioButton.setChecked(correctOption[0].equals("B"));
        radioGroup.addView(optionBRadioButton);

        //Option radio button : C
        optionCRadioButton.setText("C");
        optionCRadioButton.setChecked(correctOption[0].equals("C"));
        radioGroup.addView(optionCRadioButton);

        //Option radio button : D
        optionDRadioButton.setText("D");
        optionDRadioButton.setChecked(correctOption[0].equals("D"));
        radioGroup.addView(optionDRadioButton);

        optionARadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctOption[0] = "A";
                correctOptionLabel.setText("Correct Option: " + correctOption[0]);
                optionARadioButton.setChecked(true);
                optionBRadioButton.setChecked(false);
                optionCRadioButton.setChecked(false);
                optionDRadioButton.setChecked(false);
            }
        });

        optionBRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctOption[0] = "B";
                correctOptionLabel.setText("Correct Option: " + correctOption[0]);
                optionARadioButton.setChecked(false);
                optionBRadioButton.setChecked(true);
                optionCRadioButton.setChecked(false);
                optionDRadioButton.setChecked(false);
            }
        });

        optionCRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctOption[0] = "C";
                correctOptionLabel.setText("Correct Option: " + correctOption[0]);
                optionARadioButton.setChecked(false);
                optionBRadioButton.setChecked(false);
                optionCRadioButton.setChecked(true);
                optionDRadioButton.setChecked(false);
            }
        });

        optionDRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctOption[0] = "D";
                correctOptionLabel.setText("Correct Option: " + correctOption[0]);
                optionARadioButton.setChecked(false);
                optionBRadioButton.setChecked(false);
                optionCRadioButton.setChecked(false);
                optionDRadioButton.setChecked(true);
            }
        });


        //Make new Dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(scrollView); // Again this is a set method, not add
        dialog.setCancelable(false);
        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                HashMap<String, String> m_li = new HashMap<String, String>();
                m_li.put(Constant.SL_NO, qData.quesData.get(Constant.SL_NO));
                m_li.put(Constant.QUESTION, questionEditBox.getText().toString());
                m_li.put(Constant.OPTION_A, optionAEnglishEditBox.getText().toString());
                m_li.put(Constant.OPTION_B, optionBEnglishEditBox.getText().toString());
                m_li.put(Constant.OPTION_C, optionCEnglishEditBox.getText().toString());
                m_li.put(Constant.OPTION_D, optionDEnglishEditBox.getText().toString());
                m_li.put(Constant.CORRECT_OPTION, correctOption[0]);
                questionDataList.set(position, new QuestionData(m_li));

                //reloading the list
                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert = dialog.create();
        alert.show();
    }

    //this method will remove the item from the list
    private void removeQuestion(final int position) {
        //Creating an alert dialog to confirm the deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure want to delete this?");

        //if the response is positive in the alert
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                questionDataList.remove(position);

                //reloading the list
                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }
}

/*
 * *******************************************************************************
 * Model class
 * ********************************************************************************
 * */

class QuestionData {
    public HashMap<String, String> quesData;

    public QuestionData(HashMap<String, String> quesData) {
        this.quesData = quesData;
    }
}

