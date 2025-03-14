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
