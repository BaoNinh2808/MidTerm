package com.example.movieticketbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.movieticketbooking.R;

public class IntroActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // Delay in milliseconds (3 second)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Delayed 1 second and then transition to Login Activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Go to login activity
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the IntroActivity so the user can't go back to it using the back button
            }
        }, SPLASH_DELAY);
    }
}