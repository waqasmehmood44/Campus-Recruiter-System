package com.example.campusrecruitmentsystem.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;

public class student_jobs_list_view_holder extends RecyclerView.ViewHolder {
    public TextView job_name;
    public TextView job_salary;
    public TextView job_eligiblity;
    public TextView job_location;
    public TextView job_description;
    public Button apply_btn;
    public student_jobs_list_view_holder(@NonNull View itemView) {
        super(itemView);
        job_name = itemView.findViewById(R.id.job_name1);
        job_salary = itemView.findViewById(R.id.job_salary1);
        job_eligiblity = itemView.findViewById(R.id.job_eligiblity1);
        job_location = itemView.findViewById(R.id.job_location1);
        job_description = itemView.findViewById(R.id.job_description1);
        apply_btn = itemView.findViewById(R.id.apply_btn);
    }
}
