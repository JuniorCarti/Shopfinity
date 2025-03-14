package com.example.shopfinity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import fragments.HomeFragment;
import fragments.WishlistFragment;
import fragments.CartFragment;
import fragments.SearchFragment;
import fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle window insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        // Set up Navigation Drawer Toggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Handle Navigation Drawer Item Clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_wishlist) {
                selectedFragment = new WishlistFragment();
            } else if (itemId == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            drawerLayout.closeDrawers(); // Close drawer after selection
            return true;
        });

        // Set up Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_wishlist) {
                selectedFragment = new WishlistFragment();
            } else if (itemId == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });

        // Load the default fragment (HomeFragment)
        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.nav_home); // Set Home as the default selected item
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void openDrawer() {

    }
}
