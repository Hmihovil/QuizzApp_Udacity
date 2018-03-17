package com.rheneni.android.quizzapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Lenovo on 18/01/2018.
 */

public class Question {

    private int mQuestionNumber = 0;
    private String mQuestionName = "";
    private String mQuestionText = "";
    private ArrayList<AnswerOption> mAnswers = new ArrayList<>();
    private @AnswerOption.OptionType int mOptionType;
    private int mOptionsNumber = 5;
    private ArrayList<Boolean> mSelectedPositions = new ArrayList<>();
    private int mLastSelectedOption = -1;
    private Context mContext;

    public Question(Context context, int questionNum, int optionType, int numOptions) {
        mOptionType = optionType;
        mQuestionNumber = questionNum;
        mOptionsNumber = numOptions;
        mContext = context;
        mQuestionName = mContext.getResources().getString(R.string.question) + mQuestionNumber;
        mQuestionText = Utilities.getStringFromResourcesByName(mContext,"question" + (mQuestionNumber));
        initializeOptions();
    }

    public ArrayList<AnswerOption> getAnswerOptions() {
        return mAnswers;
    }
    public String getQuestionName() {
        return mQuestionName;
    }
    public String getQuestionText() {
        return mQuestionText;
    }
    public @AnswerOption.OptionType int getOptionType() { return mOptionType; }
    public int getOptionsNumber() { return mOptionsNumber; }
    public void setSelectedPositionCheck(int position, boolean value) {
        mAnswers.get(position).setIsChecked(value);
        mSelectedPositions.set(position, value);
    }
    public boolean getSelectedPositionCheck(int position) {
        return mSelectedPositions.get(position);
    }
    public void setLastSelectedOption(int position) {
        mLastSelectedOption = position;
    }

    public int getLastSelectedOption() {
        return mLastSelectedOption;
    }

    private void initializeOptions(){
        if(mOptionType != AnswerOption.OPTION_TYPE_FREE) {
            for (int i = 0; i < mOptionsNumber; i++) {
                mSelectedPositions.add(false);
                String name = Utilities.getStringFromResourcesByName(
                        mContext, "question" + (mQuestionNumber) + "_option" + (i + 1));
                mAnswers.add(new AnswerOption(mQuestionNumber, name, i + 1, mOptionType));
            }
        } else {
            mAnswers.add(new AnswerOption(mQuestionNumber, "", 1, mOptionType));
        }
    }
}
