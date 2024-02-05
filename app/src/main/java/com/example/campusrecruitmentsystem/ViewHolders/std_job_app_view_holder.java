package com.example.campusrecruitmentsystem.ViewHolders;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;

public class std_job_app_view_holder extends RecyclerView.ViewHolder{
    public TextView std_job_name;
    public TextView std_job_location;
    public TextView std_job_desc;
    public TextView std_job_salary;
    public  TextView std_job_cv;
    public Button view_btn;

    public std_job_app_view_holder(@NonNull View itemView) {
        super(itemView);
        std_job_name = itemView.findViewById(R.id.std_job_name);
        std_job_location = itemView.findViewById(R.id.std_job_location);
        std_job_desc = itemView.findViewById(R.id.std_job_desc);
        std_job_salary = itemView.findViewById(R.id.std_job_salary);
        std_job_cv = itemView.findViewById(R.id.std_job_cv);
        view_btn = itemView.findViewById(R.id.view_btn);
        std_job_cv.setPaintFlags(std_job_cv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }
}
