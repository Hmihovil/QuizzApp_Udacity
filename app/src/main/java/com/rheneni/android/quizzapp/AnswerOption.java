package com.rheneni.android.quizzapp;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AnswerOption {
    private int mAnswerNumber = 0;
    private String mOptionText = "";
    private int mQuestionNumber = 0;
    private boolean isChecked = false;
    private String freeAnswer = "";
    private @OptionType int mOptionType = OPTION_TYPE_RADIO;

    public AnswerOption(int questionNumber, String text, int answerNumber, @OptionType int optionType) {
        mOptionType = optionType;
        if(mOptionType != AnswerOption.OPTION_TYPE_FREE) {
            mQuestionNumber = questionNumber;
            mOptionText = text;
            mAnswerNumber = answerNumber;
        }
    }

    public int getOrderNumber() {
        return mAnswerNumber;
    }

    public String getOptionText() {
        return mOptionText;
    }

    public int getQuestionNumber() {
        return mQuestionNumber;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    public boolean getIsChecked() {
        return isChecked;
    }

    public void setFreeAnswer(String answer) {
        this.freeAnswer = answer;
    }
    public String getFreeAnswer(){
        return freeAnswer;
    }

    public @OptionType int getOptionType(){
        return mOptionType;
    }
    public static final int OPTION_TYPE_RADIO = 0;
    public static final int OPTION_TYPE_CHECKBOX = 1;
    public static final int OPTION_TYPE_FREE = 2;

    @IntDef({OPTION_TYPE_RADIO, OPTION_TYPE_CHECKBOX, OPTION_TYPE_FREE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OptionType{}

}

