package com.rheneni.android.quizzapp;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Lenovo on 22/01/2018.
 */

public class AnswerOption {
    private int mOrderNum = 0;
    private String mOptionText = "";
    private int mQuestionNumber = 0;
    private @OptionType int mOptionType = OPTION_TYPE_RADIO;

    public AnswerOption(int questionNumber, String text, int number, @OptionType int optionTypes) {
        mQuestionNumber = questionNumber;
        mOptionText = text;
        mOrderNum = number;
        mOptionType = optionTypes;
    }

    public void setOrderNumber(int number) {
        mOrderNum = number;
    }

    public int getOrderNumber() {
        return mOrderNum;
    }

    public void setOptionText(String text) {
        mOptionText = text;
    }

    public String getOptionText() {
        return mOptionText;
    }

    public void setQuestionNumber(int number) {
        mQuestionNumber = number;
    }

    public int getQuestionNumber() {
        return mQuestionNumber;
    }
    public static final int OPTION_TYPE_RADIO = 0;
    public static final int OPTION_TYPE_CHECKBOX = 1;

    @IntDef({OPTION_TYPE_RADIO, OPTION_TYPE_CHECKBOX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OptionType{}

}

