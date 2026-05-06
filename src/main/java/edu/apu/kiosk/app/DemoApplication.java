package edu.apu.kiosk.app;

import edu.apu.kiosk.domain.Drink;
import edu.apu.kiosk.domain.Menu;
import edu.apu.kiosk.domain.Order;
import edu.apu.kiosk.domain.OrderItem;
import edu.apu.kiosk.domain.Snack;
import edu.apu.kiosk.domain.User;
import edu.apu.kiosk.service.AdminSystem;
import edu.apu.kiosk.service.AuthResult;
import edu.apu.kiosk.service.AuthService;
import edu.apu.kiosk.service.Kiosk;
import edu.apu.kiosk.service.UserDatabase;
import edu.apu.kiosk.singleton.MenuManager;
import edu.apu.kiosk.singleton.TransactionLogger;
import edu.apu.kiosk.strategy.APCardPayment;

import java.util.List;

public final class DemoApplication {
    private DemoApplication() {
    }

    public static void main(String[] args) {
        Menu menu = seedMenu();
        MenuManager.getInstance().updateMenu(menu);

        User user = new User("TP000001", "Alya Rahman", "CARD-1001", 50.0);
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.save(user);
        AuthService authService = new AuthService(userDatabase);

        Kiosk kiosk = new Kiosk(
                "KIOSK-1",
                "Level 3",
                menu,
                new APCardPayment(user.getCardId(), user.getBalance())
        );

        AdminSystem adminSystem = new AdminSystem("STAFF-01", menu);
        adminSystem.attach(kiosk);
        MenuManager.getInstance().registerKiosk(kiosk);

        AuthResult authResult = authService.authenticate("CARD-1001");
        if (!authResult.authenticated()) {
            throw new IllegalStateException(authResult.message());
        }

        Drink kopiAis = (Drink) menu.findById("DRINK-001").orElseThrow();
        kopiAis.customise("large", "less", "extra", "less", List.of("cream"));

        Snack sandwich = (Snack) menu.findById("SNACK-002").orElseThrow();
        sandwich.addIngredient("tuna");
        sandwich.addIngredient("cheese");
        sandwich.chooseDressing("honey mustard");

        Order order = new Order(user.getUserId());
        order.addItem(new OrderItem(kopiAis, 1));
        order.addItem(new OrderItem(sandwich, 1));

        boolean processed = kiosk.processOrder(order);
        adminSystem.addPendingOrder(order);
        adminSystem.updateAvailability("SNACK-001", false);

        System.out.println("Processed: " + processed);
        System.out.println("Order status: " + order.getStatus());
        System.out.println("Total: RM " + order.calculateTotal());
        System.out.println("Logs for kiosk: " + TransactionLogger.getInstance().getLogs("KIOSK-1").size());
    }

    private static Menu seedMenu() {
        Menu menu = new Menu("CENTRAL-MENU");
        menu.addProduct(new Drink("DRINK-001", "Kopi Ais", 4.50));
        menu.addProduct(new Drink("DRINK-002", "Iced Latte", 6.00));
        menu.addProduct(new Snack("SNACK-001", "Curry Puff", 2.50, "pastry"));
        menu.addProduct(new Snack("SNACK-002", "Fresh Sandwich", 6.50, "sandwich"));
        return menu;
    }
}
