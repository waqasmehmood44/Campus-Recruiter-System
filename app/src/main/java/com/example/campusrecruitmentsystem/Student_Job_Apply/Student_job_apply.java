package com.example.campusrecruitmentsystem.Student_Job_Apply;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.StudentJobsList;
import com.example.campusrecruitmentsystem.Models.Submit_Application;
import com.example.campusrecruitmentsystem.Models.post_job_model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Student_job_apply extends AppCompatActivity {

    Button upload_docs, app_submit;
    EditText etstd_name;
    Uri path;
    TextView success_msg;
    ImageView success_icon;
    String student_name;

    String user_email;
    StorageReference storageReference;
    DatabaseReference databaseReference, databaseReference2,reference;
    String rec_id, job_id;
    post_job_model job_info;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_job_apply);
        upload_docs = findViewById(R.id.upload_docs);
        etstd_name = findViewById(R.id.etstd_name);
        app_submit= findViewById(R.id.app_submit);
        success_msg = findViewById(R.id.success_msg);
        success_icon = findViewById(R.id.success_icon);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Student Applications");
        // Retrieve data from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            rec_id = intent.getStringExtra("rec_id");
            job_id = intent.getStringExtra("job_id");
//            Toast.makeText(this, rec_id, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, job_id, Toast.LENGTH_SHORT).show();
        }
        reference = FirebaseDatabase.getInstance().getReference().child("Jobs").child((rec_id)).child(job_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                job_info = snapshot.getValue(post_job_model.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        upload_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select PDF Files..."), 1);
            }
        });
        app_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    user_email = currentUser.getEmail();
                }
                DatabaseReference studentInfoRef = FirebaseDatabase.getInstance().getReference("Users").child("Personal Info")
                        .child(currentUser.getUid());
                studentInfoRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();

                        // Extract the test name from the HashMap
                        student_name = (String) data.get("name");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                StorageReference reference  = storageReference.child("Student Applications/"+System.currentTimeMillis()+".pdf");
                reference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri url = uriTask.getResult();

                        Submit_Application submit_Application = new Submit_Application(etstd_name.getText().toString(),url.toString(), rec_id, job_id, FirebaseAuth.getInstance().getUid(), job_info.getJob(),job_info.getSalary(),
                                job_info.getLocation(),job_info.getDescription(), "New", "","","","","","","",user_email,student_name);
                        databaseReference2 = FirebaseDatabase.getInstance().getReference("Recruiter Job Applications")
                                .child(rec_id).child(job_id);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).child(job_id).setValue(submit_Application);
                        databaseReference2.setValue(submit_Application);
                        Intent intent = new Intent(Student_job_apply.this, StudentJobsList.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Student_job_apply.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            path = data.getData();
            success_msg.setVisibility(View.VISIBLE);
            success_icon.setVisibility(View.VISIBLE);
        }
    }
}