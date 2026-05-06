package edu.apu.kiosk.service;

import edu.apu.kiosk.domain.Menu;
import edu.apu.kiosk.domain.Order;
import edu.apu.kiosk.domain.Product;
import edu.apu.kiosk.observer.MenuObserver;
import edu.apu.kiosk.observer.MenuSubject;
import edu.apu.kiosk.singleton.MenuManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public final class AdminSystem implements MenuSubject {
    private final List<MenuObserver> observers = new CopyOnWriteArrayList<>();
    private final List<Order> pendingOrders = new CopyOnWriteArrayList<>();
    private final String staffId;
    private Menu menu;

    public AdminSystem(String staffId, Menu menu) {
        this.staffId = Objects.requireNonNull(staffId);
        this.menu = Objects.requireNonNull(menu);
    }

    @Override
    public void attach(MenuObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    @Override
    public void detach(MenuObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(menu));
    }

    public List<Order> viewOrders() {
        return List.copyOf(pendingOrders);
    }

    public void addPendingOrder(Order order) {
        pendingOrders.add(order);
    }

    public void updatePrice(String productId, double price) {
        Product product = findProduct(productId);
        product.setPrice(price);
        menu.touch();
        MenuManager.getInstance().updateMenu(menu);
        notifyObservers();
    }

    public void updateAvailability(String productId, boolean available) {
        Product product = findProduct(productId);
        product.setAvailable(available);
        menu.touch();
        MenuManager.getInstance().updateMenu(menu);
        notifyObservers();
    }

    public void notifyKiosks(Menu menu) {
        this.menu = Objects.requireNonNull(menu);
        notifyObservers();
    }

    private Product findProduct(String productId) {
        return menu.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown product: " + productId));
    }

    public String getStaffId() {
        return staffId;
    }
}
