package com.example.campusrecruitmentsystem.RecruiterFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campusrecruitmentsystem.Adapters.std_job_app_adapter;
import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.Models.Submit_Application;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Recruiter_std_Job_Applications extends Fragment {

    FirebaseAuth fauth;
    String key;
    std_job_app_adapter adapter;
    ArrayList<Submit_Application> list;
    DatabaseReference reference, reference1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fauth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_recruiter_std__job__applications, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rec_std_job_applications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reference = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                }

                for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                    key = appSnapshot.getKey();

                    list.clear();
                    reference1 = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(FirebaseAuth.getInstance().getUid()).child(key);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot sp_snapshot) {
                            Submit_Application submit_Application = sp_snapshot.getValue(Submit_Application.class);
                            list.add(submit_Application);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    adapter = new std_job_app_adapter(getContext(),list,false);
                    recyclerView.setAdapter(adapter);
                }
                list.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootView;
    }
}