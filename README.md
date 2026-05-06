# OMU Mini Project - Final Submission Package

## What To Submit

Locally, the clean submission package to upload is:

```text
OMU-final-submission.zip
```

The same content is also available unpacked locally in:

```text
final-submission/
```

## Package Contents

The zip contains only the files needed for the final hand-in:

```text
.
|-- final-report.pdf                 # Main report to read first
|-- 01_use_case_diagram.uxf           # UMLet source diagrams
|-- 02_class_diagram_no_patterns.uxf
|-- 03_sequence_place_order.uxf
|-- 04_sequence_update_menu.uxf
|-- 05_sequence_authenticate_user.uxf
|-- 06_class_diagram_with_patterns.uxf
|-- images/                          # SVG and PNG diagram exports
|-- diagrams/                        # Mermaid sources for clean redraws
|-- src/main/java/                   # Java skeletal implementation
|-- pom.xml                          # Maven project file
`-- README.md
```

## How To Review

1. Open `final-report.pdf`.
2. Check the `.uxf` files if editable UML diagrams are required.
3. Use `images/` for exported diagram versions.
4. Review `src/main/java/` for the Java implementation.

## Implemented Design Patterns

| Pattern | Main Classes |
| --- | --- |
| Observer | `MenuSubject`, `MenuObserver`, `AdminSystem`, `Kiosk` |
| Strategy | `PaymentStrategy`, `APCardPayment`, `QRCodePayment` |
| Singleton | `MenuManager`, `TransactionLogger` |

## Note

The Java code is a skeletal implementation for the UML design and selected patterns. It is structured as a Java 17 Maven project through `pom.xml`.
