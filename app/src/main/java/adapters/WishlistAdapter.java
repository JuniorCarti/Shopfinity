package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopfinity.R;
import java.util.List;
import models.WishlistItem;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private Context context;
    private List<WishlistItem> wishlistItems;
    private OnWishlistItemClickListener listener;

    public WishlistAdapter(Context context, List<WishlistItem> wishlistItems, OnWishlistItemClickListener listener) {
        this.context = context;
        this.wishlistItems = wishlistItems;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wishlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishlistItem item = wishlistItems.get(position);
        // Bind data to views
        holder.productName.setText(item.getName());
        holder.productBrand.setText(item.getBrand());
        holder.productPrice.setText(String.format("Ksh %.2f", item.getPrice()));

        // Show discount price if available
        if (item.getDiscountPrice() > 0 && item.getDiscountPrice() < item.getPrice()) {
            holder.productDiscountPrice.setText(String.format("Ksh %.2f", item.getDiscountPrice()));
            holder.productDiscountPrice.setVisibility(View.VISIBLE);
        } else {
            holder.productDiscountPrice.setVisibility(View.GONE);
        }

        // Load product image
        if (item.getImageUrls() != null && !item.getImageUrls().isEmpty()) {
            Glide.with(context)
                    .load(item.getImageUrls().get(0))
                    .placeholder(R.drawable.image_placeholder)
                    .into(holder.productImage);
        }

        // Handle remove button click
        holder.removeButton.setOnClickListener(v -> listener.onRemoveClick(item, position));

        // Handle add to cart button click
        holder.addToCartLayout.setOnClickListener(v -> listener.onAddToCartClick(item));
    }
    @Override
    public int getItemCount() {
        return wishlistItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productBrand, productPrice, productDiscountPrice;
        ImageButton removeButton;
        ViewGroup addToCartLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productBrand = itemView.findViewById(R.id.productBrand);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDiscountPrice = itemView.findViewById(R.id.productDiscountPrice);
            removeButton = itemView.findViewById(R.id.removeFromWishlistButton);
            addToCartLayout = itemView.findViewById(R.id.addToCartLayout);
        }
    }

    public interface OnWishlistItemClickListener {
        void onItemClick(WishlistItem item);

        void onRemoveClick(WishlistItem item, int position);
        void onAddToCartClick(WishlistItem item);
    }
}
