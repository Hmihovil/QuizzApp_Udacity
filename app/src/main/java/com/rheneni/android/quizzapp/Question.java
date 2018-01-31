package com.rheneni.android.quizzapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 18/01/2018.
 */

public class Question {

    static final int OPTIONS_NUMBER = 5;

    private int mNumber = 0;
    private String mQuestionName = "";
    private List<AnswerOption> mListAnswers = new ArrayList<>();


    public Question(String name, int number) {
        mQuestionName = name;
        mNumber = number;
        initializeAnswersList();
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setQuestionName(String name) {
        mQuestionName = name;
    }

    public String getQuestionName() {
        return mQuestionName;
    }

    public void setAnswerOptions(List<AnswerOption> list) {
        mListAnswers = list;
    }
    public List<AnswerOption> getAnswerOptions() {
        return mListAnswers;
    }

    private void initializeAnswersList() {
        for(int i = 0; i < OPTIONS_NUMBER; i++) {
            mListAnswers.add(new AnswerOption(mNumber, "Answer option", i+1, AnswerOption.OPTION_TYPE_RADIO));
        }
    }
}
