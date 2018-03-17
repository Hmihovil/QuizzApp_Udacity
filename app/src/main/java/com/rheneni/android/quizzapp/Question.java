package com.rheneni.android.quizzapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Lenovo on 18/01/2018.
 */

public class Question {

    private int mQuestionNumber = 0;
    private String mQuestionName = "";
    private ArrayList<AnswerOption> mAnswers = new ArrayList<>();
    private @AnswerOption.OptionType int mOptionType;
    private int optionsNumber = 5;
    private Context mContext;

    public Question(Context context, int number, int optionType, int numOptions) {
        mOptionType = optionType;
        mQuestionNumber = number;
        optionsNumber = numOptions;
        mContext = context;
        mQuestionName = Utilities.getStringFromResourcesByName(mContext,"question" + (number));
        initializeOptions();
    }

    private void initializeOptions(){
        for(int i = 0; i < optionsNumber; i++){
            String name = Utilities.getStringFromResourcesByName(
                    mContext, "question" +  mQuestionNumber + "_option" + (i + 1));
            mAnswers.add(new AnswerOption(mQuestionNumber, name, i + 1, mOptionType));
        }
    }
}
