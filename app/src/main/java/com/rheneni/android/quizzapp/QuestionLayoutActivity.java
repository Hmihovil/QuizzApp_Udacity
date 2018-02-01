package com.rheneni.android.quizzapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 18/01/2018.
 */

public class QuestionLayoutActivity extends AppCompatActivity {

    static final int QUESTIONS_NUMBER = 10;
    private List<List<Integer>> mSolutions;
    private List<Integer> mOptionTypes = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private List<Question> mList = new ArrayList<>();
    private QuestionAdapter mAdapter;
    private int questionsCorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_layout_recycler);
        mSolutions = getSolutions();
        setupRecycler();
    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        for(int i = 0; i < QUESTIONS_NUMBER; i++) {
            mList.add(new Question("Question", i+1, mOptionTypes.get(i)));
        }

        mAdapter = new QuestionAdapter(this, mList, mOptionTypes);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private List<List<Integer>> getSolutions() {
        List<List<Integer>> solutions = new ArrayList<>();
        for(int i = 0; i < QUESTIONS_NUMBER; i++) {
            List<Integer> solution = new ArrayList<>();
            //Should change to Enums/constants
            switch (i+1){
                case 1:
                    solution.add(4);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    break;
                case 2:
                    solution.add(3);
                    solution.add(5);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_CHECKBOX);
                    break;
                case 3:
                    solution.add(3);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    break;
                case 4:
                    solution.add(4);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    break;
                case 5:
                    solution.add(5);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    break;
                case 6:
                    solution.add(1);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    break;
                case 7:
                    solution.add(5);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    break;
                case 8:
                    solution.add(1);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    break;
                case 9:
                    solution.add(4);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_RADIO);
                    break;
                case 10:
                    solution.add(1);
                    mOptionTypes.add(AnswerOption.OPTION_TYPE_FREE);
                    break;
                default:
                    break;
            }
            solutions.add(solution);
        }

        return solutions;
    }

    public void showResults(View view) {
        if(mList.get(9).getAnswerOptions().get(0).getFreeAnswer() == "") {
            Toast.makeText(this,
                    "You have to write a number on question " +
                    QUESTIONS_NUMBER + "!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < QUESTIONS_NUMBER; i++) {
                List<AnswerOption> listAnswers = mList.get(i).getAnswerOptions();
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
            questionsCorrect = 0;
        }
    }
}
