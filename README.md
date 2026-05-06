# APU Kiosk UML Project

This project is based on the UXF files uploaded to GitHub. The first use case diagram keeps the UXF-style rendering, while the other diagrams use clean Mermaid redraws for a squarer final presentation.

## Main source documents

- `01_use_case_diagram.uxf`
- `02_class_diagram_no_patterns.uxf`
- `03_sequence_place_order.uxf`
- `04_sequence_update_menu.uxf`
- `05_sequence_authenticate_user.uxf`
- `06_class_diagram_with_patterns.uxf`
- `docs/OMU-Assignment-2601.pdf`

## Project structure

```text
.
|-- *.uxf                  # Main UMLet source diagrams uploaded to GitHub
|-- diagrams/              # Clean Mermaid redraws for diagrams 02-08
|-- docs/                  # Assignment PDF
|-- images/                # Final SVG and PNG exports
|-- report/                # Written report and requirements mapping
|-- scripts/               # Rendering scripts
`-- src/main/java/         # Java skeleton implementing the patterns
```

## Rendering diagrams

Regenerate all clean SVG and PNG files:

```powershell
.\scripts\render-diagrams.ps1
```

## Java implementation

The Java code implements the patterns shown in `06_class_diagram_with_patterns.uxf` and the clean redraw `diagrams/06_class_diagram_with_patterns.mmd`:

- Observer: `MenuSubject`, `MenuObserver`, `AdminSystem`, `Kiosk`
- Strategy: `PaymentStrategy`, `APCardPayment`, `QRCodePayment`
- Singleton: `MenuManager`, `TransactionLogger`

The code is intentionally skeletal, matching the assignment requirement to implement the design patterns without building a full production kiosk application.
