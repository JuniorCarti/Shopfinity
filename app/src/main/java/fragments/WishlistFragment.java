package fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.shopfinity.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import adapters.WishlistAdapter;
import models.WishlistItem;


public class WishlistFragment extends Fragment {

    private static final String TAG = "WishlistFragment";

    private ImageView backArrow, cartIcon;
    private Button shopNowBtn;
    private RecyclerView recyclerWishlist;
    private LinearLayout emptyWishlistContainer;
    private ProgressBar wishlistProgressBar;
    private LottieAnimationView lottieHeartClick;

    private List<WishlistItem> wishlistItems;
    private WishlistAdapter wishlistAdapter;

    private FirebaseFirestore db;

    public WishlistFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);


        // Initialize Views
        backArrow = view.findViewById(R.id.backArrow);
        cartIcon = view.findViewById(R.id.cartIcon);
        shopNowBtn = view.findViewById(R.id.btnShopNow);
        recyclerWishlist = view.findViewById(R.id.recyclerWishlist);
        emptyWishlistContainer = view.findViewById(R.id.emptyWishlistContainer);
        wishlistProgressBar = view.findViewById(R.id.wishlistProgressBar);
        lottieHeartClick = view.findViewById(R.id.lottieHeartClick);

        db = FirebaseFirestore.getInstance();


        // Set Click Listeners
        backArrow.setOnClickListener(v -> navigateToHomeFragment());
        shopNowBtn.setOnClickListener(v -> navigateToHomeFragment());
        cartIcon.setOnClickListener(v -> showCartToast());


        // Initialize Wishlist RecyclerView
        recyclerWishlist.setLayoutManager(new LinearLayoutManager(getContext()));
        wishlistItems = new ArrayList<>();
        wishlistAdapter = new WishlistAdapter(getContext(), wishlistItems, new WishlistAdapter.OnWishlistItemClickListener() {
            @Override
            public void onItemClick(WishlistItem item) {
                // Handle item click (e.g., navigate to product details)
            }


            @Override
            public void onRemoveClick(WishlistItem item, int position) {
                removeFromWishlist(item, position);
            }

            @Override
            public void onAddToCartClick(WishlistItem item) {
                // Handle add to cart click
            }
        });
        recyclerWishlist.setAdapter(wishlistAdapter);

        // Fetch Wishlist Products
        fetchWishlistProducts();

        return view;
    }


    private void navigateToHomeFragment() {
        // Navigate to HomeFragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment()); // Replace with your HomeFragment
        transaction.addToBackStack(null); // Optional: Add to back stack
        transaction.commit();
    }

    private void showCartToast() {
        // Show a toast if the cart is empty
        Toast.makeText(getContext(), "Start shopping to add items to your cart", Toast.LENGTH_SHORT).show();
    }


    private void fetchWishlistProducts() {
        wishlistProgressBar.setVisibility(View.VISIBLE);
        CollectionReference wishlistRef = db.collection("Wishlist");

        wishlistRef.get().addOnCompleteListener(task -> {
            wishlistProgressBar.setVisibility(View.GONE);
            if (task.isSuccessful() && task.getResult() != null) {
                wishlistItems.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    WishlistItem item = document.toObject(WishlistItem.class);
                    if (item != null) {
                        item.setId(document.getId()); // Set Firestore document ID
                        wishlistItems.add(item);
                    }
                }
                wishlistAdapter.notifyDataSetChanged();
                updateUI();
            } else {
                Log.e(TAG, "Error fetching wishlist items", task.getException());
            }
        });
    }


    private void removeFromWishlist(WishlistItem item, int position) {
        db.collection("Wishlist").document(item.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    wishlistItems.remove(position);
                    wishlistAdapter.notifyItemRemoved(position);
                    updateUI();

                    // Play Lottie animation when an item is removed
                    if (wishlistItems.isEmpty()) {
                        lottieHeartClick.setVisibility(View.VISIBLE);
                        lottieHeartClick.playAnimation();
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error removing item", e));
    }


    private void updateUI() {
        if (wishlistItems.isEmpty()) {
            emptyWishlistContainer.setVisibility(View.VISIBLE);
            recyclerWishlist.setVisibility(View.GONE);

            // Play Lottie animation when the wishlist is empty
            lottieHeartClick.setVisibility(View.VISIBLE);
            lottieHeartClick.playAnimation();
        } else {
            emptyWishlistContainer.setVisibility(View.GONE);
            recyclerWishlist.setVisibility(View.VISIBLE);
            lottieHeartClick.setVisibility(View.GONE); // Hide Lottie animation
        }
    }
}






