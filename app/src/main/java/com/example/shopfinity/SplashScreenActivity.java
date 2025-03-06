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
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Apply Rotation First
        appName.startAnimation(rotate);

        // Delay the fade-in effect after rotation
        new Handler().postDelayed(() -> {
            appName.startAnimation(fadeIn);
            appDescription.startAnimation(fadeIn);
        }, 2000); // Start fade-in after rotation completes

        // Ensure text is visible
        appName.setAlpha(1f);
        appDescription.setAlpha(1f);

        // Delay and move to WelcomeActivity after 3.5 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish(); // Close Splash Activity
        }, 3500);
    }
}
