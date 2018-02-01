package com.rheneni.android.quizzapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private int mOptionType;
    private int lastSelectedPosition = -1;
    private String answerFree = "";
    private List<Boolean> selectedPositions = new ArrayList<>();

    public class AnswerOptionHolder  extends RecyclerView.ViewHolder{
        public RadioButton answerRadio;
        public CheckBox answerCheckbox;
        public EditText answerFreeText;
        public TextView answerText;

        public AnswerOptionHolder(View itemView) {
            super(itemView);
            answerText = (TextView) itemView.findViewById(R.id.option_text);
            if(mOptionType == AnswerOption.OPTION_TYPE_RADIO) {
                answerRadio = (RadioButton) itemView.findViewById(R.id.option_radio);
                answerRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lastSelectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                });
            }
            else if(mOptionType == AnswerOption.OPTION_TYPE_CHECKBOX){
                answerCheckbox = (CheckBox) itemView.findViewById(R.id.option_checkbox);
                answerCheckbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lastSelectedPosition = getAdapterPosition();
                        selectedPositions.set(lastSelectedPosition, !selectedPositions.get(lastSelectedPosition));
                        notifyDataSetChanged();
                    }
                });
            }
            else if(mOptionType == AnswerOption.OPTION_TYPE_FREE) {
                answerFreeText = (EditText) itemView.findViewById(R.id.option_free);
                answerFreeText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        lastSelectedPosition = getAdapterPosition();
                        mAnswersList.get(lastSelectedPosition).setFreeAnswer(s.toString());
                        notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });
            }
        }
    }

    public AnswerOptionAdapter(Context context, List<AnswerOption> answerOptions, int optionType) {
        mContext = context;
        mOptionType = optionType;
        if (answerOptions != null)
            this.mAnswersList = new ArrayList<>(answerOptions);
        else this.mAnswersList = new ArrayList<>();

        for(int i = 0; i < mAnswersList.size(); i++) {
            selectedPositions.add(false);
        }
    }

    @Override
    public AnswerOptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mOptionType == AnswerOption.OPTION_TYPE_CHECKBOX) {
            return new AnswerOptionHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_checkbox_option, parent, false));
        }
        else if(mOptionType == AnswerOption.OPTION_TYPE_FREE){
            return new AnswerOptionHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_free_option, parent, false));
        }
        //OPTION_TYPE_RADIO or default
        else {
            return new AnswerOptionHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_radio_option, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(AnswerOptionHolder holder, final int position) {

        AnswerOption answerOption = mAnswersList.get(position);

        if(mOptionType != AnswerOption.OPTION_TYPE_FREE) {
            holder.answerText.setText(String.format(Locale.getDefault(), "%d: %s",
                answerOption.getOrderNumber(),
                getStringFromResourcesByName(
                        "question" +
                        (answerOption.getQuestionNumber()) +
                        "_option" +
                        answerOption.getOrderNumber()
                )));
            boolean isChecked = false;
            if (mOptionType == AnswerOption.OPTION_TYPE_RADIO) {
                isChecked = lastSelectedPosition == position;
                holder.answerRadio.setChecked(isChecked);
            } else if (mOptionType == AnswerOption.OPTION_TYPE_CHECKBOX) {
                isChecked = selectedPositions.get(position);
                holder.answerCheckbox.setChecked(isChecked);
            }
            mAnswersList.get(position).setIsChecked(isChecked);
        }
        else {
            String free = String.format(Locale.getDefault(), "%s", holder.answerFreeText.getText());
            answerFree = free;
            mAnswersList.get(position).setFreeAnswer(free);
        }
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
