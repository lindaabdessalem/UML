package edu.apu.kiosk.domain;

import java.util.ArrayList;
import java.util.List;

public final class Drink extends Product {
    private String size;
    private String sugarLevel;
    private String milkLevel;
    private String iceLevel;
    private final List<String> toppings = new ArrayList<>();

    public Drink(String productId, String name, double price) {
        super(productId, name, price, true);
        this.size = "medium";
        this.sugarLevel = "normal";
        this.milkLevel = "normal";
        this.iceLevel = "normal";
    }

    public void customise(String size, String sugarLevel, String milkLevel, String iceLevel, List<String> toppings) {
        this.size = size;
        this.sugarLevel = sugarLevel;
        this.milkLevel = milkLevel;
        this.iceLevel = iceLevel;
        this.toppings.clear();
        this.toppings.addAll(toppings);
    }

    @Override
    public String getDetails() {
        return getName() + " [" + size + ", sugar=" + sugarLevel + ", milk=" + milkLevel
                + ", ice=" + iceLevel + ", toppings=" + toppings + "]";
    }
}
