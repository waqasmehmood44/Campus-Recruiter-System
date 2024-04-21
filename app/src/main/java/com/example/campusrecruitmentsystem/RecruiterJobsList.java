package com.example.campusrecruitmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.campusrecruitmentsystem.RecruiterFragments.RecruiterJobsListFrag;
import com.example.campusrecruitmentsystem.RecruiterFragments.Recruiter_std_Job_Applications;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class RecruiterJobsList extends AppCompatActivity {
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_jobs_list);
        drawerLayout = findViewById(R.id.drawer_layout1);
        materialToolbar = findViewById(R.id.material_toolbar);
        navigationView = findViewById(R.id.nav_view_1);
        replaceFragment(new RecruiterJobsListFrag());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(RecruiterJobsList.this, drawerLayout,materialToolbar,
        R.string.drawer_close, R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.jobs_list_rec){
                    replaceFragment(new RecruiterJobsListFrag());
                    drawerLayout.closeDrawers();
                    return true;

                } if(item.getItemId() == R.id.rec_job_applications){
                    replaceFragment(new Recruiter_std_Job_Applications());
                    drawerLayout.closeDrawers();
                    return true;
                } if(item.getItemId() == R.id.logout_1){
                    Toast.makeText(RecruiterJobsList.this, "Logout", Toast.LENGTH_SHORT).show();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent intent = new Intent(RecruiterJobsList.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout1, fragment);
        fragmentTransaction.commit();
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }



}