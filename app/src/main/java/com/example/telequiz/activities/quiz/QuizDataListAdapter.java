package com.example.telequiz.activities.quiz;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;

import java.util.List;

public class QuizDataListAdapter extends ArrayAdapter<QuizDataModel> {

    //the list values in the List of type hero
    List<QuizDataModel> quizDataList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    ProgressBar progressBar;

    ScrollView mainQuizScrollView;
    CountDownTimer countDownTimer = null;

    Button nextButton, previousButton;

    TextView timerTextView, quizQuestionTextView, quesNoTextView;

    LinearLayout commentLinearLayout, quizContainerLayout;

    RadioButton optionRadioButton[] = new RadioButton[4];

    ImageButton likeButton, unlikeButton, shareButton, commentButton;

    boolean isLikeButtonPressed, isUnlikeButtonPressed;

    //constructor initializing the values
    public QuizDataListAdapter(Context context, int resource, List<QuizDataModel> quizDataList) {
        super(context, resource, quizDataList);
        this.context = context;
        this.resource = resource;
        this.quizDataList = quizDataList;
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

        initAllComponents(view);
        //getting the hero of the specified position
        QuizDataModel qData = quizDataList.get(position);

        //adding values to the list item
        quizQuestionTextView.setText(qData.quesData.get(Constant.QUESTION));
        optionRadioButton[0].setText(qData.quesData.get(Constant.OPTION_A));
        optionRadioButton[1].setText(qData.quesData.get(Constant.OPTION_B));
        optionRadioButton[2].setText(qData.quesData.get(Constant.OPTION_C));
        optionRadioButton[3].setText(qData.quesData.get(Constant.OPTION_D));

        //finally returning the view
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
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
            }
        }
    };

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
    private void initAllComponents(View view) {

        mainQuizScrollView = view.findViewById(R.id.mainQuizScrollView);

        quizContainerLayout = view.findViewById(R.id.quiz_container);

        progressBar = view.findViewById(R.id.progressBar);

        quesNoTextView = view.findViewById(R.id.ques_no);
        timerTextView = view.findViewById(R.id.timer_text_view);
        quizQuestionTextView = view.findViewById(R.id.quiz_question);

        commentLinearLayout = view.findViewById(R.id.comment_linear_layout);
        commentLinearLayout.setVisibility(View.GONE);

        likeButton = view.findViewById(R.id.like_button);
        likeButton.setOnClickListener(onClickListener);
        isLikeButtonPressed = false;

        unlikeButton = view.findViewById(R.id.unlike_button);
        unlikeButton.setOnClickListener(onClickListener);
        isUnlikeButtonPressed = false;

        shareButton = view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(onClickListener);

        commentButton = view.findViewById(R.id.comment_button);
        commentButton.setOnClickListener(onClickListener);

        optionRadioButton[0] = view.findViewById(R.id.radio_button_a);
        optionRadioButton[1] = view.findViewById(R.id.radio_button_b);
        optionRadioButton[2] = view.findViewById(R.id.radio_button_c);
        optionRadioButton[3] = view.findViewById(R.id.radio_button_d);
        for (int i = 0; i<4; i++) {
            optionRadioButton[i].setOnClickListener(onClickListener);
        }
    }
}
