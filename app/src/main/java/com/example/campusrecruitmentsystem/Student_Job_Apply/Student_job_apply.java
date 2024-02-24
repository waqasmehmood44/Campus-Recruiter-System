package com.example.campusrecruitmentsystem.Student_Job_Apply;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campusrecruitmentsystem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Student_job_apply extends AppCompatActivity {

    Button upload_docs, app_submit;
    EditText etstd_name, etemail11,etLocation11;
    Uri path;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_job_apply);
        upload_docs = findViewById(R.id.upload_docs);
        etstd_name = findViewById(R.id.etstd_name);
        etemail11 = findViewById(R.id.etemail11);
        etLocation11 = findViewById(R.id.etLocation11);
        app_submit= findViewById(R.id.app_submit);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("StudentsCV");

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
                StorageReference reference  = storageReference.child("StudentsCV/"+System.currentTimeMillis()+".pdf");
                reference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri url = uriTask.getResult();


//                        pdfClass pdfClass = new pdfClass(etstd_name.getText().toString(),url.toString());
//                        databaseReference.child(databaseReference.push().getKey()).setValue(pdfClass);

                        Toast.makeText(Student_job_apply.this, "File Uploaded!", Toast.LENGTH_SHORT).show();
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
//            StorageReference reference  = storageReference.child("StudentsCV/"+System.currentTimeMillis()+".pdf");
//            reference.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                    while(!uriTask.isComplete());
//                    Uri url = uriTask.getResult();
//
//
//                    pdfClass pdfClass = new pdfClass(etstd_name.getText().toString(),url.toString());
//                    databaseReference.child(databaseReference.push().getKey()).setValue(pdfClass);
//
//                    Toast.makeText(Student_job_apply.this, "File Uploaded!", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                    double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
//                }
//            });
        }
    }
}