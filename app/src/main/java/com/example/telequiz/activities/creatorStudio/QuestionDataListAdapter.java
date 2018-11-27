package com.example.telequiz.activities.creatorStudio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;

import java.util.List;

public class QuestionDataListAdapter extends ArrayAdapter<QuestionData> {

    //the list values in the List of type hero
    List<QuestionData> heroList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public QuestionDataListAdapter(Context context, int resource, List<QuestionData> heroList) {
        super(context, resource, heroList);
        this.context = context;
        this.resource = resource;
        this.heroList = heroList;
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

        //getting the view elements of the list from the view
        TextView textViewName = view.findViewById(R.id.ques_sl_no);
        TextView textViewTeam = view.findViewById(R.id.ques_english);

        //getting the hero of the specified position
        QuestionData qData = heroList.get(position);

        //adding values to the list item
        textViewName.setText(qData.quesData.get(Constant.SL_NO));
        textViewTeam.setText(qData.quesData.get(Constant.QUESTION_ENGLISH));

        //finally returning the view
        return view;
    }
}
