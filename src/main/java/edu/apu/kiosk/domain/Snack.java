package edu.apu.kiosk.domain;

import java.util.ArrayList;
import java.util.List;

public final class Snack extends Product {
    private final String type;
    private final List<String> ingredients = new ArrayList<>();
    private String dressingChoice;

    public Snack(String productId, String name, double price, String type) {
        super(productId, name, price, true);
        this.type = type;
        this.dressingChoice = "none";
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(String ingredient) {
        ingredients.remove(ingredient);
    }

    public void chooseDressing(String dressingChoice) {
        this.dressingChoice = dressingChoice;
    }

    @Override
    public String getDetails() {
        return getName() + " [" + type + ", ingredients=" + ingredients
                + ", dressing=" + dressingChoice + "]";
    }
}
