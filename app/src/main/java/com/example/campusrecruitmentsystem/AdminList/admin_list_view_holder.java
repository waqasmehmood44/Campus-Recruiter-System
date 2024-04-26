package com.example.campusrecruitmentsystem.AdminList;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.R;

public class admin_list_view_holder extends RecyclerView.ViewHolder {
    public EditText contact_number, user_address,user_name,user_department,user_email;
    public Button delete_btn,update_info;
    public EditText user_type111;


    public admin_list_view_holder(@NonNull View itemView) {
        super(itemView);
        contact_number = itemView.findViewById(R.id.contact_number);
        user_address = itemView.findViewById(R.id.user_address);
        user_type111 = itemView.findViewById(R.id.user_type111);
        user_name = itemView.findViewById(R.id.user_name);
        delete_btn = itemView.findViewById(R.id.delete_btn);
        update_info = itemView.findViewById(R.id.update_info);
        user_email = itemView.findViewById(R.id.user_email);
        user_department = itemView.findViewById(R.id.user_department);
    }
}
