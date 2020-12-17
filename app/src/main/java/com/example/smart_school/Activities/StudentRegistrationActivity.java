package com.example.smart_school.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smart_school.R;
import com.google.android.material.button.MaterialButton;

public class StudentRegistrationActivity extends AppCompatActivity {
    MaterialButton student_registration_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        student_registration_btn = findViewById(R.id.std_submit_btn);
        student_registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentRegistrationActivity.this,TeacherRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}