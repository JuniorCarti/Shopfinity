package com.example.shopfinity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Find Views
        LottieAnimationView animationView = findViewById(R.id.lottieAnimation);
        TextView appName = findViewById(R.id.appName);
        TextView appDescription = findViewById(R.id.appDescription);

        // Play Lottie Animation
        animationView.playAnimation();

        // Fade-in effect for app name
        new Handler().postDelayed(() -> {
            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setDuration(1500);
            appName.startAnimation(fadeIn);
            appName.setAlpha(1);
        }, 2000); // Start after 2 seconds

        // Fade-in effect for app description (after app name)
        new Handler().postDelayed(() -> {
            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setDuration(1500);
            appDescription.startAnimation(fadeIn);
            appDescription.setAlpha(1);
        }, 3500); // Start after 3.5 seconds

        // Move to MainActivity after 8 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, OnboardingActivity.class));
            finish();
        }, 8000);
    }
}
