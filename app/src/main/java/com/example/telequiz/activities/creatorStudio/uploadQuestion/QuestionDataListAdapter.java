package com.example.telequiz.activities.creatorStudio.uploadQuestion;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;

import java.util.List;

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
