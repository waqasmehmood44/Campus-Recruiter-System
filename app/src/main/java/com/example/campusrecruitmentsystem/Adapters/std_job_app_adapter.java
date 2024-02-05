package com.example.campusrecruitmentsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.Submit_Application;
import com.example.campusrecruitmentsystem.ViewHolders.std_job_app_view_holder;

import java.util.List;

public class std_job_app_adapter extends RecyclerView.Adapter<std_job_app_view_holder>{
    Context context;
    List<Submit_Application> itemList;
    public std_job_app_adapter(Context context, List<Submit_Application> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
        @NonNull
    @Override
    public std_job_app_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new std_job_app_view_holder(LayoutInflater.from(context).inflate(R.layout.std_job_app_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull std_job_app_view_holder holder, int position) {
        holder.std_job_name.setText(itemList.get(position).getJob_name());
        holder.std_job_salary.setText(itemList.get(position).getJob_salary());
        holder.std_job_location.setText(itemList.get(position).getJob_location());
        holder.std_job_desc.setText(itemList.get(position).getJob_desc());
        holder.std_job_cv.setText(itemList.get(position).getName());
        holder.std_job_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    if (context != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setType("application/pdf");
                        intent.setData(Uri.parse(itemList.get(clickedPosition).getUri()));
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
