#!/usr/bin/env python3
"""Compare EE vs port block hardness and blast resistance; optionally patch ModBlocks.java."""

from __future__ import annotations

import argparse
import csv
import re
import sys
from dataclasses import dataclass
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
EE_BLOCKS = ROOT.parent / "мод hbmntm" / "NTM-Extended-GitHub" / "src" / "main" / "java" / "com" / "hbm" / "blocks" / "ModBlocks.java"
PORT_BLOCKS = ROOT / "src" / "main" / "java" / "com" / "hbm" / "blocks" / "ModBlocks.java"
REPORT_ALL = ROOT / "tools" / "block_stats_report.csv"
REPORT_DIFF = ROOT / "tools" / "block_stats_diff.csv"
REPORT_EE_UNMATCHED = ROOT / "tools" / "block_stats_ee_unmatched.csv"
REPORT_PORT_UNMATCHED = ROOT / "tools" / "block_stats_port_unmatched.csv"
REPORT_APPLIED = ROOT / "tools" / "block_stats_applied.csv"
REPORT_MANUAL_REVIEW = ROOT / "tools" / "block_stats_manual_review.csv"

BLOCK_DECL = re.compile(
    r"public\s+static\s+final\s+Block\s+(\w+)\s*=\s*new\s+[^;]+?;",
    re.DOTALL,
)
HARDNESS = re.compile(r"\.setHardness\(([^)]+)\)")
RESISTANCE = re.compile(r"\.setResistance\(([^)]+)\)")
REGISTRY = re.compile(r'new\s+\w+\([^,]*,\s*"([^"]+)"')

# port Java field -> EE Java field (when registry names differ or port uses meta-block)
FIELD_ALIASES: dict[str, str] = {
    "reinforced_ducrete": "ducrete_reinforced",
    "brick_ducrete": "ducrete_brick",
    "concrete_colored": "concrete_white",
    "concrete_colored_ext": "concrete_white",
    "reinforced_glass_pane": "reinforced_glass",
}

COLORS = [
    "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
    "silver", "cyan", "purple", "blue", "brown", "green", "red", "black",
]


@dataclass
class BlockStats:
    field: str
    registry: str
    hardness: str | None
    resistance: str | None


def parse_blocks(text: str) -> dict[str, BlockStats]:
    out: dict[str, BlockStats] = {}
    for m in BLOCK_DECL.finditer(text):
        field = m.group(1)
        body = m.group(0)
        reg_m = REGISTRY.search(body)
        registry = reg_m.group(1) if reg_m else field
        h = HARDNESS.search(body)
        r = RESISTANCE.search(body)
        out[field] = BlockStats(
            field=field,
            registry=registry,
            hardness=h.group(1).strip() if h else None,
            resistance=r.group(1).strip() if r else None,
        )
    return out


def ee_by_registry(ee: dict[str, BlockStats]) -> dict[str, BlockStats]:
    reg: dict[str, BlockStats] = {}
    for stats in ee.values():
        reg[stats.registry] = stats
    return reg


def resolve_ee_stats(
    port_field: str,
    port: dict[str, BlockStats],
    ee: dict[str, BlockStats],
    ee_reg: dict[str, BlockStats],
) -> tuple[str, BlockStats] | None:
    ps = port[port_field]

    # 1) registry name
    if ps.registry in ee_reg:
        return ps.registry, ee_reg[ps.registry]

    # 2) same Java field in EE
    if port_field in ee:
        return port_field, ee[port_field]

    # 3) field alias -> EE field
    if port_field in FIELD_ALIASES and FIELD_ALIASES[port_field] in ee:
        ee_field = FIELD_ALIASES[port_field]
        return ee[ee_field].registry, ee[ee_field]

    # 4) colored concrete stairs from meta arrays
    for color in COLORS:
        if port_field == f"concrete_colored_stairs_{color}":
            key = f"concrete_{color}_stairs"
            if key in ee:
                return key, ee[key]
        if port_field == f"concrete_colored_ext_stairs_{color}":
            key = f"concrete_{color}_stairs"
            if key in ee:
                return key, ee[key]

    # 5) double slab -> parent single slab (EE has no double slabs)
    if port_field.endswith("_double_slab"):
        slab_field = port_field.replace("_double_slab", "_slab")
        if slab_field in port:
            slab_reg = port[slab_field].registry
            if slab_reg in ee_reg:
                return slab_reg, ee_reg[slab_reg]
            if slab_field in ee:
                return slab_field, ee[slab_field]

    return None


def patch_value(text: str, field: str, setter: str, new_val: str) -> tuple[str, bool]:
    pattern = re.compile(
        rf"(public\s+static\s+final\s+Block\s+{re.escape(field)}\s*=\s*new\s+[^;]+?\.{setter}\()([^)]+)(\))",
        re.DOTALL,
    )
    m = pattern.search(text)
    if not m or m.group(2).strip() == new_val:
        return text, False
    return text[: m.start(2)] + new_val + text[m.end(2) :], True


def patch_method_block(text: str, method_marker: str, resistance: str, hardness: str = "15.0F") -> tuple[str, int]:
    changes = 0
    idx = text.find(method_marker)
    if idx < 0:
        return text, 0

    start = text.find("{", idx)
    depth = 0
    end = start
    for i in range(start, len(text)):
        if text[i] == "{":
            depth += 1
        elif text[i] == "}":
            depth -= 1
            if depth == 0:
                end = i + 1
                break
    body = text[start:end]
    old_body = body

    body = re.sub(r"\.setResistance\([^)]+\)", f".setResistance({resistance})", body)
    body = re.sub(r"\.setHardness\([^)]+\)", f".setHardness({hardness})", body)
    if body != old_body:
        changes = old_body.count(".setResistance(") + old_body.count(".setHardness(")
        text = text[:start] + body + text[end:]
    return text, changes


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--apply", action="store_true")
    args = parser.parse_args()

    if not EE_BLOCKS.is_file() or not PORT_BLOCKS.is_file():
        print("Missing ModBlocks.java", file=sys.stderr)
        return 1

    ee_text = EE_BLOCKS.read_text(encoding="utf-8")
    port_text = PORT_BLOCKS.read_text(encoding="utf-8")
    ee = parse_blocks(ee_text)
    port = parse_blocks(port_text)
    ee_reg = ee_by_registry(ee)

    REPORT_ALL.parent.mkdir(parents=True, exist_ok=True)
    with REPORT_ALL.open("w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["field", "registry", "hardness", "resistance"])
        for field in sorted(ee, key=lambda k: ee[k].registry):
            s = ee[field]
            if s.resistance is not None:
                w.writerow([field, s.registry, s.hardness or "", s.resistance])

    diffs: list[tuple[str, str, str, str | None, str | None, str | None, str | None]] = []
    matched_ee_keys: set[str] = set()

    for port_field in sorted(port):
        ps = port[port_field]
        if ps.resistance is None and ps.hardness is None:
            continue
        resolved = resolve_ee_stats(port_field, port, ee, ee_reg)
        if not resolved:
            continue
        ee_key, es = resolved
        matched_ee_keys.add(es.registry)
        if es.hardness is None and es.resistance is None:
            continue
        if ps.hardness != es.hardness or ps.resistance != es.resistance:
            diffs.append((port_field, ps.registry, ee_key, ps.hardness, es.hardness, ps.resistance, es.resistance))

    port_matched = {row[0] for row in diffs}
    port_unmatched = []
    for port_field in sorted(port):
        ps = port[port_field]
        if ps.resistance is None:
            continue
        if resolve_ee_stats(port_field, port, ee, ee_reg) is None:
            port_unmatched.append((port_field, ps.registry, ps.hardness or "", ps.resistance))

    ee_unmatched = []
    for field in sorted(ee, key=lambda k: ee[k].registry):
        s = ee[field]
        if s.resistance is None:
            continue
        if s.registry not in matched_ee_keys:
            # check if any port block resolves to this EE block
            found = False
            for pf in port:
                r = resolve_ee_stats(pf, port, ee, ee_reg)
                if r and r[1].registry == s.registry:
                    found = True
                    break
            if not found:
                ee_unmatched.append((field, s.registry, s.hardness or "", s.resistance))

    with REPORT_DIFF.open("w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["port_field", "port_registry", "ee_source", "port_hardness", "ee_hardness", "port_resistance", "ee_resistance"])
        for row in diffs:
            w.writerow(row)

    with REPORT_PORT_UNMATCHED.open("w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["port_field", "registry", "hardness", "resistance"])
        for row in port_unmatched:
            w.writerow(row)

    with REPORT_EE_UNMATCHED.open("w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["ee_field", "registry", "hardness", "resistance"])
        for row in ee_unmatched:
            w.writerow(row)

    with REPORT_MANUAL_REVIEW.open("w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["source", "field", "registry", "hardness", "resistance"])
        for row in port_unmatched:
            w.writerow(["port_only", *row])
        for row in ee_unmatched:
            w.writerow(["ee_only", *row])

    print(f"EE blocks parsed: {len(ee)}")
    print(f"Port blocks parsed: {len(port)}")
    print(f"Diff rows: {len(diffs)}")
    print(f"Port unmatched (with resistance): {len(port_unmatched)}")
    print(f"EE unmatched (with resistance): {len(ee_unmatched)}")
    print(f"Report: {REPORT_ALL}")
    print(f"Diff:   {REPORT_DIFF}")
    print(f"Manual review: {REPORT_MANUAL_REVIEW}")
    if "gravel_obsidian" in port:
        ps = port["gravel_obsidian"]
        ee_r = ee["gravel_obsidian"].resistance if "gravel_obsidian" in ee else "?"
        print(f"REFERENCE gravel_obsidian (Crushed Obsidian): port R={ps.resistance}, EE R={ee_r}, 1.7.10 HBM R=240")
    for row in diffs[:30]:
        print(f"  {row[0]} [{row[1]}] <- {row[2]}: H {row[3]}->{row[4]}, R {row[5]}->{row[6]}")
    if len(diffs) > 30:
        print(f"  ... and {len(diffs) - 30} more")

    if not args.apply:
        return 0

    patched = port_text
    applied_rows: list[tuple[str, str, str]] = []

    for port_field, _, ee_key, ph, eh, pr, er in diffs:
        if eh and ph != eh:
            patched, c = patch_value(patched, port_field, "setHardness", eh)
            if c:
                applied_rows.append((port_field, "hardness", f"{ph} -> {eh}"))
        if er and pr != er:
            patched, c = patch_value(patched, port_field, "setResistance", er)
            if c:
                applied_rows.append((port_field, "resistance", f"{pr} -> {er}"))

    patched, c = patch_method_block(patched, "makeConcreteStairs()", "3000.0F", "15.0F")
    if c:
        applied_rows.append(("makeConcreteStairs()", "method", f"{c} updates"))
    patched, c = patch_method_block(patched, "makeConcreteExtStairs()", "3000.0F", "15.0F")
    if c:
        applied_rows.append(("makeConcreteExtStairs()", "method", f"{c} updates"))

    PORT_BLOCKS.write_text(patched, encoding="utf-8")

    with REPORT_APPLIED.open("w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["target", "stat", "change"])
        for row in applied_rows:
            w.writerow(row)

    print(f"Applied {len(applied_rows)} stat updates to {PORT_BLOCKS}")
    print(f"Applied log: {REPORT_APPLIED}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())