package com.rheneni.android.quizzapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lenovo on 22/01/2018.
 */

public class AnswerOptionAdapter extends RecyclerView.Adapter<AnswerOptionAdapter.AnswerOptionHolder>{

    private Context mContext;
    private List<AnswerOption> mAnswersList;
    private int lastSelectedPosition = -1;

    public class AnswerOptionHolder  extends RecyclerView.ViewHolder{
        public RadioButton answerRadio;
        public TextView answerText;

        public AnswerOptionHolder(View itemView) {
            super(itemView);
            answerText = (TextView) itemView.findViewById(R.id.option_text);
            answerRadio = (RadioButton) itemView.findViewById(R.id.option_radio);

            answerRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();

                    notifyDataSetChanged();
                }
            });
        }
    }

    public AnswerOptionAdapter(Context context, List<AnswerOption> answerOptions) {
        mContext = context;
        if (answerOptions != null)
            this.mAnswersList = new ArrayList<>(answerOptions);
        else this.mAnswersList = new ArrayList<>();
    }

    public void setData(List<AnswerOption> data) {
        if (mAnswersList != data) {
            mAnswersList = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public AnswerOptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnswerOptionHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_radio_option, parent, false));
    }

    @Override
    public void onBindViewHolder(AnswerOptionHolder holder, final int position) {

        AnswerOption answerOption = mAnswersList.get(position);

        holder.answerText.setText(String.format(Locale.getDefault(), "%d: %s",
                answerOption.getOrderNumber(),
                getStringFromResourcesByName(
                        "question" +
                        (answerOption.getQuestionNumber()) +
                        "_option" +
                        answerOption.getOrderNumber()
                )));
        holder.answerRadio.setChecked(lastSelectedPosition == position);
        mAnswersList.get(position).setIsChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return mAnswersList != null ? mAnswersList.size() : 0;
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
