package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.shopfinity.ProfileActivity;
import com.example.shopfinity.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private ImageView drawerIcon, userIcon, micIcon;
    private EditText searchEditText;
    private TextView greetingTextView;
    private FirebaseAuth auth;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Views
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout);
        NavigationView navigationView = requireActivity().findViewById(R.id.navigationView);
        drawerIcon = view.findViewById(R.id.drawerIcon);
        userIcon = view.findViewById(R.id.userIcon);
        searchEditText = view.findViewById(R.id.searchEditText);
        micIcon = view.findViewById(R.id.micIcon);
        greetingTextView = view.findViewById(R.id.greetingText);
        auth = FirebaseAuth.getInstance();

        // Display User Greeting
        displayGreeting();

        // Handle Drawer Icon Click (Opens Navigation Drawer)
        drawerIcon.setOnClickListener(v -> {
            if (drawerLayout != null) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        // Handle User Icon Click (Navigates to ProfileActivity)
        userIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });

        // Handle Mic Icon Click (TODO: Implement voice search functionality)
        micIcon.setOnClickListener(v -> {
            // Placeholder: Implement Speech Recognition Here
        });

        // Implement Search Functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call method to filter RecyclerView based on search query
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    // Display Greeting with User's Name and Time of Day
    private void displayGreeting() {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName(); // Get user's name from Firebase Auth
            if (name == null || name.isEmpty()) {
                name = "User"; // Fallback if name is null
            }

            // Get the current hour
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            String greeting;

            if (hour >= 5 && hour < 12) {
                greeting = "Good Morning, ";
            } else if (hour >= 12 && hour < 17) {
                greeting = "Good Afternoon, ";
            } else if (hour >= 17 && hour < 21) {
                greeting = "Good Evening, ";
            } else {
                greeting = "Good Night, ";
            }

            // Combine greeting and name
            SpannableString spannableGreeting = new SpannableString(greeting + name);

            // Set a different color for the name
            int nameColor = ContextCompat.getColor(requireContext(), R.color.blue); // Change to any color
            spannableGreeting.setSpan(new ForegroundColorSpan(nameColor),
                    greeting.length(),
                    (greeting.length() + name.length()),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set the text to TextView
            greetingTextView.setText(spannableGreeting);
        }
    }

    // Method to perform search (implement Firestore filtering here)
    private void performSearch(String query) {
        // TODO: Implement Firestore query to fetch and update RecyclerView
    }
}
