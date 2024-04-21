package com.example.campusrecruitmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.campusrecruitmentsystem.StudentFargments.StudentJobsListFrag;
import com.example.campusrecruitmentsystem.StudentFargments.Student_Job_Applications;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class StudentJobsList extends AppCompatActivity {
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    DatabaseReference reference, reference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_jobs_list);
        drawerLayout = findViewById(R.id.drawer_layout);
        materialToolbar = findViewById(R.id.material_toolbar);
        navigationView = findViewById(R.id.nav_view);

        replaceFragment(new StudentJobsListFrag());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(StudentJobsList.this, drawerLayout,materialToolbar,
        R.string.drawer_close, R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.jobs_list){
                    replaceFragment(new StudentJobsListFrag());
                    drawerLayout.closeDrawers();
                    return true;

                } else if(item.getItemId() == R.id.applications_list){
                    replaceFragment(new Student_Job_Applications());
                    drawerLayout.closeDrawers();
                    return true;
                }  if(item.getItemId() == R.id.logout_2){
                    Toast.makeText(StudentJobsList.this, "Logout", Toast.LENGTH_SHORT).show();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent intent = new Intent(StudentJobsList.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}