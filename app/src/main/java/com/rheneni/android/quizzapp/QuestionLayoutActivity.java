package com.rheneni.android.quizzapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 18/01/2018.
 */

public class QuestionLayoutActivity extends AppCompatActivity {

    static final int QUESTIONS_NUMBER = 10;
    private ArrayList<ArrayList<Integer>> mSolutions = new ArrayList<>();
    private ArrayList<Integer> mOptionTypes = new ArrayList<>();
    private ArrayList<Integer> mQuestionNumOptions = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ArrayList<Question> mQuestions = new ArrayList<>();
    //private QuestionAdapter mAdapter;
    private int questionsCorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillLists();
        for(int i = 0; i < QUESTIONS_NUMBER; i++) {
            mQuestions.add(new Question(this, i+1, mOptionTypes.get(i), mQuestionNumOptions.get(i)));
        }
    }

    private void fillLists() {
        for(int i = 0; i < QUESTIONS_NUMBER; i++) {
            ArrayList<Integer> solution = new ArrayList<>();
            //Should change to Enums/constants
            switch (i+1){
                case 1:
                    solution.add(4);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    mQuestionNumOptions.add(5);
                    break;
                case 2:
                    solution.add(3);
                    solution.add(5);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_CHECKBOX);
                    mQuestionNumOptions.add(5);
                    break;
                case 3:
                    solution.add(3);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    mQuestionNumOptions.add(5);
                    break;
                case 4:
                    solution.add(4);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    mQuestionNumOptions.add(5);
                    break;
                case 5:
                    solution.add(5);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    mQuestionNumOptions.add(5);
                    break;
                case 6:
                    solution.add(1);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    mQuestionNumOptions.add(5);
                    break;
                case 7:
                    solution.add(5);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    mQuestionNumOptions.add(5);
                    break;
                case 8:
                    solution.add(1);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    mQuestionNumOptions.add(5);
                    break;
                case 9:
                    solution.add(4);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    mQuestionNumOptions.add(5);
                    break;
                case 10:
                    solution.add(1);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_FREE);
                    mQuestionNumOptions.add(0);
                    break;
                default:
                    break;
            }
            mSolutions.add(solution);
        }
    }
}
