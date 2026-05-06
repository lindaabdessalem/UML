package edu.apu.kiosk.strategy;

public interface PaymentStrategy {
    boolean pay(double amount);

    String getDescription();
}
