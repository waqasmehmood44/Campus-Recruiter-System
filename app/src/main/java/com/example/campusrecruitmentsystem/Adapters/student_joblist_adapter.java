package com.example.campusrecruitmentsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.Student_Job_Apply.Student_job_apply;
import com.example.campusrecruitmentsystem.jobs_list_view_holder;
import com.example.campusrecruitmentsystem.post_job_model;
import com.example.campusrecruitmentsystem.student_jobs_list_view_holder;

import java.util.List;

public class student_joblist_adapter extends RecyclerView.Adapter<student_jobs_list_view_holder> {
    Context context;
    List<post_job_model> itemList;

    public student_joblist_adapter(Context context, List<post_job_model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public student_jobs_list_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new student_jobs_list_view_holder(LayoutInflater.from(context).inflate(R.layout.student_jobs_list_view, parent, false));
    }

    @Override
//
    public void onBindViewHolder(@NonNull student_jobs_list_view_holder holder, int position) {
        holder.job_name.setText(itemList.get(position).getJob());
        holder.job_salary.setText(itemList.get(position).getSalary());
        holder.job_eligiblity.setText(itemList.get(position).getEligibility());
        holder.job_location.setText(itemList.get(position).getLocation());
        holder.job_description.setText(itemList.get(position).getDescription());

        holder.apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "Apply", Toast.LENGTH_SHORT).show();

                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    if (context != null) {
                        Intent intent = new Intent(context, Student_job_apply.class);
                        intent.putExtra("rec_id", itemList.get(clickedPosition).getRec_id());
                        intent.putExtra("job_id", itemList.get(clickedPosition).getJob_id());
                        intent.putExtra("job_name", itemList.get(clickedPosition).getJob());
                        intent.putExtra("job_salary", itemList.get(clickedPosition).getSalary());
                        context.startActivity(intent);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
