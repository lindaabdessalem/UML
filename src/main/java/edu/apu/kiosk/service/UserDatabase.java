package edu.apu.kiosk.service;

import edu.apu.kiosk.domain.User;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class UserDatabase {
    private final ConcurrentMap<String, User> usersByCardId = new ConcurrentHashMap<>();

    public void save(User user) {
        usersByCardId.put(user.getCardId(), user);
    }

    public Optional<User> findUser(String cardId) {
        return Optional.ofNullable(usersByCardId.get(cardId));
    }
}
