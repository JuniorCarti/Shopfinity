package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopfinity.ProductDetailsActivity;
import com.example.shopfinity.R;

import java.text.DecimalFormat;
import java.util.List;

import models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private List<Product> productList;
    private final OnProductClickListener listener;
    private final DecimalFormat priceFormat = new DecimalFormat("#,##0.00"); // Format prices

    public interface OnProductClickListener {
        void onWishlistClick(Product product);
        void onAddToCartClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Load product image (first image from list)
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            Glide.with(context)
                    .load(product.getImageUrls().get(0))
                    .placeholder(R.drawable.image_placeholder) // Placeholder while loading
                    .error(R.drawable.image_error) // Error image if fails
                    .into(holder.productImage);
        }
        // Bind product data to views
        holder.productName.setText(product.getName());
        holder.productBrand.setText(product.getBrand());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText("Ksh " + priceFormat.format(product.getPrice()));
        // Handle discount visibility
        if (product.getDiscountPrice() > 0 && product.getDiscountPrice() < product.getPrice()) {
            holder.productDiscountPrice.setText("Ksh " + priceFormat.format(product.getDiscountPrice()));
            holder.productDiscountPrice.setVisibility(View.VISIBLE);
            holder.productPrice.setPaintFlags(holder.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); // Strike-through original price
        } else {
            holder.productDiscountPrice.setVisibility(View.GONE);
            holder.productPrice.setPaintFlags(0);
        }

        // Bind rating and reviews
        holder.productRating.setText(String.format("%.1f ★ (%d)", product.getRating(), product.getReviews()));

        // Show bestseller badge if applicable
        if (product.isBestSeller()) {
            holder.bestsellerBadge.setVisibility(View.VISIBLE);
        } else {
            holder.bestsellerBadge.setVisibility(View.GONE);
        }
        // Open product details on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productId", product.getId());
            context.startActivity(intent);
        });

        // Handle wishlist click
        holder.wishlistButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWishlistClick(product);
                Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle add to cart click
        holder.cartLayout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCartClick(product);
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }