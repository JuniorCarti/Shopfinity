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

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getCategory() { return category; }
    public String getSubcategory() { return subcategory; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    @PropertyName("discount_price")
    public double getDiscountPrice() { return discountPrice; }

    public double getRating() { return rating; }
    public int getReviews() { return reviews; }
    public int getStock() { return stock; }

    @PropertyName("image_urls")
    public List<String> getImageUrls() { return imageUrls; }

    public Map<String, Object> getSpecifications() { return specifications; } // Change to Map

    @PropertyName("best_seller")
    public boolean isBestSeller() { return bestSeller; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setCategory(String category) { this.category = category; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }

    @PropertyName("discount_price")
    public void setDiscountPrice(double discountPrice) { this.discountPrice = discountPrice; }

    public void setRating(double rating) { this.rating = rating; }
    public void setReviews(int reviews) { this.reviews = reviews; }
    public void setStock(int stock) { this.stock = stock; }

    @PropertyName("image_urls")
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public void setSpecifications(Map<String, Object> specifications) { this.specifications = specifications; } // Change to Map

    @PropertyName("best_seller")
    public void setBestSeller(boolean bestSeller) { this.bestSeller = bestSeller; }

    // toString() method for debugging
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", rating=" + rating +
                ", reviews=" + reviews +
                ", stock=" + stock +
                ", imageUrls=" + imageUrls +
                ", specifications=" + specifications +
                ", bestSeller=" + bestSeller +
                '}';
    }
}
