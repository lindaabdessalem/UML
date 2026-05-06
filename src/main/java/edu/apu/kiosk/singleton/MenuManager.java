package edu.apu.kiosk.singleton;

import edu.apu.kiosk.domain.Menu;
import edu.apu.kiosk.service.Kiosk;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MenuManager {
    private static final MenuManager INSTANCE = new MenuManager();

    private final List<Kiosk> kiosks = new CopyOnWriteArrayList<>();
    private volatile Menu menu;

    private MenuManager() {
        this.menu = new Menu("CENTRAL-MENU");
    }

    public static MenuManager getInstance() {
        return INSTANCE;
    }

    public Menu getMenu() {
        return menu;
    }

    public void updateMenu(Menu menu) {
        this.menu = menu;
        broadcast();
    }

    public void registerKiosk(Kiosk kiosk) {
        kiosks.add(kiosk);
    }

    public void unregisterKiosk(Kiosk kiosk) {
        kiosks.remove(kiosk);
    }

    public void broadcast() {
        kiosks.forEach(kiosk -> kiosk.update(menu));
    }
}
