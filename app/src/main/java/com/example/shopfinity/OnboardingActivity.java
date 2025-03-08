package com.example.shopfinity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

import fragments.OnboardingFragment;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private LinearLayout sliderDots;
    private TextView skipButton;
    private Button nextButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Initialize views
        viewPager = findViewById(R.id.viewPager);
        sliderDots = findViewById(R.id.sliderDots);
        skipButton = findViewById(R.id.skipButton);
        nextButton = findViewById(R.id.nextButton);

        // Set up ViewPager adapter
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OnboardingFragment.newInstance(0)); // First screen
        fragments.add(OnboardingFragment.newInstance(1)); // Second screen
        fragments.add(OnboardingFragment.newInstance(2)); // Third screen

        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return fragments.size();
            }

            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }
        };
        viewPager.setAdapter(adapter);

        // Set up slider dots
        setupSliderDots();

        // Handle skip button click
        skipButton.setOnClickListener(v -> navigateToSignUpActivity());

        // Handle next button click
        nextButton.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < fragments.size() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1); // Go to next screen
            } else {
                navigateToSignUpActivity(); // Go to SignUpActivity after last screen
            }
        });

        // Update slider dots and button text when page changes
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateSliderDots(position); // Update slider dots
                nextButton.setText(position == fragments.size() - 1 ? "Get Started" : "Next"); // Update button text
            }
        });
    }

    private void setupSliderDots() {
        ImageView[] dots = new ImageView[3];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(i == 0 ? R.drawable.dot_selected : R.drawable.dot_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            sliderDots.addView(dots[i], params);
        }
    }

    private void updateSliderDots(int position) {
        for (int i = 0; i < sliderDots.getChildCount(); i++) {
            ImageView imageView = (ImageView) sliderDots.getChildAt(i);
            imageView.setImageResource(i == position ? R.drawable.dot_selected : R.drawable.dot_unselected);
        }
    }

    private void navigateToSignUpActivity() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }
}