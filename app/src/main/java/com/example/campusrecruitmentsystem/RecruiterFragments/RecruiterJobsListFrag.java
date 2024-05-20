package com.example.campusrecruitmentsystem.RecruiterFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.campusrecruitmentsystem.Adapters.jobs_list_view_adapter;
import com.example.campusrecruitmentsystem.Models.post_job_model;
import com.example.campusrecruitmentsystem.RecPostJob;
import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.TestCreation.RecCreateTest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecruiterJobsListFrag extends Fragment {
    FirebaseAuth fauth;
    String key;
    ScrollView scroll_view_33;
    jobs_list_view_adapter adapter;
    ArrayList<post_job_model> list;
    AppCompatButton post_job,create_test;
    DatabaseReference reference, reference1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recruiter_jobs_list, container, false);

        fauth = FirebaseAuth.getInstance();
        post_job = rootView.findViewById(R.id.post_job);
        create_test = rootView.findViewById(R.id.create_test);
        androidx.appcompat.widget.SearchView searchView = rootView.findViewById(R.id.search_view);

        list = new ArrayList<>();
        RecyclerView recyclerView = rootView.findViewById(R.id.rec_jobs_list_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    getData(recyclerView);
                }
                adapter.getFilter().filter(newText);
                return true;
            }


        });
        getData(recyclerView);
        post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecPostJob.class);
                startActivity(intent);
            }
        });
        create_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecCreateTest.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    void getData(RecyclerView recyclerView){
        reference = FirebaseDatabase.getInstance().getReference().child("Jobs")
                .child(fauth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    // Add Text on Screen if DataSnapshot is not available
                }

                for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                    key = appSnapshot.getKey();

                    list.clear();
                    reference1 = FirebaseDatabase.getInstance().getReference().child("Jobs")
                            .child(fauth.getCurrentUser().getUid()).child(key);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    adapter = new jobs_list_view_adapter(getContext(),list);
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