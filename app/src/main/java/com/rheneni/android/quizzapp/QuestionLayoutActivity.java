package com.rheneni.android.quizzapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    private QuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillLists();
        for(int i = 0; i < QUESTIONS_NUMBER; i++) {
            mQuestions.add(new Question(this, i+1, mOptionTypes.get(i), mQuestionNumOptions.get(i)));
        }

        setupRecycler();

    }

    private void setupRecycler() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_layout_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new QuestionAdapter(this, mQuestions);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                    mQuestionNumOptions.add(1);
                    break;
                default:
                    break;
            }
            mSolutions.add(solution);
        }
    }

    public void showResults(View view) {
        if(mQuestions.get(9).getAnswerOptions().get(0).getFreeAnswer() == "") {
            Toast.makeText(this,
                    "You have to write a number on question " +
                            QUESTIONS_NUMBER + "!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            int questionsCorrect = 0;
            for (int i = 0; i < QUESTIONS_NUMBER; i++) {
                List<AnswerOption> listAnswers = mQuestions.get(i).getAnswerOptions();
                List<Integer> solution = mSolutions.get(i);
                if (mOptionTypes.get(i) == AnswerOption.OPTION_TYPE_FREE) {
                    if (Integer.parseInt(listAnswers.get(0).getFreeAnswer()) == questionsCorrect + 1 &&
                            i + 1 == QUESTIONS_NUMBER) {
                        questionsCorrect++;
                    }
                } else {
                    boolean isCorrect = true;
                    for (int j = 0; j < listAnswers.size(); j++) {
                        //if is checked and is expected to be checked - solution matrix
                        if (listAnswers.get(j).getIsChecked() && solution.contains(j + 1)) {
                            continue;
                        } else if (!listAnswers.get(j).getIsChecked() && !solution.contains(j + 1)) {
                            continue;
                        } else {
                            isCorrect = false;
                            break;
                        }
                    }
                    if (isCorrect) {
                        questionsCorrect++;
                    }

                }
            }
            String questionPlural = " question";
            if(questionsCorrect != 1) {
                questionPlural+= "s";
            }
            Toast.makeText(this,
                    "You got " + String.valueOf(questionsCorrect) +
                            " out of " +
                            QUESTIONS_NUMBER +
                            questionPlural + " correct!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
