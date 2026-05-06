package edu.apu.kiosk.service;

import edu.apu.kiosk.domain.Menu;
import edu.apu.kiosk.domain.Order;
import edu.apu.kiosk.domain.Product;
import edu.apu.kiosk.observer.MenuObserver;
import edu.apu.kiosk.singleton.TransactionLogger;
import edu.apu.kiosk.strategy.PaymentStrategy;

import java.util.List;
import java.util.Objects;

public final class Kiosk implements MenuObserver {
    private final String kioskId;
    private final String location;
    private boolean active;
    private Menu localMenu;
    private PaymentStrategy payStrategy;

    public Kiosk(String kioskId, String location, Menu localMenu, PaymentStrategy payStrategy) {
        this.kioskId = Objects.requireNonNull(kioskId);
        this.location = Objects.requireNonNull(location);
        this.localMenu = Objects.requireNonNull(localMenu);
        this.payStrategy = Objects.requireNonNull(payStrategy);
        this.active = true;
    }

    public List<Product> displayMenu() {
        return localMenu.getProducts().stream()
                .filter(Product::isAvailable)
                .toList();
    }

    public boolean processOrder(Order order) {
        if (!active) {
            throw new IllegalStateException("Kiosk is inactive");
        }

        double total = order.calculateTotal();
        boolean paid = payStrategy.pay(total);
        if (!paid) {
            return false;
        }

        order.markPaid();
        order.submit();
        logTransaction(order);
        return true;
    }

    public void updateMenu(Menu menu) {
        this.localMenu = Objects.requireNonNull(menu);
    }

    public void logTransaction(Order order) {
        TransactionLogger.getInstance().log(kioskId, order);
    }

    public void refreshLocalMenu(Menu menu) {
        updateMenu(menu);
    }

    @Override
    public void update(Menu menu) {
        updateMenu(menu);
    }

    public void setPaymentStrategy(PaymentStrategy payStrategy) {
        this.payStrategy = Objects.requireNonNull(payStrategy);
    }

    public String getKioskId() {
        return kioskId;
    }

    public String getLocation() {
        return location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
