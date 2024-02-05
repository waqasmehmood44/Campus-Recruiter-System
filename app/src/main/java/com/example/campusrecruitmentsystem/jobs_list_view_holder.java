package com.example.campusrecruitmentsystem;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class jobs_list_view_holder extends RecyclerView.ViewHolder {
    public TextView job_name;
    public TextView job_salary;
    public TextView job_eligiblity;
    public TextView job_location;
    public TextView job_description;
    public jobs_list_view_holder(@NonNull View itemView) {
        super(itemView);
        job_name = itemView.findViewById(R.id.job_name);
        job_salary = itemView.findViewById(R.id.job_salary);
        job_eligiblity = itemView.findViewById(R.id.job_eligibility);
        job_location = itemView.findViewById(R.id.job_location);
        job_description = itemView.findViewById(R.id.job_description);
    }
}
