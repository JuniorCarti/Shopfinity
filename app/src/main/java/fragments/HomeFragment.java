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
