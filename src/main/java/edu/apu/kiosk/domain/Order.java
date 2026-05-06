package edu.apu.kiosk.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Order {
    private final String orderId;
    private final List<OrderItem> items;
    private final String userId;
    private final LocalDateTime timestamp;
    private OrderStatus status;

    public Order(String userId) {
        this.orderId = UUID.randomUUID().toString();
        this.items = new ArrayList<>();
        this.userId = Objects.requireNonNull(userId);
        this.timestamp = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    public void addItem(OrderItem item) {
        items.add(Objects.requireNonNull(item));
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(OrderItem::getLineTotal)
                .sum();
    }

    public void submit() {
        status = OrderStatus.SUBMITTED;
    }

    public void markPaid() {
        status = OrderStatus.PAID;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
