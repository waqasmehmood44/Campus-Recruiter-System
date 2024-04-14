package com.example.campusrecruitmentsystem.StudentTest;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;

public class student_test_view_holder extends RecyclerView.ViewHolder {
    public TextView question_text,option_1,option_2,option_3;
    public RadioButton radio_button1,radio_button2,radio_button3;

    public student_test_view_holder(@NonNull View itemView) {
        super(itemView);
        question_text = itemView.findViewById(R.id.question_text);
        radio_button1 = itemView.findViewById(R.id.radio_button1);
        radio_button2 = itemView.findViewById(R.id.radio_button2);
        radio_button3 = itemView.findViewById(R.id.radio_button3);
    }

}
