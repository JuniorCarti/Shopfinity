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