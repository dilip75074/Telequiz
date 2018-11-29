package com.example.telequiz.activities.creatorStudio.uploadQuestion;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import static com.example.telequiz.R.drawable.icon_edit;

public class QuestionDataListAdapter extends ArrayAdapter<QuestionData> {

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
        TextView quesEnglishTextView = view.findViewById(R.id.ques_english);
        TextView optionAEnglishTextView = view.findViewById(R.id.option_a_english);
        TextView optionBEnglishTextView = view.findViewById(R.id.option_b_english);
        TextView optionCEnglishTextView = view.findViewById(R.id.option_c_english);
        TextView optionDEnglishTextView = view.findViewById(R.id.option_d_english);
        TextView correctOptionTextView = view.findViewById(R.id.correct_option);
        Button deleteButton = view.findViewById(R.id.button_delete);
        Button editButton = view.findViewById(R.id.button_edit);

        //getting the hero of the specified position
        QuestionData qData = questionDataList.get(position);

        //adding values to the list item
        quesSerialNoTextView.setText(qData.quesData.get(Constant.SL_NO));
        quesEnglishTextView.setText(qData.quesData.get(Constant.QUESTION_ENGLISH));
        optionAEnglishTextView.setText(qData.quesData.get(Constant.OPTION_A_ENGLISH));
        optionBEnglishTextView.setText(qData.quesData.get(Constant.OPTION_B_ENGLISH));
        optionCEnglishTextView.setText(qData.quesData.get(Constant.OPTION_C_ENGLISH));
        optionDEnglishTextView.setText(qData.quesData.get(Constant.OPTION_D_ENGLISH));
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
                questionEditBox.setText(qData.quesData.get(Constant.QUESTION_ENGLISH));
                layout.addView(questionEditBox); // Notice this is an add method

                // Add another TextView here for the "Description" label
                final EditText optionAEnglishEditBox = new EditText(context);
                optionAEnglishEditBox.setHint("Type option: a");
                optionAEnglishEditBox.setText(qData.quesData.get(Constant.OPTION_A_ENGLISH));
                layout.addView(optionAEnglishEditBox); // Another add method

                // Add a TextView here for the "Title" label, as noted in the comments
                final EditText optionBEnglishEditBox = new EditText(context);
                optionBEnglishEditBox.setHint("Type option: b");
                optionBEnglishEditBox.setText(qData.quesData.get(Constant.OPTION_B_ENGLISH));
                layout.addView(optionBEnglishEditBox); // Notice this is an add method

                // Add another TextView here for the "Description" label
                final EditText optionCEnglishEditBox = new EditText(context);
                optionCEnglishEditBox.setHint("Type option: c");
                optionCEnglishEditBox.setText(qData.quesData.get(Constant.OPTION_C_ENGLISH));
                layout.addView(optionCEnglishEditBox); // Another add method

                // Add another TextView here for the "Description" label
                final EditText optionDEnglishEditBox = new EditText(context);
                optionDEnglishEditBox.setHint("Type option: d");
                optionDEnglishEditBox.setText(qData.quesData.get(Constant.OPTION_D_ENGLISH));
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

               // }
            //}
        //}

        //Make new Dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(scrollView); // Again this is a set method, not add
        dialog.setCancelable(false);
        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                questionDataList.remove(position);
                HashMap<String, String> m_li = new HashMap<String, String>();
                m_li.put(Constant.SL_NO, qData.quesData.get(Constant.SL_NO));
                m_li.put(Constant.QUESTION_ENGLISH, questionEditBox.getText().toString());
                m_li.put(Constant.OPTION_A_ENGLISH, optionAEnglishEditBox.getText().toString());
                m_li.put(Constant.OPTION_B_ENGLISH, optionBEnglishEditBox.getText().toString());
                m_li.put(Constant.OPTION_C_ENGLISH, optionCEnglishEditBox.getText().toString());
                m_li.put(Constant.OPTION_D_ENGLISH, optionDEnglishEditBox.getText().toString());
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

        dialog.show();
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
