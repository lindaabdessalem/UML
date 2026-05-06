# APU Campus Wide Drinks and Snack Ordering Kiosk System

## Cover Page

**Module:** Object Modelling with UML  
**Assessment:** Mini Project Coursework  
**Weightage:** 30%  
**Institution:** Asia Pacific University of Technology and Innovation  
**Year:** 2026  
**Project:** APU Campus Wide Drinks and Snack Ordering Kiosk System  
**Group Members:** _Add names and student IDs_  

## Table of Contents

1. Overview of the Scenario
2. Use Case Diagram and Use Case Descriptions
3. Class Diagram without Design Patterns
4. Sequence Diagrams
5. Design Pattern Report
6. Refined Class Diagram with Design Patterns
7. Pattern Interaction Sequence Diagrams
8. Critical Appraisal
9. References

## 1. Overview of the Scenario

Asia Pacific University plans to implement a campus-wide kiosk system for ordering drinks and snacks. The system is intended to reduce queues at the 3rd floor snack booth by allowing students, staff, and lecturers to order through kiosks. Five kiosks are planned initially, with possible future expansion.

Users identify themselves with their AP card, check their balance, browse the menu, customise drinks or snacks, place an order, and pay using their card balance. Paid orders are sent to the snack booth preparation area. Admin staff can update prices and availability centrally, and those menu changes must be broadcast to connected kiosks.

The uploaded UXF diagrams are used as the main design documents for this report. The first use case diagram keeps the UXF-style rendering, while the remaining diagrams use clean Mermaid redraws for readability.

## 2. Use Case Diagram and Use Case Descriptions

**Source and rendered style:** `01_use_case_diagram.uxf`

![Use case diagram](../images/01_use_case_diagram.svg)

### Actors

**Student**  
Uses the kiosk to identify themselves, browse the menu, customise items, place orders, and pay.

**Staff / Lecturer**  
Uses the same ordering workflow as students.

**Admin / Staff**  
Maintains prices and product availability, receives order information, and broadcasts menu changes.

### Main Use Cases

**Identify User / Check Balance**  
The user swipes their AP card. The kiosk validates the card and displays the user identity and balance.

**Browse Menu**  
The user views available drinks and snacks.

**Customise Drink / Snack**  
The user selects options such as size, sugar level, milk level, ice level, toppings, ingredients, or dressing.

**Place Order**  
The user confirms selected items. This includes deducting balance and logging the transaction.

**Deduct Card Balance**  
The payment processor deducts the order total from the AP card balance.

**Log Transaction**  
The kiosk records the order and payment transaction for audit and reporting.

**Receive Order Notification**  
Snack booth staff receive details of orders that need preparation.

**Update Price / Availability**  
Admin staff update product prices or availability in the admin system.

**Broadcast Menu Changes**  
The admin system notifies connected kiosks so their local menus remain consistent.

## 3. Class Diagram without Design Patterns

**Source:** `02_class_diagram_no_patterns.uxf`  
**Clean redraw:** `diagrams/02_class_diagram_no_patterns.mmd`

![Class diagram without patterns](../images/02_class_diagram_no_patterns.svg)

The initial class diagram models the system before applying design patterns. The main classes are:

- `User`, storing identity, card ID, and card balance.
- `Kiosk`, handling menu display, order processing, menu updates, and transaction logging.
- `Order` and `OrderItem`, representing confirmed purchases and selected products.
- `Product`, with concrete product types `Drink` and `Snack`.
- `Menu`, containing available products.
- `PaymentProcessor`, responsible for balance deduction and payment confirmation.
- `TransactionLog`, recording kiosk transactions.
- `AdminSystem`, managing pending orders, prices, availability, and kiosk notifications.

This model covers the main responsibilities, but some classes have broad responsibilities. The refined design improves this through Observer, Strategy, and Singleton.

## 4. Sequence Diagrams

### 4.1 Place Order

**Source:** `03_sequence_place_order.uxf`  
**Clean redraw:** `diagrams/03_sequence_place_order.mmd`

![Place order sequence](../images/03_sequence_place_order.svg)

The place order sequence starts with the user swiping an AP card. The kiosk retrieves products from the menu, displays them, receives item selections and customisation choices, creates the order, calculates the total, deducts the card balance, submits the order, and displays confirmation.

### 4.2 Update Menu

**Source:** `04_sequence_update_menu.uxf`  
**Clean redraw:** `diagrams/04_sequence_update_menu.mmd`

![Update menu sequence](../images/04_sequence_update_menu.svg)

The admin system updates a product price through the menu. The product is found, updated, persisted, and the menu timestamp is refreshed. The updated menu is then sent to every connected kiosk.

### 4.3 Authenticate User

**Source:** `05_sequence_authenticate_user.uxf`  
**Clean redraw:** `diagrams/05_sequence_authenticate_user.mmd`

![Authenticate user sequence](../images/05_sequence_authenticate_user.svg)

The user swipes their card at the kiosk. The kiosk asks the authentication service to authenticate the card, the service looks up the user in the user database, validates balance, and returns either a welcome message with balance or an error message.

## 5. Design Pattern Report

The refined design uses three design patterns shown in `06_class_diagram_with_patterns.uxf`: Observer, Strategy, and Singleton.

### Observer Pattern

The Observer pattern is used to broadcast menu changes. `AdminSystem` acts as the subject and maintains a list of `MenuObserver` objects. `Kiosk` implements `MenuObserver` and receives menu updates through `update(menu)`.

This pattern is suitable because the assignment requires central price and availability changes to take effect across all kiosks. Observer reduces coupling: the admin system does not need to know the internal implementation of every kiosk.

### Strategy Pattern

The Strategy pattern is used for payment. `PaymentStrategy` defines the common payment interface, while `APCardPayment` implements the current campus card payment. `QRCodePayment` is marked as a future payment option.

This pattern is suitable because payment methods may change. The kiosk can use any object implementing `PaymentStrategy` without changing the order-processing code.

### Singleton Pattern

The Singleton pattern is used for `MenuManager` and `TransactionLogger`. `MenuManager` provides a single central menu manager, while `TransactionLogger` provides a single shared logging service.

This pattern is suitable when there should be one coordinated access point for shared state. However, Singleton must be used carefully because it can introduce global state and make testing harder.

### Java Implementation Mapping

| Pattern | Java Classes | Purpose |
| --- | --- | --- |
| Observer | `MenuSubject`, `MenuObserver`, `AdminSystem`, `Kiosk` | Broadcasts central menu changes to all kiosks. |
| Strategy | `PaymentStrategy`, `APCardPayment`, `QRCodePayment` | Allows interchangeable payment methods. |
| Singleton | `MenuManager`, `TransactionLogger` | Provides controlled single instances for shared menu management and transaction logging. |

## 6. Refined Class Diagram with Design Patterns

**Source:** `06_class_diagram_with_patterns.uxf`  
**Clean redraw:** `diagrams/06_class_diagram_with_patterns.mmd`

![Class diagram with patterns](../images/06_class_diagram_with_patterns.svg)

The refined class diagram improves the initial model by separating pattern responsibilities:

- `MenuSubject` and `MenuObserver` formalise the menu update notification mechanism.
- `AdminSystem` implements the subject role and notifies kiosks.
- `Kiosk` implements the observer role and refreshes its local menu.
- `PaymentStrategy` separates payment behaviour from kiosk behaviour.
- `APCardPayment` supports the current AP card system.
- `QRCodePayment` shows how future payment options can be added.
- `MenuManager` and `TransactionLogger` centralise shared system services.

## 7. Pattern Interaction Sequence Diagrams

The uploaded UXF documents provide the refined class diagram with patterns. To complete the assignment requirement for pattern interactions, two supplementary sequence diagrams were added. These diagrams are derived from `06_class_diagram_with_patterns.uxf` and the Java implementation.

### 7.1 Observer Menu Broadcast

**Source:** `diagrams/07_sequence_observer_menu_broadcast.mmd`

![Observer menu broadcast sequence](../images/07_sequence_observer_menu_broadcast.svg)

This sequence shows kiosks registering as observers and receiving a menu update after an admin changes product availability.

### 7.2 Strategy and Singleton Payment Logging

**Source:** `diagrams/08_sequence_strategy_singleton_payment_logging.mmd`

![Strategy and singleton sequence](../images/08_sequence_strategy_singleton_payment_logging.svg)

This sequence shows the kiosk calling a payment strategy and then using the singleton transaction logger when payment succeeds.

## 8. Critical Appraisal

The design covers the main assignment requirements: user authentication, menu browsing, product customisation, order placement, card balance deduction, transaction logging, admin product updates, and menu broadcasting.

The Observer pattern is a strong fit because the system has multiple kiosks that must react to central menu changes. This directly supports extensibility from five kiosks to ten kiosks.

The Strategy pattern is also suitable because it keeps payment processing flexible. Although the current requirement specifies AP card payment, the design can later support QR code payment or another campus payment method.

The Singleton pattern is useful for shared services such as the menu manager and transaction logger, but it is the pattern that requires the most caution. In a real system, dependency injection could sometimes be preferable because it improves testability and reduces hidden global state.

The main limitation of the current diagrams is that concurrency is only implied. Since several kiosks can place orders at the same time, the implementation should use thread-safe collections or queues for order submission, menu broadcasting, and transaction logs.

Overall, the design is suitable for the coursework because it addresses the functional requirements and demonstrates three object-oriented design patterns in a coherent way.

## 9. References

- Asia Pacific University. (2026). _OMU Mini Project: APU Campus Wide Drinks and Snack Ordering Kiosk System_.
- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). _Design Patterns: Elements of Reusable Object-Oriented Software_. Addison-Wesley.
- Oracle. (n.d.). _Java Platform Documentation_.
