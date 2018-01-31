package com.rheneni.android.quizzapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


    private RecyclerView mRecyclerView;
    private List<Question> mList = new ArrayList<>();
    private QuestionAdapter mAdapter;
    private int questionsCorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_layout_recycler);
        setupRecycler();
    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        for(int i = 0; i < QUESTIONS_NUMBER; i++) {
            mList.add(new Question("Question", i+1));
        }

        mAdapter = new QuestionAdapter(this, mList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void showResults(View view) {
        questionsCorrect = 4;
        String questionPlural = " question";
        if(questionsCorrect != 1) {
            questionPlural+= "s";
        }
        Toast.makeText(this,
                "You got " + String.valueOf(questionsCorrect) + questionPlural + " correct!",
                Toast.LENGTH_LONG).show();
    }
}

