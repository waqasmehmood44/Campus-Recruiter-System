package com.example.campusrecruitmentsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.ApplyForJobs.ApplyForJobs;
import com.example.campusrecruitmentsystem.Models.post_job_model;
import com.example.campusrecruitmentsystem.ViewHolders.student_jobs_list_view_holder;

import java.util.ArrayList;
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


//                int clickedPosition = holder.getAdapterPosition();
                Toast.makeText(context.getApplicationContext(), itemList.get(position).getJob_id(), Toast.LENGTH_SHORT).show();
//                if (clickedPosition != RecyclerView.NO_POSITION) {
                    if (context != null) {
                        Intent intent = new Intent(context, ApplyForJobs.class);
                        intent.putExtra("rec_id", itemList.get(position).getRec_id());
                        intent.putExtra("job_id", itemList.get(position).getJob_id());
                        context.startActivity(intent);
                    }
//                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<post_job_model> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (post_job_model item : itemList) {
                    if (item.getJob().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List<post_job_model>) results.values);
            notifyDataSetChanged();
        }
    };
}
