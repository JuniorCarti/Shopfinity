package models;

import com.google.firebase.firestore.PropertyName;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class WishlistItem implements Serializable {
    private String id;
    private String name;
    private String brand;
    private String category;
    private String subcategory;
    private String description;
    private double price;

    @PropertyName("discount_price")
    private double discountPrice;

    @PropertyName("image_urls")
    private List<String> imageUrls;

    private Map<String, Object> specifications;

    // Empty constructor (required for Firestore)
    public WishlistItem() {}