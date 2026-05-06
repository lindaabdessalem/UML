package edu.apu.kiosk.observer;

public interface MenuSubject {
    void attach(MenuObserver observer);

    void detach(MenuObserver observer);

    void notifyObservers();
}
