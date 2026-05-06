package edu.apu.kiosk.domain;

import java.time.LocalDateTime;

public record LogEntry(String kioskId, String orderId, String userId, double total, LocalDateTime timestamp) {
}
