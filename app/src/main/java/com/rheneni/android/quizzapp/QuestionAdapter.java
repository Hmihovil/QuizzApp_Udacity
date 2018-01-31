package com.rheneni.android.quizzapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lenovo on 18/01/2018.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder> {

    private Context mContext;
    private List<Question> mListQuestions;
    private RecyclerView mAnswerOptions;
    private AnswerOptionAdapter answerOptionAdapter;

    public class QuestionHolder extends RecyclerView.ViewHolder {

        public TextView questionNumber;
        public TextView questionText;

        public QuestionHolder(View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            questionNumber = (TextView) itemView.findViewById(R.id.question_number);
            questionText = (TextView) itemView.findViewById(R.id.question);

            mAnswerOptions = (RecyclerView) itemView.findViewById(R.id.question_option_recycler_view);
            mAnswerOptions.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    public QuestionAdapter(Context context, List<Question> questions) {
        mContext = context;
        if (questions != null)
            this.mListQuestions = new ArrayList<>(questions);
        else mListQuestions = new ArrayList<>();
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question, parent, false));
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, final int position) {
        String message = mListQuestions.get(position).getQuestionName();
        Log.d("tag", message);
        Question question = mListQuestions.get(position);

        holder.questionNumber.setText(String.format(Locale.getDefault(), "%s %d",
                question.getQuestionName(),
                question.getNumber()
        ));
        holder.questionText.setText(getStringFromResourcesByName(
                "question" + (position+1)));

        answerOptionAdapter = new AnswerOptionAdapter(mContext, question.getAnswerOptions());
        mAnswerOptions.setAdapter(answerOptionAdapter);
    }

    @Override
    public int getItemCount() {
        return mListQuestions != null ? mListQuestions.size() : 0;
    }

    private String getStringFromResourcesByName(String resourceName){
        // Get the application package name
        String packageName = mContext.getPackageName();

        // Get the string resource id by name
        int resourceId = mContext.getResources().getIdentifier(resourceName,"string",packageName);

        // Return the string value
        return mContext.getString(resourceId);
    }
}
