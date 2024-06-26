package com.example.campusrecruitmentsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.ViewHolders.jobs_list_view_holder;
import com.example.campusrecruitmentsystem.Models.post_job_model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class jobs_list_view_adapter extends RecyclerView.Adapter<jobs_list_view_holder> {
    Context context;
    DatabaseReference reference,reference1;
    List<post_job_model> itemList;
    List<post_job_model> itemListFull; // A full copy of your list

    public jobs_list_view_adapter(Context context, List<post_job_model> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.itemListFull = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public jobs_list_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new jobs_list_view_holder(LayoutInflater.from(context).inflate(R.layout.jobs_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull jobs_list_view_holder holder, int position) {
    holder.job_name.setText(itemList.get(position).getJob());
    holder.job_salary.setText(itemList.get(position).getSalary());
    holder.job_eligiblity.setText(itemList.get(position).getEligibility());
    holder.job_location.setText(itemList.get(position).getLocation());
    holder.job_description.setText(itemList.get(position).getDescription());
    holder.delete_icon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reference = FirebaseDatabase.getInstance().getReference().child("Jobs").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
            reference1 = FirebaseDatabase.getInstance().getReference().child("Student Jobs List").child(itemList.get(position).getJob_id());
            reference1.removeValue();
            reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Job Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
            });
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
