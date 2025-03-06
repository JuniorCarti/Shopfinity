package com.example.shopfinity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        // Initialize Views
        TextView appName = findViewById(R.id.appName);
        TextView appDescription = findViewById(R.id.appDescription);

        // Load Animations
        Animation rotateSmooth = AnimationUtils.loadAnimation(this, R.anim.rotate_smooth);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Apply Smooth Rotation First
        appName.startAnimation(rotateSmooth);

        // Delay fade-in effect to start after rotation finishes
        new Handler().postDelayed(() -> {
            appName.startAnimation(fadeIn);
            appDescription.startAnimation(fadeIn);
        }, 4000); // Start fade-in after 4 seconds

        // Ensure text is visible after fade-in
        new Handler().postDelayed(() -> {
            appName.setAlpha(1f);
            appDescription.setAlpha(1f);
        }, 4500); // Small delay to allow fade-in to complete

        // Transition to MainActivity after 6 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish(); // Close Splash Activity
        }, 6000); // 6 seconds total duration
    }
}
