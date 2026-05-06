package edu.apu.kiosk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class OrderItem {
    private final Product product;
    private final int quantity;
    private final List<String> customisations;

    public OrderItem(Product product, int quantity) {
        this.product = Objects.requireNonNull(product);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
        this.customisations = new ArrayList<>();
    }

    public void addCustomisation(String customisation) {
        customisations.add(customisation);
    }

    public double getLineTotal() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<String> getCustomisations() {
        return List.copyOf(customisations);
    }
}
