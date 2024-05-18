package com.example.campusrecruitmentsystem.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;

public class jobs_list_view_holder extends RecyclerView.ViewHolder {
    public TextView job_name;
    public TextView job_salary;
    public TextView job_eligiblity;
    public TextView job_location;
    public TextView job_description;
    public ImageView delete_icon;
    public jobs_list_view_holder(@NonNull View itemView) {
        super(itemView);
        job_name = itemView.findViewById(R.id.job_name);
        job_salary = itemView.findViewById(R.id.job_salary);
        job_eligiblity = itemView.findViewById(R.id.job_eligiblity);
        job_location = itemView.findViewById(R.id.job_location);
        job_description = itemView.findViewById(R.id.job_description);
        delete_icon = itemView.findViewById(R.id.delete_icon);
    }
}
