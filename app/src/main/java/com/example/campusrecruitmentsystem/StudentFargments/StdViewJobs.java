package com.example.campusrecruitmentsystem.StudentFargments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.example.campusrecruitmentsystem.Adapters.student_joblist_adapter;
import com.example.campusrecruitmentsystem.Models.post_job_model;
import com.example.campusrecruitmentsystem.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StdViewJobs extends Fragment {

    FirebaseAuth fauth;
    String key;
    ScrollView scroll_view_33;
    student_joblist_adapter adapter;
    ArrayList<post_job_model> list;
    Button post_job;
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    DatabaseReference reference, reference1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fauth = FirebaseAuth.getInstance();
//        drawerLayout = findViewById(R.id.drawer_layout);
        list = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_student_jobs_list, container, false);
        androidx.appcompat.widget.SearchView search_view_std = rootView.findViewById(R.id.search_view_std);
        RecyclerView recyclerView = rootView.findViewById(R.id.std_jobs_list_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search_view_std.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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
        return rootView;
    }
    void getData(RecyclerView recyclerView){
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
                    adapter = new student_joblist_adapter(getContext(),list);
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