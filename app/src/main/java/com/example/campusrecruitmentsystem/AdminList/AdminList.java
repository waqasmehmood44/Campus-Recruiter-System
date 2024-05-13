package com.example.campusrecruitmentsystem.AdminList;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.Adapters.student_joblist_adapter;
import com.example.campusrecruitmentsystem.Models.User_Model;
import com.example.campusrecruitmentsystem.Models.post_job_model;
import com.example.campusrecruitmentsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminList extends AppCompatActivity {
    DatabaseReference reference, reference1;
    admin_list_adapter adapter;
    ArrayList<User_Model> list;
    FirebaseAuth fauth;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);
        fauth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.admin_list_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Personal Info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(AdminList.this,"2", Toast.LENGTH_SHORT).show();
                if(snapshot.exists()){
                    // Add Text on Screen if DataSnapshot is not available
                }
                for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                    key = appSnapshot.getKey();
                    list.clear();
                    reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Personal Info").child(key);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User_Model jobs_list = snapshot.getValue(User_Model.class);
                            list.add(jobs_list);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    adapter = new admin_list_adapter(getApplicationContext(),list);
                    recyclerView.setAdapter(adapter);
                }
                list.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}