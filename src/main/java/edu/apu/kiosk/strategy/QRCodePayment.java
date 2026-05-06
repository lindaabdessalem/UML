package edu.apu.kiosk.strategy;

import java.util.UUID;

public final class QRCodePayment implements PaymentStrategy {
    private final String qrToken;
    private final String sessionId;
    private boolean confirmed;

    public QRCodePayment() {
        this.qrToken = UUID.randomUUID().toString();
        this.sessionId = UUID.randomUUID().toString();
    }

    @Override
    public boolean pay(double amount) {
        return confirmed;
    }

    public String generateQR() {
        return "QR:" + sessionId + ":" + qrToken;
    }

    public void confirmExternally() {
        this.confirmed = true;
    }

    @Override
    public String getDescription() {
        return "QR code payment session: " + sessionId;
    }
}
