package com.example.telequiz.activities.quiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.services.utilities.Constant;
import com.example.telequiz.services.utilities.SoundManager;

import java.util.HashMap;
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

    TextView quizQuestionTextView, quesNoOnReviewModeTextView;

    LinearLayout commentLinearLayout, quizContainerLayout;

    CheckBox optionCheckBox[] = new CheckBox[4];

    ImageButton likeButton, unlikeButton, shareButton, commentButton;

    boolean isLikeButtonPressed, isUnlikeButtonPressed;

    TextView totalViews, totalLikes, totalShares, totalComments;

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

    private int getPosition() {
        return PlayQuizActivity.quesNo - 1;
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
        quesNoOnReviewModeTextView.setText("Ques: " + String.valueOf(position + 1));
        quizQuestionTextView.setText(qData.quesData.get(Constant.QUESTION));
        optionCheckBox[0].setText(qData.quesData.get(Constant.OPTION_A));
        optionCheckBox[1].setText(qData.quesData.get(Constant.OPTION_B));
        optionCheckBox[2].setText(qData.quesData.get(Constant.OPTION_C));
        optionCheckBox[3].setText(qData.quesData.get(Constant.OPTION_D));

        String userOption = qData.quesData.get(Constant.USER_OPTION);
        boolean isReviewMode = PlayQuizActivity.mode.toLowerCase().equals(Constant.MODE_REVIEW);
        if ((userOption == null || userOption.isEmpty()) && isReviewMode) {
            userOption = "E";
        }
        setOptionCheckBoxChecked(userOption, position);

        totalViews.setText(qData.quesData.get(Constant.TOTAL_VIEWS));
        totalLikes.setText(qData.quesData.get(Constant.TOTAL_LIKES));
        totalShares.setText(qData.quesData.get(Constant.TOTAL_SHARES));
        totalComments.setText(qData.quesData.get(Constant.TOTAL_COMMENTS));

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

                case R.id.share_button:
                    onShareButtonClick();
                    break;

                case R.id.comment_button:
                    onCommentButtonClick();
                    break;
                /*
                 * Action for Option radio button click
                 * */
                case R.id.option_check_box_a:
                    onOptionAClick();
                    break;
                case R.id.option_check_box_b:
                    onOptionBClick();
                    break;
                case R.id.option_check_box_c:
                    onOptionCClick();
                    break;
                case R.id.option_check_box_d:
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
                if(optionCheckBox[i].isChecked())
                    saveUserAnswer("A");
                else
                    saveUserAnswer(null);
            }
            else {
                optionCheckBox[i].setChecked(false);
            }
        }
    }

    private void onOptionBClick() {

        for (int i = 0; i<4; i++ ) {
            if(i == 1) {
                if(optionCheckBox[i].isChecked())
                    saveUserAnswer("B");
                else
                    saveUserAnswer(null);
            }
            else {
                optionCheckBox[i].setChecked(false);
            }
        }
    }

    private void onOptionCClick() {
        for (int i = 0; i<4; i++ ) {
            if(i == 2) {
                if(optionCheckBox[i].isChecked())
                    saveUserAnswer("C");
                else
                    saveUserAnswer(null);
            }
            else {
                optionCheckBox[i].setChecked(false);
            }
        }
    }

    private void onOptionDClick() {
        for (int i = 0; i<4; i++ ) {
            if(i == 3) {
                if(optionCheckBox[i].isChecked())
                    saveUserAnswer("D");
                else
                    saveUserAnswer(null);
            }
            else {
                optionCheckBox[i].setChecked(false);
            }
        }
    }

    private void setOptionCheckBoxChecked(String userOption, int position) {
        boolean isPracticeMode = PlayQuizActivity.mode.toLowerCase().equals(Constant.MODE_PRACTICE);
        boolean isReviewMode = PlayQuizActivity.mode.toLowerCase().equals(Constant.MODE_REVIEW);
        if (userOption == null || userOption.isEmpty())
            return;

        switch (userOption) {
            case "A":
                optionCheckBox[0].setChecked(true);
                break;
            case "B":
                optionCheckBox[1].setChecked(true);
                break;
            case "C":
                optionCheckBox[2].setChecked(true);
                break;
            case "D":
                optionCheckBox[3].setChecked(true);
                break;
        }

        if(isPracticeMode || isReviewMode) {
            if(!isReviewMode)
                position = getPosition();
            showCorrectAnswer(userOption, true, position);
        }

    }

    private void saveUserAnswer(String userOption) {
        int position = getPosition();
        // Updating quiz data value in the list
        final QuizDataModel qData = PlayQuizActivity.quizDataList.get(position);
        HashMap<String, String> m_li = qData.quesData;
        m_li.put(Constant.USER_OPTION, userOption);
        PlayQuizActivity.quizDataList.set(position, new QuizDataModel(m_li));

        if(PlayQuizActivity.mode.toLowerCase().equals(Constant.MODE_PRACTICE)) {
            showCorrectAnswer(userOption, false, position);
        }
    }

    private void showCorrectAnswer(String userOption, boolean isAlreadyShown, int position) {
        int rightAnsBackground = R.drawable.background_style_right_ans;
        int wrongAnsBackground = R.drawable.background_style_wrong_ans;

        String correctOption = PlayQuizActivity.quizDataList.get(position).quesData.get(Constant.CORRECT_OPTION);
        correctOption = correctOption.toUpperCase();
        userOption = userOption.toUpperCase();

        int correctOptionValue = (int) correctOption.charAt(0) - 65;
        int userOptionValue = (int) userOption.charAt(0) - 65;

        PlayQuizActivity.quesNoTextView.setText(String.valueOf(position + 1) + "/" + String.valueOf(PlayQuizActivity.totalQuestion));
        if (userOptionValue == 4) {
            optionCheckBox[correctOptionValue].setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.icon_success, 0);
        }
        else if (userOptionValue == correctOptionValue) {
            optionCheckBox[correctOptionValue].setBackgroundResource(rightAnsBackground);
            if (!isAlreadyShown) {
                SoundManager sm = new SoundManager(context, R.raw.correct_answer);
                sm.playSound();
            }
        }
        else {
            optionCheckBox[correctOptionValue].setBackgroundResource(rightAnsBackground);
            optionCheckBox[userOptionValue].setBackgroundResource(wrongAnsBackground);
            if (!isAlreadyShown) {
                SoundManager sm = new SoundManager(context, R.raw.incorrect_answer);
                sm.playSound();
            }
        }

        for (int i = 0; i<4; i++ ) {
            optionCheckBox[i].setEnabled(false);
        }
    }

    private void animateLeftToRightEntry(final View childView, View containerView) {
//        final View childView = findViewById(R.id.quiz_question);
//        View containerView = findViewById(R.id.quiz_container);
        Log.i("DK Prev: ", "Prev animation");
        childView.setTranslationX(-containerView.getWidth());
        long TRANSLATION_DURATION = 1000;
        childView.animate()
                .translationXBy(containerView.getWidth())
                .setDuration(TRANSLATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        childView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void animateRightToLeftEntry(final View childView, View containerView) {
        Log.i("DK Next: ", "Next animation");
        childView.setTranslationX(containerView.getWidth());
        long TRANSLATION_DURATION = 1000;
        childView.animate()
                .translationXBy(-containerView.getWidth())
                .setDuration(TRANSLATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        childView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void animateRightToLeftExit(final View childView, View containerView) {
        long TRANSLATION_DURATION = 1000;
        childView.animate()
                .translationXBy(-containerView.getWidth())
                .setDuration(TRANSLATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        childView.setVisibility(View.GONE);
                    }
                });
    }

    private void initAllComponents(View view) {

        quizContainerLayout = view.findViewById(R.id.quiz_container);

        progressBar = view.findViewById(R.id.progressBar);

        quesNoOnReviewModeTextView = view.findViewById(R.id.ques_no_on_review_text);
        if(PlayQuizActivity.mode.toLowerCase().equals(Constant.MODE_REVIEW)) {
            quesNoOnReviewModeTextView.setVisibility(View.VISIBLE);
        }
        else {
            quesNoOnReviewModeTextView.setVisibility(View.GONE);
        }
        quizQuestionTextView = view.findViewById(R.id.quiz_question);

        commentLinearLayout = view.findViewById(R.id.comment_linear_layout);
        commentLinearLayout.setVisibility(View.GONE);

        optionCheckBox[0] = view.findViewById(R.id.option_check_box_a);
        optionCheckBox[1] = view.findViewById(R.id.option_check_box_b);
        optionCheckBox[2] = view.findViewById(R.id.option_check_box_c);
        optionCheckBox[3] = view.findViewById(R.id.option_check_box_d);
        for (int i = 0; i<4; i++) {
            optionCheckBox[i].setChecked(false);
            optionCheckBox[i].setOnClickListener(onClickListener);
        }

        totalViews = view.findViewById(R.id.total_views_text);

        likeButton = view.findViewById(R.id.like_button);
        likeButton.setOnClickListener(onClickListener);
        isLikeButtonPressed = false;
        totalLikes = view.findViewById(R.id.total_likes_text);

        shareButton = view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(onClickListener);
        totalShares = view.findViewById(R.id.total_shares_text);

        commentButton = view.findViewById(R.id.comment_button);
        commentButton.setOnClickListener(onClickListener);
        totalComments = view.findViewById(R.id.total_comments_text);
    }
}
