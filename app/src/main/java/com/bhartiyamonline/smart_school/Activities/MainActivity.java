package com.bhartiyamonline.smart_school.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bhartiyamonline.smart_school.Fragments.AddStudentFragment;
import com.bhartiyamonline.smart_school.Fragments.AddTeacherFragment;
import com.bhartiyamonline.smart_school.Fragments.ClassViewFragment;
import com.bhartiyamonline.smart_school.Fragments.DashBoardFragment;
import com.bhartiyamonline.smart_school.Fragments.SectionVewFragment;
import com.bhartiyamonline.smart_school.Fragments.SmsFragment;
import com.bhartiyamonline.smart_school.Fragments.ViewStudentListFragment;
import com.bhartiyamonline.smart_school.Fragments.ViewTeacherListFragment;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ConstraintLayout mButtonGroup;
    private Toolbar toolbar,mSecondToolbar;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);

        if(!sharedPrefManager.isLoggedIn()){
            Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
            startActivity(intent);
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DashBoardFragment()).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
            finishAffinity();
        }
    }
    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DashBoardFragment()).commit();
                break;
            case R.id.nav_add_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddStudentFragment()).commit();
                break;
            case R.id.nav_view_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ViewStudentListFragment()).commit();
                break;
            case R.id.nav_add_teacher:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddTeacherFragment()).commit();
                break;
            case R.id.nav_view_teacher:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ViewTeacherListFragment()).commit();
                break;

            case R.id.nav_class_add_view:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ClassViewFragment()).commit();
                break;

            case R.id.nav_section_add_view:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SectionVewFragment()).commit();
                break;
            case R.id.nav_sms_view:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SmsFragment()).commit();

            break;
            case R.id.nav_logOut:
                //showMessage("Signing out");
                sharedPrefManager.logout();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}