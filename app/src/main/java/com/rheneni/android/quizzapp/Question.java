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

    private void initializeOptions(){
        if(mOptionType != AnswerOption.OPTION_TYPE_FREE) {
            for (int i = 0; i < mOptionsNumber; i++) {
                String name = Utilities.getStringFromResourcesByName(
                        mContext, "question" + (mQuestionNumber) + "_option" + (i + 1));
                mAnswers.add(new AnswerOption(mQuestionNumber, name, i + 1, mOptionType));
            }
        } else {
            mAnswers.add(new AnswerOption(mQuestionNumber, "", 1, mOptionType));
        }
    }
}
