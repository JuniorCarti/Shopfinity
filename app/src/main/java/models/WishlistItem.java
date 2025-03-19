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
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubcategory() { return subcategory; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @PropertyName("discount_price")
    public double getDiscountPrice() { return discountPrice; }
    @PropertyName("discount_price")
    public void setDiscountPrice(double discountPrice) { this.discountPrice = discountPrice; }

    @PropertyName("image_urls")
    public List<String> getImageUrls() { return imageUrls; }
    @PropertyName("image_urls")
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public Map<String, Object> getSpecifications() { return specifications; }
    public void setSpecifications(Map<String, Object> specifications) { this.specifications = specifications; }

    // toString() method for debugging
    @Override
    public String toString() {
        return "WishlistItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", imageUrls=" + imageUrls +
                ", specifications=" + specifications +
                '}';
    }
}