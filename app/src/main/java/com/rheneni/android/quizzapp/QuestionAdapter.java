package com.rheneni.android.quizzapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    private int lastSelectedPosition = -1;
    private ArrayList<Boolean> selectedPositions = new ArrayList<>();

    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        mContext = context;
        if (questions != null)
            mQuestions = new ArrayList<>(questions);
        else mQuestions = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView questionNumber;
        public TextView questionText;
        public TextView optionText;
        public RadioButton radioOption;
        public CheckBox checkBoxOption;
        public EditText freeText;

        public ViewHolder(final View itemView) {
            super(itemView);

            questionNumber = (TextView) itemView.findViewById(R.id.question_number);
            questionText = (TextView) itemView.findViewById(R.id.question);

            //optionText has the same id in both radio and checkbox layout;
            optionText = (TextView)itemView.findViewById(R.id.option_text);
            radioOption = (RadioButton)itemView.findViewById(R.id.option_radio);
            checkBoxOption = (CheckBox)itemView.findViewById(R.id.option_checkbox);

            freeText = (EditText)itemView.findViewById(R.id.option_free);
        }

        public void bindQuestionViewList(int pos){
            Log.d("tag", "pos:" + pos);
            Question question = mQuestions.get(mActualQuestionPos);
            String message = question.getQuestionName();
            questionNumber.setText(message);
            questionText.setText(question.getQuestionText());
        }

        public void bindRadioViewList(int pos){
            Log.d("tag", "pos:" + pos);
            Question question = mQuestions.get(mActualQuestionPos);

            String text = question.getAnswerOptions().get(mActualAnswerPos).getOptionText();
            boolean isChecked = question.getAnswerOptions().get(mActualAnswerPos).getIsChecked();
            optionText.setText(text);
            radioOption.setSelected(isChecked);
            radioOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    int questionIndex = findLastPositionQuestion();
                    int answerIndex = findLastPositionAnswer();
                    boolean isChecked = mQuestions.get(questionIndex).getAnswerOptions().get(answerIndex).getIsChecked();
                    mQuestions.get(questionIndex).getAnswerOptions().get(answerIndex).setIsChecked(!isChecked);
                    radioOption.setSelected(!isChecked);
                    notifyDataSetChanged();
                }
            });
        }

        public void bindCheckBoxViewList(int pos){
            Log.d("tag", "pos:" + pos);
            Question question = mQuestions.get(mActualQuestionPos);

            String text = question.getAnswerOptions().get(mActualAnswerPos).getOptionText();
            boolean isChecked = question.getAnswerOptions().get(mActualAnswerPos).getIsChecked();
            optionText.setText(text);
            checkBoxOption.setSelected(isChecked);
        }

        public void bindEditTextViewList(int pos){
            Log.d("tag", "pos:" + pos);
            Question question = mQuestions.get(mActualQuestionPos);

            String text = question.getAnswerOptions().get(mActualAnswerPos).getFreeAnswer();
            freeText.setText(text);

            /*freeText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    lastSelectedPosition = getAdapterPosition();

                    int questionIndex = findLastPositionQuestion();
                    mQuestions.get(questionIndex).getAnswerOptions().get(0).setFreeAnswer(s.toString());
                    freeText.setText(mQuestions.get(questionIndex).getAnswerOptions().get(0).getFreeAnswer());
                    //notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });*/
        }
    }

    private class QuestionViewHolder extends ViewHolder{
        public QuestionViewHolder(View itemView){
            super(itemView);
        }
    }

    private class RadioViewHolder extends ViewHolder{
        public RadioViewHolder(View itemView){
            super(itemView);
        }
    }

    private class CheckBoxViewHolder extends ViewHolder{
        public CheckBoxViewHolder(View itemView){
            super(itemView);
        }
    }

    private class EditTextViewHolder extends ViewHolder{
        public EditTextViewHolder(View itemView){
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == QUESTION_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false);
            QuestionViewHolder vh = new QuestionViewHolder(v);
            return vh;
        } else if(viewType == RADIO_OPTION_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_radio_option, parent, false);
            RadioViewHolder vh = new RadioViewHolder(v);
            return vh;
        } else if(viewType == CHECKBOX_OPTION_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_checkbox_option, parent, false);
            CheckBoxViewHolder vh = new CheckBoxViewHolder(v);
            return vh;
        } else if(viewType == FREE_ANSWER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_free_option, parent, false);
            EditTextViewHolder vh = new EditTextViewHolder(v);
            return vh;
        } else {
            Log.d("tag", "viewType not found");
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_radio_option, parent, false);
            RadioViewHolder vh = new RadioViewHolder(v);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if(holder instanceof QuestionViewHolder){
                QuestionViewHolder vh = (QuestionViewHolder)holder;
                vh.bindQuestionViewList(position);
            } else if (holder instanceof RadioViewHolder) {
                RadioViewHolder vh = (RadioViewHolder) holder;
                vh.bindRadioViewList(position);
            } else if(holder instanceof CheckBoxViewHolder) {
                CheckBoxViewHolder vh = (CheckBoxViewHolder) holder;
                vh.bindCheckBoxViewList(position);
            } else if(holder instanceof EditTextViewHolder) {
                EditTextViewHolder vh = (EditTextViewHolder) holder;
                vh.bindEditTextViewList(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(mQuestions != null){
            for(int i = 0; i < mQuestions.size(); i++) {
                count += mQuestions.get(i).getOptionsNumber() + 1;
            }
            Log.v("tag", "itemCount: " + count);
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
                mActualAnswerPos = mQuestions.get(i).getOptionsNumber() - (count - position);
                break;
            }
        }
        return isQuestionPos;
    }

    private int findLastPositionQuestion() {
        int count = 0;
        int lastPositionQuestion = 0;
        for(int i = 0; i < mQuestions.size(); i++) {
            count += mQuestions.get(i).getOptionsNumber() +1;
            if(count > lastSelectedPosition) {
                lastPositionQuestion = i;
                break;
            }
        }
        return lastPositionQuestion;
    }

    private int findLastPositionAnswer() {
        int count = 0;
        int lastPositionAnswer = 0;
        for(int i = 0; i < mQuestions.size(); i++) {
            if(count == lastSelectedPosition) {
                break;
            }
            count += mQuestions.get(i).getOptionsNumber() +1;
            if(count == lastSelectedPosition ) {
                break;
            } else if(count > lastSelectedPosition) {
                lastPositionAnswer = mQuestions.get(i).getOptionsNumber() - (count - lastSelectedPosition);
                break;
            }
        }
        return lastPositionAnswer;
    }
}