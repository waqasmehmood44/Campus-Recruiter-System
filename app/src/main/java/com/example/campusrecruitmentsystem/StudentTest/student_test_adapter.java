package com.example.campusrecruitmentsystem.StudentTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.Submit_Application;
import com.example.campusrecruitmentsystem.ViewHolders.std_job_app_view_holder;

import java.util.List;

public class student_test_adapter extends RecyclerView.Adapter<student_test_view_holder>{
    @NonNull
    List<student_test_model> itemList;
    String correct_ans;
    Context context;
    public student_test_adapter(Context context, List<student_test_model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    @Override
    public student_test_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new student_test_view_holder(LayoutInflater.from(context).inflate(R.layout.student_test_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull student_test_view_holder holder, int position) {
        int count = position + 1;
        holder.question_text.setText(count+":"+itemList.get(position).getQuestion_text());
        holder.radio_button1.setText(itemList.get(position).getOption_1());
        holder.radio_button2.setText(itemList.get(position).getOption_2());
        holder.radio_button3.setText(itemList.get(position).getOption_3());
        correct_ans = itemList.get(position).getCorrect_ans();

        holder.radio_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correct_ans.equals(itemList.get(position).getOption_1())){

                }
            }
        });
        holder.radio_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correct_ans.equals(itemList.get(position).getOption_1())){

                }
            }
        });
        holder.radio_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correct_ans.equals(itemList.get(position).getOption_1())){

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
