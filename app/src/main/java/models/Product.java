package models;

import com.google.firebase.firestore.PropertyName;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Product implements Serializable {
    private String id;
    private String name;
    private String brand;
    private String category;
    private String subcategory;
    private String description;
    private double price;

    @PropertyName("discount_price")
    private double discountPrice;

    private double rating;
    private int reviews;
    private int stock;

    @PropertyName("image_urls")
    private List<String> imageUrls;

    private Map<String, Object> specifications; // Change to Map

    @PropertyName("best_seller")
    private boolean bestSeller;

    // Empty constructor (required for Firestore)
    public Product() {}

    // Constructor
    public Product(String id, String name, String brand, String category, String subcategory,
                   String description, double price, double discountPrice, double rating,
                   int reviews, int stock, List<String> imageUrls, Map<String, Object> specifications, boolean bestSeller) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.rating = rating;
        this.reviews = reviews;
        this.stock = stock;
        this.imageUrls = imageUrls;
        this.specifications = specifications;
        this.bestSeller = bestSeller;
    }
