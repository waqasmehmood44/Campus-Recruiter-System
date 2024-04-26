package com.example.campusrecruitmentsystem.AdminList;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.Models.User_Model;
import com.example.campusrecruitmentsystem.Models.post_job_model;
import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.ViewHolders.student_jobs_list_view_holder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admin_list_adapter extends RecyclerView.Adapter<admin_list_view_holder> {
    @NonNull
    Context context;
    DatabaseReference reference;
    List<User_Model> itemList;

    public admin_list_adapter(Context context, List<User_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    public admin_list_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new admin_list_view_holder(LayoutInflater.from(context).inflate(R.layout.admin_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull admin_list_view_holder holder, int position) {
        holder.user_name.setText(itemList.get(position).getName());
        holder.user_type111.setText(itemList.get(position).getUser_type());
        holder.contact_number.setText(itemList.get(position).getContact_no());
        holder.user_department.setText(itemList.get(position).getDepartment());
        holder.user_address.setText(itemList.get(position).getAddress());
        holder.user_email.setText(itemList.get(position).getUser_email());

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Personal Info").child(itemList.get(position).getUserId());
                reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Personal Info").child(itemList.get(position).getUserId());
                String name = String.valueOf(holder.user_name.getText());
                String usertype = String.valueOf(holder.user_type111.getText());
                String user_address = String.valueOf(holder.user_address.getText());
                String contact_no = String.valueOf(holder.contact_number.getText());
                String user_department = String.valueOf(holder.user_department.getText());
                Map<String, Object> user_model = new HashMap<>();
                user_model.put("name", name);
                user_model.put("contact_no", contact_no);
                user_model.put("department", user_department);
                user_model.put("user_type", usertype);
                user_model.put("address", user_address);
                reference.updateChildren(user_model);
                Toast.makeText(context, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
