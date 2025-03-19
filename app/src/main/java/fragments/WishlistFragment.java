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


