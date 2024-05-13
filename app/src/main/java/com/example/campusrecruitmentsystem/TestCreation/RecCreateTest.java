package com.example.campusrecruitmentsystem.TestCreation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.RecHomeScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RecCreateTest extends AppCompatActivity {
    EditText test_name,question_text,option_1,option_2,option_3,correct_ans;
    AppCompatButton save_test, add_question;
    DatabaseReference reference, reference1;
    String testid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test_questions);
        test_name  = findViewById(R.id.test_name);
        question_text = findViewById(R.id.question_text);
        option_1 = findViewById(R.id.option_1);
        option_2 = findViewById(R.id.option_2);
        option_3 = findViewById(R.id.option_3);
        correct_ans = findViewById(R.id.correct_ans);
        save_test = findViewById(R.id.save_test);
        add_question = findViewById(R.id.add_question);
        save_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecHomeScreen.class);
                startActivity(intent);
            }
        });
        add_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Applicants Test Names").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
                Map<String, Object> testDetails = new HashMap<>();
                testDetails.put("test_name", test_name.getText().toString());


                Map<String, Object> questionDetails = new HashMap<>();
                questionDetails.put("question_text", question_text.getText().toString());
                questionDetails.put("option_1", option_1.getText().toString());
                questionDetails.put("option_2", option_2.getText().toString());
                questionDetails.put("option_3", option_3.getText().toString());
                questionDetails.put("correct_ans", correct_ans.getText().toString()); // Index of the correct answer
                if (testid == null){
                    reference.setValue(testDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Data saved successfully
                                    testid = reference.getKey(); // Get the ID of the newly created test
                                    testDetails.put("test_id", testid);
                                    reference.setValue(testDetails);
                                    Toast.makeText(RecCreateTest.this, "Question Added Successfully", Toast.LENGTH_SHORT).show();
                                    question_text.setText("");
                                    option_1.setText("");
                                    option_2.setText("");
                                    option_3.setText("");
                                    correct_ans.setText("");
                                    reference1 = FirebaseDatabase.getInstance().getReference().child("Applicants Test Questions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(testid).push();
                                    reference1.setValue(questionDetails);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error saving data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    reference1 = FirebaseDatabase.getInstance().getReference().child("Applicants Test Questions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(testid).push();
                    reference1.setValue(questionDetails);
                    Toast.makeText(RecCreateTest.this, "Question Added Successfully", Toast.LENGTH_SHORT).show();
                    question_text.setText("");
                    option_1.setText("");
                    option_2.setText("");
                    option_3.setText("");
                    correct_ans.setText("");
                }
            }
        });
    }
}