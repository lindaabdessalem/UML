package edu.apu.kiosk.service;

import edu.apu.kiosk.domain.User;

import java.util.Optional;

public final class AuthService {
    private final UserDatabase userDatabase;

    public AuthService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public AuthResult authenticate(String cardId) {
        Optional<User> user = userDatabase.findUser(cardId);
        if (user.isEmpty()) {
            return AuthResult.failure("Unknown card");
        }

        User found = user.get();
        if (!found.authenticate(cardId)) {
            return AuthResult.failure("Authentication failed");
        }

        if (found.getBalance() < 0) {
            return AuthResult.failure("Insufficient balance");
        }

        return AuthResult.success(found, found.getBalance());
    }
}
