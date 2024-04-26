package com.example.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.campusrecruitmentsystem.Models.User_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class StudentFormActivity extends AppCompatActivity {
    TextInputEditText tilName, tilEmail,  tilContactNo, tilAddress, tilDepartment, signUp_pass;
    AppCompatButton signUp_btn;
    AutoCompleteTextView userType111;
    FirebaseAuth mAuth;
    String[] user_type = {"Student", "Recruiter"};
    TextView already_have_account;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        tilName = findViewById(R.id.etName);
        tilEmail = findViewById(R.id.etEmail);
        tilContactNo = findViewById(R.id.etContactNo);
        tilAddress = findViewById(R.id.etAddress);
        tilDepartment = findViewById(R.id.etDepartment);
        signUp_pass = findViewById(R.id.etsignUp_pass);
        signUp_btn = findViewById(R.id.signUp_btn);
        already_have_account = findViewById(R.id.already_have_account);
        userType111 = findViewById(R.id.userType__reg);
        //Spinner Code****************************************
        String[] user_type = {"Student", "Recruiter"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, user_type);
        userType111.setAdapter(arrayAdapter);
        userType111.setInputType(0);

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(tilEmail.getText());
                password = String.valueOf(signUp_pass.getText());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(StudentFormActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(StudentFormActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(StudentFormActivity.this, "Password must be greater then 6 numbers", Toast.LENGTH_SHORT).show();
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String name, contact_no, address, department, user_type, user_email;
                                    name = String.valueOf(tilName.getText());
                                    contact_no = String.valueOf(tilContactNo.getText());
                                    address = String.valueOf(tilAddress.getText());
                                    department = String.valueOf(tilDepartment.getText());
                                    user_type = String.valueOf(userType111.getText());
                                    user_email = String.valueOf(tilEmail.getText());
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(StudentFormActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                                    reference=FirebaseDatabase.getInstance().getReference().child("Users").child("Personal Info")
                                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
                                    User_Model user_model = new User_Model(name, contact_no, address, department,user_type, FirebaseAuth.getInstance().getCurrentUser().getUid(),user_email);
                                    reference.setValue(user_model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(StudentFormActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });


                                } else {
                                    Toast.makeText(StudentFormActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                });
            }
        });
        already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentFormActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        userType111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType111.showDropDown();
            }
        });
    }
}