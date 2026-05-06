package edu.apu.kiosk.domain;

import java.util.Objects;

public final class User {
    private final String userId;
    private final String name;
    private final String cardId;
    private double cardBalance;

    public User(String userId, String name, String cardId, double cardBalance) {
        this.userId = Objects.requireNonNull(userId);
        this.name = Objects.requireNonNull(name);
        this.cardId = Objects.requireNonNull(cardId);
        this.cardBalance = cardBalance;
    }

    public boolean authenticate(String cardId) {
        return this.cardId.equals(cardId);
    }

    public double getBalance() {
        return cardBalance;
    }

    public void updateBalance(double balance) {
        this.cardBalance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getCardId() {
        return cardId;
    }
}
