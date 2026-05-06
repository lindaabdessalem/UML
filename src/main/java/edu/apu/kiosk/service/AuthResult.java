package edu.apu.kiosk.service;

import edu.apu.kiosk.domain.User;

public record AuthResult(boolean authenticated, User user, double balance, String message) {
    public static AuthResult success(User user, double balance) {
        return new AuthResult(true, user, balance, "Authenticated");
    }

    public static AuthResult failure(String message) {
        return new AuthResult(false, null, 0.0, message);
    }
}
