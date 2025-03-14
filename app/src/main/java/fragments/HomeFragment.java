package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopfinity.MainActivity;
import com.example.shopfinity.ProfileActivity;
import com.example.shopfinity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.ProductAdapter;
import models.Product;

public class HomeFragment extends Fragment {

    private ImageView drawerIcon, userIcon, micIcon;
    private EditText searchEditText;
    private TextView greetingTextView;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private FirebaseAuth auth;
    private FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize Views
        drawerIcon = view.findViewById(R.id.drawerIcon);
        userIcon = view.findViewById(R.id.userIcon);
        searchEditText = view.findViewById(R.id.searchEditText);
        micIcon = view.findViewById(R.id.micIcon);
        greetingTextView = view.findViewById(R.id.greetingText);
        recyclerView = view.findViewById(R.id.recyclerView);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Display User Greeting
        displayGreeting();

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(getContext(), productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onWishlistClick(Product product) {
                Toast.makeText(getContext(), "Added to Wishlist: " + product.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddToCartClick(Product product) {
                Toast.makeText(getContext(), "Added to Cart: " + product.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(productAdapter);

        // Load Products from Firestore
        loadProducts();

        // Handle Drawer Icon Click
        drawerIcon.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        // Handle User Icon Click (Navigates to ProfileActivity)
        userIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProfileActivity.class)));

        // Handle Mic Icon Click (TODO: Implement voice search functionality)
        micIcon.setOnClickListener(v -> {
            // Placeholder: Implement Speech Recognition Here
            Toast.makeText(getContext(), "Voice search coming soon!", Toast.LENGTH_SHORT).show();
        });

