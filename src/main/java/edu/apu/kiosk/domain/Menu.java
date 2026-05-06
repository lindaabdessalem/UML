package edu.apu.kiosk.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Menu {
    private final String menuId;
    private final CopyOnWriteArrayList<Product> products;
    private LocalDateTime lastUpdated;

    public Menu(String menuId) {
        this.menuId = Objects.requireNonNull(menuId);
        this.products = new CopyOnWriteArrayList<>();
        this.lastUpdated = LocalDateTime.now();
    }

    public void addProduct(Product product) {
        products.add(Objects.requireNonNull(product));
        touch();
    }

    public void removeProduct(String productId) {
        products.removeIf(product -> product.getProductId().equals(productId));
        touch();
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Product> findById(String productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
    }

    public String getMenuId() {
        return menuId;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = Objects.requireNonNull(lastUpdated);
    }

    public void touch() {
        lastUpdated = LocalDateTime.now();
    }
}
