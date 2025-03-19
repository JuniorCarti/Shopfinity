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


    // Constructor
    public WishlistItem(String id, String name, String brand, String category, String subcategory,
                        String description, double price, double discountPrice,
                        List<String> imageUrls, Map<String, Object> specifications) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.imageUrls = imageUrls;
        this.specifications = specifications;
    }
