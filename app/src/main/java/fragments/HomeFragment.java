package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.shopfinity.ProfileActivity;
import com.example.shopfinity.R;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private ImageView drawerIcon, userIcon, micIcon;
    private EditText searchEditText;

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

    // Method to perform search (implement Firestore filtering here)
    private void performSearch(String query) {
        // TODO: Implement Firestore query to fetch and update RecyclerView
    }
}

