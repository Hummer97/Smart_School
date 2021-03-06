package com.bhartiyamonline.smart_school.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bhartiyamonline.smart_school.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Take instance of Action Bar
        // using getSupportActionBar and
        // if it is not Null
        // then call hide function
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        },SPLASH_DISPLAY_LENGTH);

    }
}