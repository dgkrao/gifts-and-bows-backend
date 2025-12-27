package com.giftsandbows.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    private double price;
    private double discountedPrice;
    private int quantity;

    private String imageUrls; // comma separated

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /* ===== GETTERS & SETTERS ===== */

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(double discountedPrice) { this.discountedPrice = discountedPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getImageUrls() { return imageUrls; }
    public void setImageUrls(String imageUrls) { this.imageUrls = imageUrls; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
