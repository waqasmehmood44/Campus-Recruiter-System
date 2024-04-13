package com.example.campusrecruitmentsystem.ViewHolders;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    public  TextView std_job_cv,spinner_text;
    public Button accept_btn,reject_btn,create_test_button;
    public Spinner test_list;
    public LinearLayout spinner_layout;

    public std_job_app_view_holder(@NonNull View itemView) {
        super(itemView);
        std_job_name = itemView.findViewById(R.id.std_job_name);
        std_job_location = itemView.findViewById(R.id.std_job_location);
        std_job_desc = itemView.findViewById(R.id.std_job_desc);
        std_job_salary = itemView.findViewById(R.id.std_job_salary);
        std_job_cv = itemView.findViewById(R.id.std_job_cv);
        accept_btn = itemView.findViewById(R.id.accept_btn);
        reject_btn = itemView.findViewById(R.id.reject_btn);
        test_list = itemView.findViewById(R.id.test_list);
        spinner_text = itemView.findViewById(R.id.spinner_text);
        spinner_layout = itemView.findViewById(R.id.spinner_layout);
        create_test_button = itemView.findViewById(R.id.create_test_button);
        std_job_cv.setPaintFlags(std_job_cv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }
}
