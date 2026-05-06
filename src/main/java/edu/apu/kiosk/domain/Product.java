package edu.apu.kiosk.domain;

import java.util.Objects;

public abstract class Product {
    private final String productId;
    private final String name;
    private double price;
    private boolean available;

    protected Product(String productId, String name, double price, boolean available) {
        this.productId = Objects.requireNonNull(productId);
        this.name = Objects.requireNonNull(name);
        this.price = price;
        this.available = available;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract String getDetails();
}
