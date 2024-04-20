package com.example.campusrecruitmentsystem.StudentTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusrecruitmentsystem.Adapters.jobs_list_view_adapter;
import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.StudentJobsList;
import com.example.campusrecruitmentsystem.Student_Job_Apply.Student_job_apply;
import com.example.campusrecruitmentsystem.post_job_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class student_test extends AppCompatActivity {
    DatabaseReference reference, reference1,test_questions_ref,update_ans_ref_std, update_ans_ref_rec;
    String test_name,key;
    TextView test_name11;
    int totalQuestions = 0;
    ArrayList<student_test_model> list;
    student_test_adapter adapter;
    AppCompatButton save_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_test);
        // In ActivityB
        Intent intent = getIntent();
        String Test_id = intent.getStringExtra("Test_id");
        String recruiter_id = intent.getStringExtra("recruiter_id");
        String student_id = intent.getStringExtra("student_id");
        String job_Id = intent.getStringExtra("job_Id");
        test_name11 = findViewById(R.id.test_name11);
        save_test = findViewById(R.id.save_test);
        list = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.student_test_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference().child("Applicants Test Names").child(recruiter_id).child(Test_id);
        reference1 = FirebaseDatabase.getInstance().getReference().child("Applicants Test Questions").child(recruiter_id).child(Test_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                test_name  = (String) data.get("test_name");
                test_name11.setText(test_name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalQuestions = (int) snapshot.getChildrenCount();

                for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                    key = appSnapshot.getKey();
                    list.clear();
                    test_questions_ref = FirebaseDatabase.getInstance().getReference().child("Applicants Test Questions")
                            .child(recruiter_id).child(Test_id).child(key);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    test_questions_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            student_test_model  test_model = snapshot.getValue(student_test_model.class);
                            list.add(test_model);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    adapter = new student_test_adapter(getApplicationContext(),list);
                    recyclerView.setAdapter(adapter);
                }
                list.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        save_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String questionsCount = String.valueOf(adapter.getQuestionsCount());
            String ansCount = String.valueOf(adapter.getAnsCount());
            update_ans_ref_std = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(student_id).child(job_Id);
            update_ans_ref_rec = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(recruiter_id).child(job_Id);
            Map<String, Object> updates = new HashMap<>();
            updates.put("correct_ans", ansCount);
            updates.put("totals_questions", questionsCount);
            update_ans_ref_std.updateChildren(updates);
            update_ans_ref_rec.updateChildren(updates);
                Intent intent = new Intent(student_test.this, StudentJobsList.class);
                startActivity(intent);
            }
        });
    }
}