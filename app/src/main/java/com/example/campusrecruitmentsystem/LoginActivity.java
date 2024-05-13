package com.example.campusrecruitmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusrecruitmentsystem.AdminList.AdminList;
import com.example.campusrecruitmentsystem.Models.User_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText etloginEmail, etloginPass;
    TextView not_have_account,forgot_password;
    FirebaseAuth mAuth;
    Button login_btn;
    ImageView contact_us;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        etloginEmail = findViewById(R.id.etlogin_Email);
        contact_us = findViewById(R.id.contact_us);
        forgot_password = findViewById(R.id.forgot_password);
        etloginPass = findViewById(R.id.etloginPass);
        login_btn = findViewById(R.id.login_btn);
        not_have_account = findViewById(R.id.not_have_account);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the FirebaseAuth instance
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                // Get the user's email address from the input field (assuming editTextEmail is your EditText)
                String email = etloginEmail.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Send a password reset email to the user
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Password reset email sent successfully
                                    Toast.makeText(LoginActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Failed to send password reset email
                                    Toast.makeText(LoginActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        //Login Button
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(etloginEmail.getText());
                password = String.valueOf(etloginPass.getText());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        if("admin@admin.com".equals(email) && "Admin123*".equals(password)){
                                            Intent intent = new Intent(LoginActivity.this, AdminList.class);
                                            startActivity(intent);
                                            return;}
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        firebaseDatabase.getReference().child("Users").child("Personal Info")
                                                .child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        try {
                                                            User_Model users = snapshot.getValue(User_Model.class);
                                                            if (users.getUser_type().equals("Student")) {
                                                                Toast.makeText(LoginActivity.this, "Student", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(LoginActivity.this, StdHomeScreen.class);
                                                                startActivity(intent);
                                                            } else if(users.getUser_type().equals("Recruiter")){
                                                                Toast.makeText(LoginActivity.this, "Recruiter", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(LoginActivity.this, RecHomeScreen.class);
                                                                startActivity(intent);
                                                            } else {
                                                                Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } catch (Exception e) {
                                                            Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                    }
                                                });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


            }
        });
        not_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterationFormActivity.class);
                startActivity(intent);
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // define Intent object with action attribute as ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                // add three fields to intent using putExtra function
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"asadu410@gmail.com"});
                // set type of intent
                intent.setType("message/rfc822");

                // startActivity with intent with chooser as Email client using createChooser function
                startActivity(intent);
            }
        });
    }
}