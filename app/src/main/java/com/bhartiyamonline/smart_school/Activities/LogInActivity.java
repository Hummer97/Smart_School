package com.example.smart_school.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smart_school.R;
import com.google.android.material.button.MaterialButton;

public class LogInActivity extends AppCompatActivity {
    MaterialButton logIn_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn_btn = findViewById(R.id.logIn_btn);
        logIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}