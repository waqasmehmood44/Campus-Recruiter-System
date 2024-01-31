package com.example.campusrecruitmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.campusrecruitmentsystem.Adapters.jobs_list_view_adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentJobsList extends AppCompatActivity {
    FirebaseAuth fauth;
    String key;
    ScrollView scroll_view_33;
    jobs_list_view_adapter adapter;
    ArrayList<post_job_model> list;
    Button post_job;
    DatabaseReference reference, reference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_jobs_list);
        fauth = FirebaseAuth.getInstance();

        list = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.std_jobs_list_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("Student Jobs List");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    // Add Text on Screen if DataSnapshot is not available
                }

                for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                    key = appSnapshot.getKey();

                    list.clear();
                    reference1 = FirebaseDatabase.getInstance().getReference().child("Student Jobs List").child(key);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot sp_snapshot) {
                            post_job_model jobs_list = sp_snapshot.getValue(post_job_model.class);
                            list.add(jobs_list);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    adapter = new jobs_list_view_adapter(getApplicationContext(),list);
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