from __future__ import annotations

import html
import re
import sys
import xml.etree.ElementTree as ET
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
IMAGE_DIR = ROOT / "images"


def clean_text(value: str | None) -> str:
    text = value or ""
    replacements = {
        "\u00c2\u00ab": "<<",
        "\u00c2\u00bb": ">>",
        "\u00e2\u20ac\u201c": "-",
        "\r": "",
    }
    for old, new in replacements.items():
        text = text.replace(old, new)
    return text


def esc(value: str) -> str:
    return html.escape(value, quote=True)


def parse_points(coordinates: ET.Element, additional: str) -> list[tuple[int, int]]:
    x = int(coordinates.findtext("x", "0"))
    y = int(coordinates.findtext("y", "0"))
    numbers = [int(n) for n in re.findall(r"-?\d+", additional)]
    return [(x + numbers[i], y + numbers[i + 1]) for i in range(0, len(numbers) - 1, 2)]


def text_lines(text: str) -> list[str]:
    return clean_text(text).split("\n")


def draw_multiline_text(lines: list[str], x: int, y: int, width: int, size: int = 13) -> list[str]:
    output: list[str] = []
    current_y = y
    for raw_line in lines:
        line = raw_line.strip()
        if line == "--":
            output.append(f'<line x1="{x}" y1="{current_y - 8}" x2="{x + width}" y2="{current_y - 8}" stroke="#222" stroke-width="1"/>')
            current_y += 8
            continue

        font_style = ""
        cleaned = line
        if len(cleaned) >= 2 and cleaned.startswith("/") and cleaned.endswith("/"):
            cleaned = cleaned[1:-1]
            font_style = ' font-style="italic"'

        if cleaned:
            output.append(
                f'<text x="{x + 8}" y="{current_y}" font-size="{size}" font-family="Arial, sans-serif"{font_style}>{esc(cleaned)}</text>'
            )
        current_y += size + 4
    return output


def draw_class(element: ET.Element) -> list[str]:
    coords = element.find("coordinates")
    assert coords is not None
    x = int(coords.findtext("x", "0"))
    y = int(coords.findtext("y", "0"))
    w = int(coords.findtext("w", "0"))
    h = int(coords.findtext("h", "0"))
    lines = text_lines(element.findtext("panel_attributes", ""))
    return [
        f'<rect x="{x}" y="{y}" width="{w}" height="{h}" fill="#fffdf7" stroke="#1f2933" stroke-width="1.6"/>',
        *draw_multiline_text(lines, x, y + 20, w),
    ]


def draw_object(element: ET.Element) -> list[str]:
    coords = element.find("coordinates")
    assert coords is not None
    x = int(coords.findtext("x", "0"))
    y = int(coords.findtext("y", "0"))
    w = int(coords.findtext("w", "0"))
    h = int(coords.findtext("h", "0"))
    label = clean_text(element.findtext("panel_attributes", "")).strip()
    return [
        f'<rect x="{x}" y="{y}" width="{w}" height="{h}" fill="#f7fbff" stroke="#1f2933" stroke-width="1.4"/>',
        f'<text x="{x + w / 2}" y="{y + h / 2 + 5}" text-anchor="middle" font-size="13" font-family="Arial, sans-serif">{esc(label)}</text>',
    ]


def draw_use_case(element: ET.Element) -> list[str]:
    coords = element.find("coordinates")
    assert coords is not None
    x = int(coords.findtext("x", "0"))
    y = int(coords.findtext("y", "0"))
    w = int(coords.findtext("w", "0"))
    h = int(coords.findtext("h", "0"))
    label = clean_text(element.findtext("panel_attributes", "")).strip()
    return [
        f'<ellipse cx="{x + w / 2}" cy="{y + h / 2}" rx="{w / 2}" ry="{h / 2}" fill="#fff" stroke="#1f2933" stroke-width="1.5"/>',
        f'<text x="{x + w / 2}" y="{y + h / 2 + 5}" text-anchor="middle" font-size="12" font-family="Arial, sans-serif">{esc(label)}</text>',
    ]


def draw_actor(element: ET.Element) -> list[str]:
    coords = element.find("coordinates")
    assert coords is not None
    x = int(coords.findtext("x", "0"))
    y = int(coords.findtext("y", "0"))
    w = int(coords.findtext("w", "0"))
    h = int(coords.findtext("h", "0"))
    label = clean_text(element.findtext("panel_attributes", "")).strip()
    cx = x + w / 2
    return [
        f'<circle cx="{cx}" cy="{y + 14}" r="10" fill="#fff" stroke="#1f2933" stroke-width="1.5"/>',
        f'<line x1="{cx}" y1="{y + 24}" x2="{cx}" y2="{y + 52}" stroke="#1f2933" stroke-width="1.5"/>',
        f'<line x1="{x + 10}" y1="{y + 34}" x2="{x + w - 10}" y2="{y + 34}" stroke="#1f2933" stroke-width="1.5"/>',
        f'<line x1="{cx}" y1="{y + 52}" x2="{x + 12}" y2="{y + h - 18}" stroke="#1f2933" stroke-width="1.5"/>',
        f'<line x1="{cx}" y1="{y + 52}" x2="{x + w - 12}" y2="{y + h - 18}" stroke="#1f2933" stroke-width="1.5"/>',
        f'<text x="{cx}" y="{y + h - 2}" text-anchor="middle" font-size="12" font-family="Arial, sans-serif">{esc(label)}</text>',
    ]


def draw_note_or_frame(element: ET.Element, fill: str) -> list[str]:
    coords = element.find("coordinates")
    assert coords is not None
    x = int(coords.findtext("x", "0"))
    y = int(coords.findtext("y", "0"))
    w = int(coords.findtext("w", "0"))
    h = int(coords.findtext("h", "0"))
    lines = text_lines(element.findtext("panel_attributes", ""))
    return [
        f'<rect x="{x}" y="{y}" width="{w}" height="{h}" fill="{fill}" stroke="#1f2933" stroke-width="1.2"/>',
        *draw_multiline_text(lines, x, y + 18, w, size=12),
    ]


def draw_generic(element: ET.Element) -> list[str]:
    coords = element.find("coordinates")
    assert coords is not None
    x = int(coords.findtext("x", "0"))
    y = int(coords.findtext("y", "0"))
    w = int(coords.findtext("w", "0"))
    h = int(coords.findtext("h", "0"))
    lines = text_lines(element.findtext("panel_attributes", ""))
    title = lines[0].strip() if lines else ""
    return [
        f'<rect x="{x}" y="{y}" width="{w}" height="{h}" fill="none" stroke="#111827" stroke-width="1.5"/>',
        f'<text x="{x + w / 2}" y="{y + 22}" text-anchor="middle" font-size="14" font-family="Arial, sans-serif" font-weight="bold">{esc(title)}</text>',
    ]


def draw_relation(element: ET.Element) -> list[str]:
    coords = element.find("coordinates")
    assert coords is not None
    points = parse_points(coords, element.findtext("additional_attributes", ""))
    if len(points) < 2:
        return []

    attrs = clean_text(element.findtext("panel_attributes", ""))
    dash = ' stroke-dasharray="5 4"' if "lt=." in attrs else ""
    point_attr = " ".join(f"{x},{y}" for x, y in points)
    output = [f'<polyline points="{point_attr}" fill="none" stroke="#6b7280" stroke-width="1.2"{dash}/>']

    label_lines = [line for line in attrs.split("\n") if line.strip() and not line.strip().startswith("lt=")]
    if label_lines:
        mid_x = int(sum(x for x, _ in points) / len(points))
        mid_y = int(sum(y for _, y in points) / len(points)) - 4
        label_width = max(48, min(220, max(len(line) for line in label_lines) * 6 + 20))
        label_height = len(label_lines) * 15 + 8
        output.append(
            f'<rect x="{mid_x + 2}" y="{mid_y - 14}" width="{label_width}" height="{label_height}" fill="white" opacity="0.86"/>'
        )
        output.extend(draw_multiline_text(label_lines, mid_x, mid_y, label_width, size=11))
    return output


def render_file(path: Path) -> str:
    tree = ET.parse(path)
    root = tree.getroot()
    elements = root.findall("element")
    max_x = 0
    max_y = 0
    for element in elements:
        coords = element.find("coordinates")
        if coords is None:
            continue
        x = int(coords.findtext("x", "0"))
        y = int(coords.findtext("y", "0"))
        w = int(coords.findtext("w", "0"))
        h = int(coords.findtext("h", "0"))
        max_x = max(max_x, x + w + 40)
        max_y = max(max_y, y + h + 40)

    relations = [element for element in elements if element.findtext("id", "") == "Relation"]
    shapes = [element for element in elements if element.findtext("id", "") != "Relation"]

    body: list[str] = []
    # Draw relations first so class/object boxes mask line crossings.
    for element in relations:
        body.extend(draw_relation(element))

    for element in shapes:
        element_id = element.findtext("id", "")
        if element_id == "UMLClass":
            body.extend(draw_class(element))
        elif element_id == "UMLObject":
            body.extend(draw_object(element))
        elif element_id == "UMLUseCase":
            body.extend(draw_use_case(element))
        elif element_id == "UMLActor":
            body.extend(draw_actor(element))
        elif element_id == "UMLNote":
            body.extend(draw_note_or_frame(element, "#fff8db"))
        elif element_id == "UMLFrame":
            body.extend(draw_note_or_frame(element, "#f8fafc"))
        elif element_id == "UMLGeneric":
            body.extend(draw_generic(element))

    return "\n".join(
        [
            '<?xml version="1.0" encoding="UTF-8"?>',
            f'<svg xmlns="http://www.w3.org/2000/svg" width="{max_x}" height="{max_y}" viewBox="0 0 {max_x} {max_y}">',
            '<rect width="100%" height="100%" fill="white"/>',
            *body,
            "</svg>",
        ]
    )


def main() -> int:
    IMAGE_DIR.mkdir(exist_ok=True)
    files = sorted(ROOT.glob("*.uxf"))
    if not files:
        print("No UXF files found.", file=sys.stderr)
        return 1

    for path in files:
        output = IMAGE_DIR / f"{path.stem}.svg"
        output.write_text(render_file(path), encoding="utf-8")
        print(f"Rendered {path.name} -> {output.relative_to(ROOT)}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
