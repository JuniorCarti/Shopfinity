package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        // Load product image
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            Glide.with(context)
                    .load(product.getImageUrls().get(0))
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_error)
                    .into(holder.productImage);
        }

        // Bind product data
        holder.productName.setText(product.getName());
        holder.productBrand.setText(product.getBrand());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText("Ksh " + priceFormat.format(product.getPrice()));

        // Handle discount visibility
        if (product.getDiscountPrice() > 0 && product.getDiscountPrice() < product.getPrice()) {
            holder.productDiscountPrice.setText("Ksh " + priceFormat.format(product.getDiscountPrice()));
            holder.productDiscountPrice.setVisibility(View.VISIBLE);
            holder.productPrice.setPaintFlags(holder.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.productDiscountPrice.setVisibility(View.GONE);
            holder.productPrice.setPaintFlags(0);
        }

        // Update star rating and review count
        updateStarRating(holder, product.getRating(), product.getReviews());

        // Bestseller badge visibility
        holder.bestsellerBadge.setVisibility(product.isBestSeller() ? View.VISIBLE : View.GONE);

        // Open product details on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productId", product.getId());
            context.startActivity(intent);
        });

        // Wishlist click listener
        holder.wishlistButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWishlistClick(product);
                Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
            }
        });

        // Add to cart click listener
        holder.cartLayout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCartClick(product);
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<Product> newList) {
        productList = newList;
        notifyDataSetChanged(); // Use DiffUtil for better performance in future
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, wishlistButton;
        TextView productName, productBrand, productDescription, productPrice, productDiscountPrice, bestsellerBadge, productRatingCount;
        LinearLayout cartLayout, starContainer;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            wishlistButton = itemView.findViewById(R.id.wishlistButton);
            cartLayout = itemView.findViewById(R.id.cartLayout);
            productName = itemView.findViewById(R.id.productName);
            productBrand = itemView.findViewById(R.id.productBrand);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDiscountPrice = itemView.findViewById(R.id.productDiscountPrice);
            bestsellerBadge = itemView.findViewById(R.id.bestsellerBadge);
            starContainer = itemView.findViewById(R.id.starContainer);
            productRatingCount = itemView.findViewById(R.id.productRatingCount);
        }
    }

    /**
     * Updates the star rating UI dynamically and sets the review count.
     */
    private void updateStarRating(ProductViewHolder holder, double rating, int reviewCount) {
        holder.starContainer.removeAllViews();

        int fullStars = (int) rating;
        boolean hasHalfStar = (rating - fullStars) >= 0.5;
        int emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);

        for (int i = 0; i < fullStars; i++) {
            ImageView star = new ImageView(context);
            star.setImageResource(R.drawable.ic_star_filled);
            holder.starContainer.addView(star);
        }

        if (hasHalfStar) {
            ImageView halfStar = new ImageView(context);
            halfStar.setImageResource(R.drawable.ic_star_half);
            holder.starContainer.addView(halfStar);
        }

        for (int i = 0; i < emptyStars; i++) {
            ImageView emptyStar = new ImageView(context);
            emptyStar.setImageResource(R.drawable.ic_star_empty);
            holder.starContainer.addView(emptyStar);
        }

        // Set review count
        holder.productRatingCount.setText("(" + reviewCount + ")");
    }
}
