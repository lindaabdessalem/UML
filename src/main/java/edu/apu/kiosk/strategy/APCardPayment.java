package edu.apu.kiosk.strategy;

import java.util.Objects;

public final class APCardPayment implements PaymentStrategy {
    private final String cardId;
    private double balance;

    public APCardPayment(String cardId, double balance) {
        this.cardId = Objects.requireNonNull(cardId);
        this.balance = balance;
    }

    @Override
    public synchronized boolean pay(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (balance < amount) {
            return false;
        }
        deductBalance(amount);
        return true;
    }

    public synchronized void deductBalance(double amount) {
        balance -= amount;
    }

    @Override
    public String getDescription() {
        return "AP card payment: " + cardId;
    }

    public synchronized double getBalance() {
        return balance;
    }
}
