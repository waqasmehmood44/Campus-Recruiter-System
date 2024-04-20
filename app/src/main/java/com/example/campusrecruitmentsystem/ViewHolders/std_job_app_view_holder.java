package com.example.campusrecruitmentsystem.ViewHolders;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;

public class std_job_app_view_holder extends RecyclerView.ViewHolder{
    public TextView std_job_name;
    public TextView std_job_location;
    public TextView std_job_desc;
    public TextView std_job_salary;
    public TextView std_job_cv,marks,time_option_1 , time_option_2,selected_time1;
    public LinearLayout recruiter_layout,student_layout,marks_layout,time_option_1_layout ,time_option_2_layout, selected_time_layout;
    public Button accept_btn,reject_btn,create_test_button,select_test,take_test,assign_int_time,send_int_link,generate_offer_letter;
    public RadioButton radio_button_int_time1,radio_button_int_time2;


    public std_job_app_view_holder(@NonNull View itemView) {
        super(itemView);
        std_job_name = itemView.findViewById(R.id.std_job_name);
        std_job_location = itemView.findViewById(R.id.std_job_location);
        std_job_desc = itemView.findViewById(R.id.std_job_desc);
        std_job_salary = itemView.findViewById(R.id.std_job_salary);
        std_job_cv = itemView.findViewById(R.id.std_job_cv);
        accept_btn = itemView.findViewById(R.id.accept_btn);
        reject_btn = itemView.findViewById(R.id.reject_btn);
        select_test = itemView.findViewById(R.id.select_test);
        create_test_button = itemView.findViewById(R.id.create_test_button);
        recruiter_layout = itemView.findViewById(R.id.recruiter_layout);
        take_test = itemView.findViewById(R.id.take_test);
        student_layout = itemView.findViewById(R.id.student_layout);
        assign_int_time = itemView.findViewById(R.id.assign_int_time);
        marks = itemView.findViewById(R.id.marks);
        marks_layout = itemView.findViewById(R.id.marks_layout);
        time_option_2_layout = itemView.findViewById(R.id.time_option_2_layout);
        time_option_1_layout = itemView.findViewById(R.id.time_option_1_layout);
        time_option_1 = itemView.findViewById(R.id.time_option_1);
        time_option_2 = itemView.findViewById(R.id.time_option_2);
        radio_button_int_time2 = itemView.findViewById(R.id.radio_button_int_time2);
        radio_button_int_time1 = itemView.findViewById(R.id.radio_button_int_time1);
        selected_time1 = itemView.findViewById(R.id.selected_time1);
        selected_time_layout = itemView.findViewById(R.id.selected_time_layout);
        send_int_link = itemView.findViewById(R.id.send_int_link);
        generate_offer_letter = itemView.findViewById(R.id.generate_offer_letter);
        std_job_cv.setPaintFlags(std_job_cv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }
}
