package com.rheneni.android.quizzapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**

 * Created by Lenovo on 18/01/2018.

 */

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int QUESTION_VIEW  = 1;
    private static final int RADIO_OPTION_VIEW = 2;
    private static final int CHECKBOX_OPTION_VIEW = 3;
    private static final int FREE_ANSWER = 4;
    private Context mContext;
    private ArrayList<Question> mQuestions;
    private RecyclerView mRecyclerView;
    private int mActualQuestionPos = 0;
    private int mActualAnswerPos = 0;
    private int mCount = 0;

    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        mContext = context;
        if (questions != null)
            mQuestions = new ArrayList<>(questions);
        else mQuestions = new ArrayList<>();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{
        public TextView questionNumber;
        public TextView questionText;

        public QuestionViewHolder(View itemView){
            super(itemView);
            questionNumber = (TextView) itemView.findViewById(R.id.question_number);
            questionText = (TextView) itemView.findViewById(R.id.question);
        }
    }

    public class RadioViewHolder extends RecyclerView.ViewHolder{
        public RadioButton radioButton;
        public TextView radioText;

        public RadioViewHolder(View itemView){
            super(itemView);
            radioText = (TextView)itemView.findViewById(R.id.option_text);
            radioButton = (RadioButton)itemView.findViewById(R.id.option_radio);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isQuestionPosition(getAdapterPosition());
                    mQuestions.get(mActualQuestionPos).setLastSelectedOption(mActualAnswerPos);
                    notifyDataSetChanged();
                }
            });
        }
    }

    private class CheckBoxViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
        public TextView checkBoxText;

        public CheckBoxViewHolder(View itemView){
            super(itemView);
            checkBox = (CheckBox)itemView.findViewById(R.id.option_checkbox);
            checkBoxText = (TextView)itemView.findViewById(R.id.option_text);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isQuestionPosition(getAdapterPosition());
                    mQuestions.get(mActualQuestionPos).setLastSelectedOption(mActualAnswerPos);
                    boolean isChecked = mQuestions.get(mActualQuestionPos).getSelectedPositionCheck(mActualAnswerPos);
                    mQuestions.get(mActualQuestionPos).setSelectedPositionCheck(mActualAnswerPos, !isChecked);
                    notifyDataSetChanged();
                }
            });
        }
    }

    private class EditTextViewHolder extends RecyclerView.ViewHolder {
        public EditText freeText;
        public EditTextViewHolder(View itemView){
            super(itemView);
            freeText = (EditText) itemView.findViewById(R.id.option_free);
            freeText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isQuestionPosition(getAdapterPosition());
                    mQuestions.get(mActualQuestionPos).getAnswerOptions().get(mActualAnswerPos).setFreeAnswer(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == QUESTION_VIEW) {
            return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.question, parent, false));
        } else if(viewType == RADIO_OPTION_VIEW) {
            return new RadioViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.question_radio_option, parent, false));
        } else if(viewType == CHECKBOX_OPTION_VIEW) {
            return new CheckBoxViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.question_checkbox_option, parent, false));
        }  else if(viewType == FREE_ANSWER) {
            return new EditTextViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.question_free_option, parent, false));
        } else {
            Log.d("tag", "viewType not found");
            return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.question, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            isQuestionPosition(position);
            if(holder.getItemViewType() == QUESTION_VIEW) {
                QuestionViewHolder vh = (QuestionViewHolder) holder;
                vh.questionNumber.setText(mQuestions.get(mActualQuestionPos).getQuestionName());
                vh.questionText.setText(mQuestions.get(mActualQuestionPos).getQuestionText());
            } else if (holder.getItemViewType() == RADIO_OPTION_VIEW) {
                RadioViewHolder vh = (RadioViewHolder) holder;
                boolean isChecked = mQuestions.get(mActualQuestionPos).getLastSelectedOption() == mActualAnswerPos;
                vh.radioButton.setChecked(isChecked);
                mQuestions.get(mActualQuestionPos).setSelectedPositionCheck(mActualAnswerPos, isChecked);
                vh.radioText.setText(mQuestions.get(mActualQuestionPos).getAnswerOptions().get(mActualAnswerPos).getOptionText());
            } else if(holder.getItemViewType() == CHECKBOX_OPTION_VIEW) {
                CheckBoxViewHolder vh = (CheckBoxViewHolder) holder;
                vh.checkBoxText.setText(mQuestions.get(mActualQuestionPos).getAnswerOptions().get(mActualAnswerPos).getOptionText());
                boolean isChecked = mQuestions.get(mActualQuestionPos).getSelectedPositionCheck(mActualAnswerPos);
                vh.checkBox.setChecked(isChecked);
            } else if(holder.getItemViewType() == FREE_ANSWER) {
                EditTextViewHolder vh = (EditTextViewHolder) holder;
                vh.freeText.setText(mQuestions.get(mActualQuestionPos).getAnswerOptions().get(mActualAnswerPos).getFreeAnswer());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int count = mCount;

        //Only goes through the cycle on the first call
        if(count == 0 && mQuestions != null) {
            for (int i = 0; i < mQuestions.size(); i++) {
                count += mQuestions.get(i).getOptionsNumber() + 1;
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if(isQuestionPosition(position)) {
            viewType = QUESTION_VIEW;
        } else {
            Question question = mQuestions.get(mActualQuestionPos);
            switch (question.getOptionType()){
                case AnswerOption.OPTION_TYPE_RADIO:
                    viewType = RADIO_OPTION_VIEW;
                    break;
                case AnswerOption.OPTION_TYPE_CHECKBOX:
                    viewType = CHECKBOX_OPTION_VIEW;
                    break;
                case AnswerOption.OPTION_TYPE_FREE:
                    viewType = FREE_ANSWER;
                    break;
                default:
                    break;
            }
        }
        return viewType;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    private boolean isQuestionPosition(int position){
        int count = 0;
        boolean isQuestionPos = false;
        for(int i = 0; i < mQuestions.size(); i++) {
            if(count == position) {
                mActualQuestionPos = i;
                isQuestionPos = true;
                break;
            }
            count += mQuestions.get(i).getOptionsNumber() +1;
            if(count == position ) {
                mActualQuestionPos = i + 1;
                isQuestionPos = true;
                break;
            } else if(count > position) {
                mActualQuestionPos = i;
                mActualAnswerPos = mQuestions.get(i).getOptionsNumber() - (count - position);
                break;
            }
        }
        return isQuestionPos;
    }
}