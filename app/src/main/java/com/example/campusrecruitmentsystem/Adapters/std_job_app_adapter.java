package com.example.campusrecruitmentsystem.Adapters;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.campusrecruitmentsystem.R;
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
    DatabaseReference reference, reference1,test_list_ref,test_name_ref;
    ArrayAdapter<String> adapter;
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
        reference = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
        reference1 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());
        holder.std_job_name.setText(itemList.get(position).getJob_name());
        holder.std_job_salary.setText(itemList.get(position).getJob_salary());
        holder.std_job_location.setText(itemList.get(position).getJob_location());
        holder.std_job_desc.setText(itemList.get(position).getJob_desc());
        holder.std_job_cv.setText(itemList.get(position).getName());
        if("Accepted".equals(itemList.get(position).getApplication_status()) || "Rejected".equals(itemList.get(position).getApplication_status())){
            holder.accept_btn.setVisibility(View.GONE);
            holder.reject_btn.setVisibility(View.GONE);
            holder.spinner_layout.setVisibility(View.VISIBLE);
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
                        holder.spinner_layout.setVisibility(View.GONE);
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
                            testNamesList.add(test_name);
                            holder.test_list.setSelected(true);
                            holder.test_list.setSelection(testNamesList.indexOf(0));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    // Create an ArrayAdapter using the string array and a default Spinner layout
                    adapter = new ArrayAdapter<>(context.getApplicationContext(), android.R.layout.simple_spinner_item, testNamesList);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Set the adapter to the Spinner
                    holder.test_list.setAdapter(adapter);
                    holder.test_list.setSelection(0);
                }
                testNamesList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Set Click Listner on test list
        holder.test_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Perform action when an item is selected
                String selectedTestName = testNamesList.get(position);
                Log.d("TAG", "Selected Test Name: " + selectedTestName); // Log selected test name for debugging
                if (holder.spinner_text != null) {
                    holder.spinner_text.setText(selectedTestName);
                    Log.d("TAG", "Text set on holder.spinner_text: " + selectedTestName); // Log text set on spinner_text for debugging
                } else {
                    Log.e("TAG", "holder.spinner_text is null"); // Log if spinner_text is null for debugging
                }
                Toast.makeText(context.getApplicationContext(), "Selected Test: " + selectedTestName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
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
                        holder.spinner_layout.setVisibility(View.VISIBLE);
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
