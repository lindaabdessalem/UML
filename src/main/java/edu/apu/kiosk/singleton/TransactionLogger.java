package edu.apu.kiosk.singleton;

import edu.apu.kiosk.domain.LogEntry;
import edu.apu.kiosk.domain.Order;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class TransactionLogger {
    private static final TransactionLogger INSTANCE = new TransactionLogger();

    private final List<LogEntry> logs = new CopyOnWriteArrayList<>();

    private TransactionLogger() {
    }

    public static TransactionLogger getInstance() {
        return INSTANCE;
    }

    public void log(String kioskId, Order order) {
        logs.add(new LogEntry(
                kioskId,
                order.getOrderId(),
                order.getUserId(),
                order.calculateTotal(),
                order.getTimestamp()
        ));
    }

    public List<LogEntry> getLogs(String kioskId) {
        return logs.stream()
                .filter(log -> log.kioskId().equals(kioskId))
                .toList();
    }

    public List<LogEntry> exportLogs() {
        return List.copyOf(logs);
    }
}
