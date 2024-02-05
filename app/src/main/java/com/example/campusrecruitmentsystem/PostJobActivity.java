package com.example.campusrecruitmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class PostJobActivity extends AppCompatActivity {
    TextInputEditText etJobTitle, etSalary, etEligibility, etLocation, etJobDescription;
    Button post_job;
    DatabaseReference reference, reference1;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        etJobTitle = findViewById(R.id.etJobTitle);
        etSalary = findViewById(R.id.etSalary);
        etEligibility = findViewById(R.id.etEligibility);
        etLocation = findViewById(R.id.etLocation);
        etJobDescription = findViewById(R.id.etJobDescription);
        post_job = findViewById(R.id.post_job);


        post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String job, salary, eligibility, location, description;
                job = String.valueOf(etJobTitle.getText());
                salary = String.valueOf(etSalary.getText());
                eligibility = String.valueOf(etEligibility.getText());
                location = String.valueOf(etLocation.getText());
                description = String.valueOf(etJobDescription.getText());



                reference=FirebaseDatabase.getInstance().getReference().child("Jobs")
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).push();
                String key = reference.getKey();
                post_job_model post_job_model = new post_job_model(job, salary, eligibility, location,description, FirebaseAuth.getInstance().getCurrentUser().getUid(),key);
                reference1=FirebaseDatabase.getInstance().getReference().child("Student Jobs List").child(key);

                reference.setValue(post_job_model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        reference1.setValue(post_job_model);
                        Toast.makeText(PostJobActivity.this, "Job Posted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostJobActivity.this, RecruiterJobsList.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}