package com.example.campusrecruitmentsystem.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.campusrecruitmentsystem.LoginActivity;
import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.StudentFormActivity;
import com.example.campusrecruitmentsystem.StudentTest.student_test;
import com.example.campusrecruitmentsystem.Submit_Application;
import com.example.campusrecruitmentsystem.ViewHolders.std_job_app_view_holder;
import com.example.campusrecruitmentsystem.post_job_model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class std_job_app_adapter extends RecyclerView.Adapter<std_job_app_view_holder>{
    Context context;
    String key;
    ArrayList<String> testNamesList = new ArrayList<>();
    ArrayList<String> testidList = new ArrayList<>();
    DatabaseReference reference, reference1,test_list_ref,test_name_ref;

    String[] items = {"Item 1", "Item 2", "Item 3"};
    List<Submit_Application> itemList;
    boolean std_fragment;
    public std_job_app_adapter(Context context, List<Submit_Application> itemList,boolean std_fragment) {
        this.context = context;
        this.itemList = itemList;
        this.std_fragment = std_fragment;
    }
        @NonNull
    @Override
    public std_job_app_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new std_job_app_view_holder(LayoutInflater.from(context).inflate(R.layout.std_job_app_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull std_job_app_view_holder holder, int position) {
        reference = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
        reference1 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());
        holder.std_job_name.setText(itemList.get(position).getJob_name());
        holder.std_job_salary.setText(itemList.get(position).getJob_salary());
        holder.std_job_location.setText(itemList.get(position).getJob_location());
        holder.std_job_desc.setText(itemList.get(position).getJob_desc());
        holder.std_job_cv.setText(itemList.get(position).getName());
        Toast.makeText(context.getApplicationContext(), String.valueOf(std_fragment), Toast.LENGTH_SHORT).show();
        if (std_fragment) {
            holder.recruiter_layout.setVisibility(View.GONE);
            holder.student_layout.setVisibility(View.VISIBLE);
        } else {
            holder.recruiter_layout.setVisibility(View.VISIBLE);
            holder.student_layout.setVisibility(View.GONE);
        }
        holder.take_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, student_test.class);
                intent.putExtra("Test_id",itemList.get(position).getTest_id());
                intent.putExtra("recruiter_id",itemList.get(position).getRec_id());
                context.startActivity(intent);
            }
        });
        holder.select_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Test");
                final String[] itemsArray = testNamesList.toArray(new String[testNamesList.size()]);
                builder.setItems(itemsArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedItem = testNamesList.get(which);
                        String selectedId = testidList.get(which);
                        Map<String, Object> updateTestId = new HashMap<>();
                        updateTestId.put("test_id", selectedId);
                        reference1.updateChildren(updateTestId);
                        reference.updateChildren(updateTestId);
                        Toast.makeText(context.getApplicationContext(), "Test Assigned", Toast.LENGTH_SHORT).show();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        if("Accepted".equals(itemList.get(position).getApplication_status()) || "Rejected".equals(itemList.get(position).getApplication_status())){
            holder.accept_btn.setVisibility(View.GONE);
            holder.reject_btn.setVisibility(View.GONE);
            holder.select_test.setVisibility(View.VISIBLE);
        }
        if (!"".equals(itemList.get(position).getTest_id())) {
            holder.accept_btn.setVisibility(View.GONE);
            holder.reject_btn.setVisibility(View.GONE);
            holder.select_test.setVisibility(View.GONE);
        }
        //get Tests List
        test_list_ref = FirebaseDatabase.getInstance().getReference().child("Applicants Test Names")
                .child(itemList.get(position).getRec_id());
        test_list_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    if("Accepted".equals(itemList.get(position).getApplication_status()) || "Rejected".equals(itemList.get(position).getApplication_status())){
                        holder.create_test_button.setVisibility(View.VISIBLE);
                        holder.select_test.setVisibility(View.GONE);
                    }
                }
                testNamesList.clear();
                for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                    key = appSnapshot.getKey();


                    test_name_ref = FirebaseDatabase.getInstance().getReference().child("Applicants Test Names")
                            .child(itemList.get(position).getRec_id()).child(key);

                    test_name_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();

                            // Extract the test name from the HashMap
                            String test_name = (String) data.get("test_name");
                            String test_id = (String) data.get("test_id");
                            testNamesList.add(test_name);
                            testidList.add(test_id);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                testNamesList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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
        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> updates = new HashMap<>();
                updates.put("application_status", "Accepted");
                reference1.updateChildren(updates);
                reference.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        holder.accept_btn.setVisibility(View.GONE);
                        holder.reject_btn.setVisibility(View.GONE);
                        holder.select_test.setVisibility(View.VISIBLE);
                        Toast.makeText(v.getContext(), "Application Accepted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> updates = new HashMap<>();
                updates.put("application_status", "Rejected");
                reference1.updateChildren(updates);
                reference.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        holder.accept_btn.setVisibility(View.GONE);
                        holder.reject_btn.setVisibility(View.GONE);
                        Toast.makeText(v.getContext(), "Application Rejected", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
}
}
